package de.static_interface.shoebillplusplus.api.scheduler;

import net.gtaun.shoebill.resource.*;

import java.util.*;
import java.util.concurrent.*;

public interface PlusScheduler {

    /**
     * Schedules a once off task to occur after a delay.
     * <p>
     * This task will be executed by the main server thread.
     *
     * @param resource Resource that owns the task
     * @param task Task to be executed
     * @param delay Delay in server ticks before executing task
     * @return Task id number (-1 if scheduling failed)
     */
    public int scheduleSyncDelayedTask(Resource resource, Runnable task, long delay);

    /**
     * Schedules a once off task to occur as soon as possible.
     * <p>
     * This task will be executed by the main server thread.
     *
     * @param resource Resource that owns the task
     * @param task Task to be executed
     * @return Task id number (-1 if scheduling failed)
     */
    public int scheduleSyncDelayedTask(Resource resource, Runnable task);

    /**
     * Schedules a repeating task.
     * <p>
     * This task will be executed by the main server thread.
     *
     * @param resource Resource that owns the task
     * @param task Task to be executed
     * @param delay Delay in server ticks before executing first repeat
     * @param period Period in server ticks of the task
     * @return Task id number (-1 if scheduling failed)
     */
    public int scheduleSyncRepeatingTask(Resource resource, Runnable task, long delay, long period);

    /**
     * Calls a method on the main thread and returns a Future object. This
     * task will be executed by the main server thread.
     * <ul>
     * <li>Note: The Future.get() methods must NOT be called from the main
     *     thread.
     * <li>Note2: There is at least an average of 10ms latency until the
     *     isDone() method returns true.
     * </ul>
     * @param <T> The callable's return type
     * @param resource Resource that owns the task
     * @param task Task to be executed
     * @return Future Future object related to the task
     */
    public <T> Future<T> callSyncMethod(Resource resource, Callable<T> task);

    /**
     * Removes task from scheduler.
     *
     * @param taskId Id number of task to be removed
     */
    public void cancelTask(int taskId);

    /**
     * Removes all tasks associated with a particular resource from the
     * scheduler.
     *
     * @param resource Owner of tasks to be removed
     */
    public void cancelTasks(Resource resource);

    /**
     * Removes all tasks from the scheduler.
     */
    public void cancelAllTasks();

    /**
     * Check if the task currently running.
     * <p>
     * A repeating task might not be running currently, but will be running in
     * the future. A task that has finished, and does not repeat, will not be
     * running ever again.
     * <p>
     * Explicitly, a task is running if there exists a thread for it, and that
     * thread is alive.
     *
     * @param taskId The task to check.
     * <p>
     * @return If the task is currently running.
     */
    public boolean isCurrentlyRunning(int taskId);

    /**
     * Check if the task queued to be run later.
     * <p>
     * If a repeating task is currently running, it might not be queued now
     * but could be in the future. A task that is not queued, and not running,
     * will not be queued again.
     *
     * @param taskId The task to check.
     * <p>
     * @return If the task is queued to be run.
     */
    public boolean isQueued(int taskId);

    /**
     * Returns a list of all active workers.
     * <p>
     * This list contains asynch tasks that are being executed by separate
     * threads.
     *
     * @return Active workers
     */
    public List<PlusWorker> getActiveWorkers();

    /**
     * Returns a list of all pending tasks. The ordering of the tasks is not
     * related to their order of execution.
     *
     * @return Active workers
     */
    public List<PlusTask> getPendingTasks();

    /**
     * Returns a task that will run on the next server tick.
     *
     * @param resource the reference to the resource scheduling task
     * @param task the task to be run
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalArgumentException if task is null
     */
    public PlusTask runTask(Resource resource, Runnable task) throws IllegalArgumentException;

    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Returns a task that will run asynchronously.
     *
     * @param resource the reference to the resource scheduling task
     * @param task the task to be run
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalArgumentException if task is null
     */
    public PlusTask runTaskAsynchronously(Resource resource, Runnable task) throws IllegalArgumentException;

    /**
     * Returns a task that will run after the specified number of server
     * ticks.
     *
     * @param resource the reference to the resource scheduling task
     * @param task the task to be run
     * @param delay the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalArgumentException if task is null
     */
    public PlusTask runTaskLater(Resource resource, Runnable task, long delay) throws IllegalArgumentException;

    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Returns a task that will run asynchronously after the specified number
     * of server ticks.
     *
     * @param resource the reference to the resource scheduling task
     * @param task the task to be run
     * @param delay the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalArgumentException if task is null
     */
    public PlusTask runTaskLaterAsynchronously(Resource resource, Runnable task, long delay) throws IllegalArgumentException;

    /**
     * Returns a task that will repeatedly run until cancelled, starting after
     * the specified number of server ticks.
     *
     * @param resource the reference to the resource scheduling task
     * @param task the task to be run
     * @param delay the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalArgumentException if task is null
     */
    public PlusTask runTaskTimer(Resource resource, Runnable task, long delay, long period) throws IllegalArgumentException;

    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Returns a task that will repeatedly run asynchronously until cancelled,
     * starting after the specified number of server ticks.
     *
     * @param resource the reference to the resource scheduling task
     * @param task the task to be run
     * @param delay the ticks to wait before running the task for the first
     *     time
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalArgumentException if task is null
     */
    public PlusTask runTaskTimerAsynchronously(Resource resource, Runnable task, long delay, long period) throws IllegalArgumentException;

}