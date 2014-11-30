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

import java.util.concurrent.*;

class FutureImpl<T> extends TaskImpl implements Future<T> {

    private final Callable<T> callable;
    private T value;
    private Exception exception = null;

    FutureImpl(final Callable<T> callable, final Resource resource, final int id) {
        super(resource, null, id, -1l);
        this.callable = callable;
    }

    public synchronized boolean cancel(final boolean mayInterruptIfRunning) {
        if (getPeriod() != -1l) {
            return false;
        }
        setPeriod(-2l);
        return true;
    }

    public boolean isCancelled() {
        return getPeriod() == -2l;
    }

    public boolean isDone() {
        final long period = this.getPeriod();
        return period != -1l && period != -3l;
    }

    public T get() throws CancellationException, InterruptedException, ExecutionException {
        try {
            return get(0, TimeUnit.MILLISECONDS);
        } catch (final TimeoutException e) {
            throw new Error(e);
        }
    }

    public synchronized T get(long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        timeout = unit.toMillis(timeout);
        long period = this.getPeriod();
        long timestamp = timeout > 0 ? System.currentTimeMillis() : 0l;
        while (true) {
            if (period == -1l || period == -3l) {
                this.wait(timeout);
                period = this.getPeriod();
                if (period == -1l || period == -3l) {
                    if (timeout == 0l) {
                        continue;
                    }
                    timeout += timestamp - (timestamp = System.currentTimeMillis());
                    if (timeout > 0) {
                        continue;
                    }
                    throw new TimeoutException();
                }
            }
            if (period == -2l) {
                throw new CancellationException();
            }
            if (period == -4l) {
                if (exception == null) {
                    return value;
                }
                throw new ExecutionException(exception);
            }
            throw new IllegalStateException("Expected " + -1l + " to " + -4l + ", got " + period);
        }
    }

    @Override
    public void run() {
        synchronized (this) {
            if (getPeriod() == -2l) {
                return;
            }
            setPeriod(-3l);
        }
        try {
            value = callable.call();
        } catch (final Exception e) {
            exception = e;
        } finally {
            synchronized (this) {
                setPeriod(-4l);
                this.notifyAll();
            }
        }
    }

    synchronized boolean cancel0() {
        if (getPeriod() != -1l) {
            return false;
        }
        setPeriod(-2l);
        notifyAll();
        return true;
    }
}