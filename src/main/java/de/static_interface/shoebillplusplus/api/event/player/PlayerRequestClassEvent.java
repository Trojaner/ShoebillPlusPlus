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

package de.static_interface.shoebillplusplus.api.event.player;

import de.static_interface.shoebillplusplus.api.event.Allowable;
import de.static_interface.shoebillplusplus.api.event.Cancellable;

public class PlayerRequestClassEvent extends PlayerEvent<net.gtaun.shoebill.event.player.PlayerRequestClassEvent>
                                     implements Cancellable, Allowable {
    private boolean cancelled;
    private int classId;
    private boolean allowed;

    public PlayerRequestClassEvent(net.gtaun.shoebill.event.player.PlayerRequestClassEvent base) {
        super(base);
        classId = getBase().getClassId();
        allowed = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public void allow() {
        allowed = true;
        setCancelled(true);
    }

    public void disallow() {
        allowed = false;
        setCancelled(true);
    }

    public boolean isAllowed() {
        return allowed;
    }

    public int getResponse() {
        return getBase().getResponse();
    }

    public int getClassId() {
        return this.classId;
    }
}
