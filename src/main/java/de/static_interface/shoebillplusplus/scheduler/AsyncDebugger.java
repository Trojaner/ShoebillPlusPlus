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


import net.gtaun.shoebill.resource.*;

class AsyncDebugger {
    private AsyncDebugger next = null;
    private final int expiry;
    private final Resource resource;
    private final Class<? extends Runnable> clazz;

    AsyncDebugger(final int expiry, final  Resource resource, final Class<? extends Runnable> clazz) {
        this.expiry = expiry;
        this.resource = resource;
        this.clazz = clazz;

    }

    final AsyncDebugger getNextHead(final int time) {
        AsyncDebugger next, current = this;
        while (time > current.expiry && (next = current.next) != null) {
            current = next;
        }
        return current;
    }

    final AsyncDebugger setNext(final AsyncDebugger next) {
        return this.next = next;
    }

    StringBuilder debugTo(final StringBuilder string) {
        for (AsyncDebugger next = this; next != null; next = next.next) {
            string.append(next.resource.getDescription().getName()).append(':').append(next.clazz.getName()).append('@').append(next.expiry).append(',');
        }
        return string;
    }
}