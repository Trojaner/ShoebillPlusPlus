package de.static_interface.shoebillplusplus.scheduler;

import de.static_interface.shoebillplusplus.api.scheduler.*;
import net.gtaun.shoebill.resource.*;
import org.apache.commons.lang3.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class SchedulerImpl implements PlusScheduler {

    /**
     * Counter for IDs. Order doesn't matter, only uniqueness.
     */
    private final AtomicInteger ids = new AtomicInteger(1);
    /**
     * Current head of linked-list. This reference is always stale, {@link TaskImpl#next} is the live reference.
     */
    private volatile TaskImpl head = new TaskImpl();
    /**
     * Tail of a linked-list. AtomicReference only matters when adding to queue
     */
    private final AtomicReference<TaskImpl> tail = new AtomicReference<>(head);
    /**
     * Main thread logic only
     */
    private final PriorityQueue<TaskImpl> pending = new PriorityQueue<>(10,
                                                                                  new Comparator<TaskImpl>() {
                                                                                      public int compare(final TaskImpl o1, final TaskImpl o2) {
                                                                                          return (int) (o1.getNextRun() - o2.getNextRun());
                                                                                      }
                                                                                  });
    /**
     * Main thread logic only
     */
    private final List<TaskImpl> temp = new ArrayList<>();
    /**
     * These are tasks that are currently active. It's provided for 'viewing' the current state.
     */
    private final ConcurrentHashMap<Integer, TaskImpl> runners = new ConcurrentHashMap<>();
    private volatile int currentTick = -1;
    private final Executor executor = Executors.newCachedThreadPool();
    private AsyncDebugger debugHead = new AsyncDebugger(-1, null, null) {@Override StringBuilder debugTo(StringBuilder string) {return string;}};
    private AsyncDebugger debugTail = debugHead;
    private static final int RECENT_TICKS;

    static {
        RECENT_TICKS = 30;
    }

    @Override
    public int scheduleSyncDelayedTask(final Resource resource, final Runnable task) {
        return this.scheduleSyncDelayedTask(resource, task, 0l);
    }

    @Override
    public PlusTask runTask(Resource resource, Runnable runnable) {
        return runTaskLater(resource, runnable, 0l);
    }

    @Override
    public PlusTask runTaskAsynchronously(Resource resource, Runnable runnable) {
        return runTaskLaterAsynchronously(resource, runnable, 0l);
    }

    @Override
    public PlusTask runTaskLater(Resource resource, Runnable runnable, long delay) throws IllegalArgumentException {
        return runTaskTimer(resource, runnable, delay, -1l);
    }

    @Override
    public int scheduleSyncDelayedTask(final Resource resource, final Runnable task, final long delay) {
        return this.scheduleSyncRepeatingTask(resource, task, delay, -1l);
    }

    @Override
    public PlusTask runTaskLaterAsynchronously(Resource resource, Runnable runnable, long delay) {
        return runTaskTimerAsynchronously(resource, runnable, delay, -1l);
    }

    @Override
    public int scheduleSyncRepeatingTask(final Resource resource, final Runnable runnable, long delay, long period) {
        return runTaskTimer(resource, runnable, delay, period).getTaskId();
    }

    @Override
    public PlusTask runTaskTimer(Resource resource, Runnable runnable, long delay, long period) {
        validate(resource, runnable);
        if (delay < 0l) {
            delay = 0;
        }
        if (period == 0l) {
            period = 1l;
        } else if (period < -1l) {
            period = -1l;
        }
        return handle(new TaskImpl(resource, runnable, nextId(), period), delay);
    }

    @Override
    public PlusTask runTaskTimerAsynchronously(Resource resource, Runnable runnable, long delay, long period) {
        validate(resource, runnable);
        if (delay < 0l) {
            delay = 0;
        }
        if (period == 0l) {
            period = 1l;
        } else if (period < -1l) {
            period = -1l;
        }
        return handle(new AsyncTaskImpl(runners, resource, runnable, nextId(), period), delay);
    }

    @Override
    public <T> Future<T> callSyncMethod(final Resource resource, final Callable<T> task) {
        validate(resource, task);
        final FutureImpl<T> future = new FutureImpl<T>(task, resource, nextId());
        handle(future, 0l);
        return future;
    }

    @Override
    public void cancelTask(final int taskId) {
        if (taskId <= 0) {
            return;
        }
        TaskImpl task = runners.get(taskId);
        if (task != null) {
            task.cancel0();
        }
        task = new TaskImpl(
                new Runnable() {
                    public void run() {
                        if (!check(SchedulerImpl.this.temp)) {
                            check(SchedulerImpl.this.pending);
                        }
                    }
                    private boolean check(final Iterable<TaskImpl> collection) {
                        final Iterator<TaskImpl> tasks = collection.iterator();
                        while (tasks.hasNext()) {
                            final TaskImpl task = tasks.next();
                            if (task.getTaskId() == taskId) {
                                task.cancel0();
                                tasks.remove();
                                if (task.isSync()) {
                                    runners.remove(taskId);
                                }
                                return true;
                            }
                        }
                        return false;
                    }});
        handle(task, 0l);
        for (TaskImpl taskPending = head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
            if (taskPending == task) {
                return;
            }
            if (taskPending.getTaskId() == taskId) {
                taskPending.cancel0();
            }
        }
    }

    @Override
    public void cancelTasks(final Resource resource) {
        Validate.notNull(resource, "Cannot cancel tasks of null resource");
        final TaskImpl task = new TaskImpl(
                new Runnable() {
                    public void run() {
                        check(SchedulerImpl.this.pending);
                        check(SchedulerImpl.this.temp);
                    }
                    void check(final Iterable<TaskImpl> collection) {
                        final Iterator<TaskImpl> tasks = collection.iterator();
                        while (tasks.hasNext()) {
                            final TaskImpl task = tasks.next();
                            if (task.getOwner().equals(resource)) {
                                task.cancel0();
                                tasks.remove();
                                if (task.isSync()) {
                                    runners.remove(task.getTaskId());
                                }
                            }
                        }
                    }
                });
        handle(task, 0l);
        for (TaskImpl taskPending = head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
            if (taskPending == task) {
                return;
            }
            if (taskPending.getTaskId() != -1 && taskPending.getOwner().equals(resource)) {
                taskPending.cancel0();
            }
        }
        for (TaskImpl runner : runners.values()) {
            if (runner.getOwner().equals(resource)) {
                runner.cancel0();
            }
        }
    }

    @Override
    public void cancelAllTasks() {
        final TaskImpl task = new TaskImpl(
                new Runnable() {
                    public void run() {
                        Iterator<TaskImpl> it = SchedulerImpl.this.runners.values().iterator();
                        while (it.hasNext()) {
                            TaskImpl task = it.next();
                            task.cancel0();
                            if (task.isSync()) {
                                it.remove();
                            }
                        }
                        SchedulerImpl.this.pending.clear();
                        SchedulerImpl.this.temp.clear();
                    }
                });
        handle(task, 0l);
        for (TaskImpl taskPending = head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
            if (taskPending == task) {
                break;
            }
            taskPending.cancel0();
        }
        for (TaskImpl runner : runners.values()) {
            runner.cancel0();
        }
    }

    @Override
    public boolean isCurrentlyRunning(final int taskId) {
        final TaskImpl task = runners.get(taskId);
        if (task == null || task.isSync()) {
            return false;
        }
        final AsyncTaskImpl asyncTask = (AsyncTaskImpl) task;
        synchronized (asyncTask.getWorkers()) {
            return asyncTask.getWorkers().isEmpty();
        }
    }

    @Override
    public boolean isQueued(final int taskId) {
        if (taskId <= 0) {
            return false;
        }
        for (TaskImpl task = head.getNext(); task != null; task = task.getNext()) {
            if (task.getTaskId() == taskId) {
                return task.getPeriod() >= -1l; // The task will run
            }
        }
        TaskImpl task = runners.get(taskId);
        return task != null && task.getPeriod() >= -1l;
    }

    @Override
    public List<PlusWorker> getActiveWorkers() {
        final ArrayList<PlusWorker> workers = new ArrayList<PlusWorker>();
        for (final TaskImpl taskObj : runners.values()) {
            // Iterator will be a best-effort (may fail to grab very new values) if called from an async thread
            if (taskObj.isSync()) {
                continue;
            }
            final AsyncTaskImpl task = (AsyncTaskImpl) taskObj;
            synchronized (task.getWorkers()) {
                // This will never have an issue with stale threads; it's state-safe
                workers.addAll(task.getWorkers());
            }
        }
        return workers;
    }

    @Override
    public List<PlusTask> getPendingTasks() {
        final List<TaskImpl> truePending = new ArrayList<>();
        for (TaskImpl task = head.getNext(); task != null; task = task.getNext()) {
            if (task.getTaskId() != -1) {
                // -1 is special code
                truePending.add(task);
            }
        }

        final List<PlusTask> pending = new ArrayList<>();
        for (TaskImpl task : runners.values()) {
            if (task.getPeriod() >= -1l) {
                pending.add(task);
            }
        }

        for (final TaskImpl task : truePending) {
            if (task.getPeriod() >= -1l && !pending.contains(task)) {
                pending.add(task);
            }
        }
        return pending;
    }

    /**
     * This method is designed to never block or wait for locks; an immediate execution of all current tasks.
     */
    public void mainThreadHeartbeat(final int currentTick) {
        this.currentTick = currentTick;
        final List<TaskImpl> temp = this.temp;
        parsePending();
        while (isReady(currentTick)) {
            final TaskImpl task = pending.remove();
            if (task.getPeriod() < -1l) {
                if (task.isSync()) {
                    runners.remove(task.getTaskId(), task);
                }
                parsePending();
                continue;
            }
            if (task.isSync()) {
                try {
                    task.run();
                } catch (final Throwable throwable) {
                    task.getOwner().getLogger().warn(
                            String.format(
                                    "Task #%s for %s generated an exception",
                                    task.getTaskId(),
                                    task.getOwner().getDescription().getName()),
                            throwable);
                }
                parsePending();
            } else {
                debugTail = debugTail.setNext(new AsyncDebugger(currentTick + RECENT_TICKS, task.getOwner(), task.getTaskClass()));
                executor.execute(task);
                // We don't need to parse pending
                // (async tasks must live with race-conditions if they attempt to cancel between these few lines of code)
            }
            final long period = task.getPeriod(); // State consistency
            if (period > 0) {
                task.setNextRun(currentTick + period);
                temp.add(task);
            } else if (task.isSync()) {
                runners.remove(task.getTaskId());
            }
        }
        pending.addAll(temp);
        temp.clear();
        debugHead = debugHead.getNextHead(currentTick);
    }

    private void addTask(final TaskImpl task) {
        final AtomicReference<TaskImpl> tail = this.tail;
        TaskImpl tailTask = tail.get();
        while (!tail.compareAndSet(tailTask, task)) {
            tailTask = tail.get();
        }
        tailTask.setNext(task);
    }

    private TaskImpl handle(final TaskImpl task, final long delay) {
        task.setNextRun(currentTick + delay);
        addTask(task);
        return task;
    }

    private static void validate(final Resource resource, final Object task) {
        Validate.notNull(resource, "Resource cannot be null");
        Validate.notNull(task, "Task cannot be null");
        if (!resource.isEnabled()) {
            throw new RuntimeException("Resource attempted to register task while disabled");
        }
    }

    private int nextId() {
        return ids.incrementAndGet();
    }

    private void parsePending() {
        TaskImpl head = this.head;
        TaskImpl task = head.getNext();
        TaskImpl lastTask = head;
        for (; task != null; task = (lastTask = task).getNext()) {
            if (task.getTaskId() == -1) {
                task.run();
            } else if (task.getPeriod() >= -1l) {
                pending.add(task);
                runners.put(task.getTaskId(), task);
            }
        }
        // We split this because of the way things are ordered for all of the async calls in CraftScheduler
        // (it prevents race-conditions)
        for (task = head; task != lastTask; task = head) {
            head = task.getNext();
            task.setNext(null);
        }
        this.head = lastTask;
    }

    private boolean isReady(final int currentTick) {
        return !pending.isEmpty() && pending.peek().getNextRun() <= currentTick;
    }

    @Override
    public String toString() {
        int debugTick = currentTick;
        StringBuilder string = new StringBuilder("Recent tasks from ").append(debugTick - RECENT_TICKS).append('-').append(debugTick).append('{');
        debugHead.debugTo(string);
        return string.append('}').toString();
    }
}