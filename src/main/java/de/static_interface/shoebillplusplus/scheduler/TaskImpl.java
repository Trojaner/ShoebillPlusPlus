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

package de.static_interface.shoebillplusplus.scheduler;


import de.static_interface.shoebillplusplus.*;
import de.static_interface.shoebillplusplus.api.scheduler.*;
import net.gtaun.shoebill.resource.*;

class TaskImpl implements PlusTask, Runnable {

    private volatile TaskImpl next = null;
    /**
     * -1 means no repeating <br>
     * -2 means cancel <br>
     * -3 means processing for Future <br>
     * -4 means done for Future <br>
     * Never 0 <br>
     * >0 means number of ticks to wait between each execution
     */
    private volatile long period;
    private long nextRun;
    private final Runnable task;
    private final Resource resource;
    private final int id;

    TaskImpl() {
        this(null, null, -1, -1);
    }

    TaskImpl(final Runnable task) {
        this(null, task, -1, -1);
    }

    TaskImpl(final Resource resource, final Runnable task, final int id, final long period) {
        this.resource = resource;
        this.task = task;
        this.id = id;
        this.period = period;
    }

    public final int getTaskId() {
        return id;
    }

    public final Resource getOwner() {
        return resource;
    }

    public boolean isSync() {
        return true;
    }

    public void run() {
        task.run();
    }

    long getPeriod() {
        return period;
    }

    void setPeriod(long period) {
        this.period = period;
    }

    long getNextRun() {
        return nextRun;
    }

    void setNextRun(long nextRun) {
        this.nextRun = nextRun;
    }

    TaskImpl getNext() {
        return next;
    }

    void setNext(TaskImpl next) {
        this.next = next;
    }

    Class<? extends Runnable> getTaskClass() {
        return task.getClass();
    }

    public void cancel() {
        PlusServer.get().getScheduler().cancelTask(id);
    }

    /**
     * This method properly sets the status to cancelled, synchronizing when required.
     *
     * @return false if it is a craft future task that has already begun execution, true otherwise
     */
    boolean cancel0() {
        setPeriod(-2l);
        return true;
    }
}