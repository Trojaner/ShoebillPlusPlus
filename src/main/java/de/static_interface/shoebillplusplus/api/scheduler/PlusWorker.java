package de.static_interface.shoebillplusplus.api.scheduler;

import net.gtaun.shoebill.resource.*;

/**
 * Represents a worker thread for the scheduler. This gives information about
 * the Thread object for the task, owner of the task and the taskId.
 * <p>
 * Workers are used to execute async tasks.
 */
public interface PlusWorker {

    /**
     * Returns the taskId for the task being executed by this worker.
     *
     * @return Task id number
     */
    public int getTaskId();

    /**
     * Returns the resource that owns this task.
     *
     * @return the resource that owns the task
     */
    public Resource getOwner();

    /**
     * Returns the thread for the worker.
     *
     * @return The Thread object for the worker
     */
    public Thread getThread();

}