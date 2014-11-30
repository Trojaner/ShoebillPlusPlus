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

package de.static_interface.shoebillplusplus.api.event;

public class EventException extends Exception {
    private static final long serialVersionUID = 3532808232324183999L;
    private final Throwable cause;

    /**
     * Constructs a new EventException based on the given Exception
     *
     * @param throwable Exception that triggered this Exception
     */
    public EventException(Throwable throwable) {
        cause = throwable;
    }

    /**
     * Constructs a new EventException
     */
    public EventException() {
        cause = null;
    }

    /**
     * Constructs a new EventException with the given message
     *
     * @param cause The exception that caused this
     * @param message The message
     */
    public EventException(Throwable cause, String message) {
        super(message);
        this.cause = cause;
    }

    /**
     * Constructs a new EventException with the given message
     *
     * @param message The message
     */
    public EventException(String message) {
        super(message);
        cause = null;
    }

    /**
     * If applicable, returns the Exception that triggered this Exception
     *
     * @return Inner exception, or null if one does not exist
     */
    @Override
    public Throwable getCause() {
        return cause;
    }
}