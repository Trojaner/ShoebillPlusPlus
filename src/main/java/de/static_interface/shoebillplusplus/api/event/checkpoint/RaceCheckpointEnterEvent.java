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

package de.static_interface.shoebillplusplus.api.event.checkpoint;

import de.static_interface.shoebillplusplus.api.event.Cancellable;

public class RaceCheckpointEnterEvent extends RaceCheckpointEvent<net.gtaun.shoebill.event.checkpoint.RaceCheckpointEnterEvent> implements Cancellable {
    private boolean cancelled;

    public RaceCheckpointEnterEvent(net.gtaun.shoebill.event.checkpoint.RaceCheckpointEnterEvent base) {
        super(base);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
