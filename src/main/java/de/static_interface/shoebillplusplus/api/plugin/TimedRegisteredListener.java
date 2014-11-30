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

package de.static_interface.shoebillplusplus.api.plugin;

import de.static_interface.shoebillplusplus.api.event.Event;
import de.static_interface.shoebillplusplus.api.event.EventException;
import de.static_interface.shoebillplusplus.api.event.EventPriority;
import de.static_interface.shoebillplusplus.api.event.Listener;
import net.gtaun.shoebill.resource.Resource;


/**
 * Extends RegisteredListener to include timing information
 */
public class TimedRegisteredListener extends RegisteredListener {
    private int count;
    private long totalTime;
    private Class<? extends Event> eventClass;
    private boolean multiple = false;

    public TimedRegisteredListener(final Listener pluginListener, final EventExecutor eventExecutor, final EventPriority eventPriority, final Resource registeredResource, final boolean listenCancelled) {
        super(pluginListener, eventExecutor, eventPriority, registeredResource, listenCancelled);
    }

    @Override
    public void callEvent(Event event) throws EventException {
        if (event.isAsynchronous()) {
            super.callEvent(event);
            return;
        }
        count++;
        Class<? extends Event> newEventClass = event.getClass();
        if (this.eventClass == null) {
            this.eventClass = newEventClass;
        } else if (!this.eventClass.equals(newEventClass)) {
            multiple = true;
            this.eventClass = getCommonSuperclass(newEventClass, this.eventClass).asSubclass(Event.class);
        }
        long start = System.nanoTime();
        super.callEvent(event);
        totalTime += System.nanoTime() - start;
    }

    private static Class<?> getCommonSuperclass(Class<?> class1, Class<?> class2) {
        while (!class1.isAssignableFrom(class2)) {
            class1 = class1.getSuperclass();
        }
        return class1;
    }

    /**
     * Resets the call count and total time for this listener
     */
    public void reset() {
        count = 0;
        totalTime = 0;
    }

    /**
     * Gets the total times this listener has been called
     *
     * @return Times this listener has been called
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets the total time calls to this listener have taken
     *
     * @return Total time for all calls of this listener
     */
    public long getTotalTime() {
        return totalTime;
    }

    /**
     * Gets the class of the events this listener handled. If it handled
     * multiple classes of event, the closest shared superclass will be
     * returned, such that for any event this listener has handled,
     * <code>this.getEventClass().isAssignableFrom(event.getClass())</code>
     * and no class <code>this.getEventClass().isAssignableFrom(clazz)
     * && this.getEventClass() != clazz &&
     * event.getClass().isAssignableFrom(clazz)</code> for all handled events.
     *
     * @return the event class handled by this RegisteredListener
     */
    public Class<? extends Event> getEventClass() {
        return eventClass;
    }

    /**
     * Gets whether this listener has handled multiple events, such that for
     * some two events, <code>eventA.getClass() != eventB.getClass()</code>.
     *
     * @return true if this listener has handled multiple events
     */
    public boolean hasMultiple() {
        return multiple;
    }
}