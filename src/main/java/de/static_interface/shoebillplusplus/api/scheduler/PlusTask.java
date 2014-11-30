package de.static_interface.shoebillplusplus.api.scheduler;

import net.gtaun.shoebill.resource.*;

/**
 * Represents a task being executed by the scheduler
 */
public interface PlusTask {

    /**
     * Returns the taskId for the task.
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
     * Returns true if the Task is a sync task.
     *
     * @return true if the task is run by main thread
     */
    public boolean isSync();

    /**
     * Will attempt to cancel this task.
     */
    public void cancel();
}