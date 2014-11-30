/*
 * Copyright (c) 2013 - 2014 <http://static-interface.de> and contributors
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.static_interface.shoebillplusplus.api.scheduler;

import de.static_interface.shoebillplusplus.*;
import net.gtaun.shoebill.resource.*;

/**
 * This class is provided as an easy way to handle scheduling tasks.
 */
public abstract class PlusRunnable implements Runnable {
    private int taskId = -1;

    /**
     * Attempts to cancel this task.
     *
     * @throws IllegalStateException if task was not scheduled yet
     */
    public synchronized void cancel() throws IllegalStateException {
        PlusServer.get().getScheduler().cancelTask(getTaskId());
    }

    /**
     * Schedules this in the Bukkit scheduler to run on next tick.
     *
     * @param resource the reference to the resource scheduling task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalStateException if this was already scheduled
     * @see PlusScheduler#runTask(Resource, Runnable)
     */
    public synchronized PlusTask runTask(Resource resource) throws IllegalArgumentException, IllegalStateException {
        checkState();
        return setupId(PlusServer.get().getScheduler().runTask(resource, (Runnable) this));
    }

    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules this in the Bukkit scheduler to run asynchronously.
     *
     * @param resource the reference to the resource scheduling task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalStateException if this was already scheduled
     * @see PlusScheduler#runTaskAsynchronously(Resource, Runnable)
     */
    public synchronized PlusTask runTaskAsynchronously(Resource resource) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(PlusServer.get().getScheduler().runTaskAsynchronously(resource, (Runnable) this));
    }

    /**
     * Schedules this to run after the specified number of server ticks.
     *
     * @param resource the reference to the resource scheduling task
     * @param delay the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalStateException if this was already scheduled
     * @see PlusScheduler#runTaskLater(Resource, Runnable, long)
     */
    public synchronized PlusTask runTaskLater(Resource resource, long delay) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(PlusServer.get().getScheduler().runTaskLater(resource, (Runnable) this, delay));
    }

    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules this to run asynchronously after the specified number of
     * server ticks.
     *
     * @param resource the reference to the resource scheduling task
     * @param delay the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalStateException if this was already scheduled
     * @see PlusScheduler#runTaskLaterAsynchronously(Resource, Runnable, long)
     */
    public synchronized PlusTask runTaskLaterAsynchronously(Resource resource, long delay) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(PlusServer.get().getScheduler().runTaskLaterAsynchronously(resource, (Runnable) this, delay));
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the
     * specified number of server ticks.
     *
     * @param resource the reference to the resource scheduling task
     * @param delay the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalStateException if this was already scheduled
     * @see PlusScheduler#runTaskTimer(Resource, Runnable, long, long)
     */
    public synchronized PlusTask runTaskTimer(Resource resource, long delay, long period) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(PlusServer.get().getScheduler().runTaskTimer(resource, (Runnable) this, delay, period));
    }

    /**
     * <b>Asynchronous tasks should never access any API in Bukkit. Great care
     * should be taken to assure the thread-safety of asynchronous tasks.</b>
     * <p>
     * Schedules this to repeatedly run asynchronously until cancelled,
     * starting after the specified number of server ticks.
     *
     * @param resource the reference to the resource scheduling task
     * @param delay the ticks to wait before running the task for the first
     *     time
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     * @throws IllegalArgumentException if resource is null
     * @throws IllegalStateException if this was already scheduled
     * @see PlusScheduler#runTaskTimerAsynchronously(Resource, Runnable, long,
     *     long)
     */
    public synchronized PlusTask runTaskTimerAsynchronously(Resource resource, long delay, long period) throws IllegalArgumentException, IllegalStateException  {
        checkState();
        return setupId(PlusServer.get().getScheduler().runTaskTimerAsynchronously(resource, (Runnable) this, delay, period));
    }

    /**
     * Gets the task id for this runnable.
     *
     * @return the task id that this runnable was scheduled as
     * @throws IllegalStateException if task was not scheduled yet
     */
    public synchronized int getTaskId() throws IllegalStateException {
        final int id = taskId;
        if (id == -1) {
            throw new IllegalStateException("Not scheduled yet");
        }
        return id;
    }

    private void checkState() {
        if (taskId != -1) {
            throw new IllegalStateException("Already scheduled as " + taskId);
        }
    }

    private PlusTask setupId(final PlusTask task) {
        this.taskId = task.getTaskId();
        return task;
    }
}