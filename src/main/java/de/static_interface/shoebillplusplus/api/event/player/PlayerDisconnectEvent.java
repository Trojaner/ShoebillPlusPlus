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

import net.gtaun.shoebill.constant.DisconnectReason;

public class PlayerDisconnectEvent extends PlayerEvent<net.gtaun.shoebill.event.player.PlayerDisconnectEvent> {

    private final DisconnectReason reason;

    public PlayerDisconnectEvent(net.gtaun.shoebill.event.player.PlayerDisconnectEvent base) {
        super(base);
        this.reason = getBase().getReason();
    }

    public DisconnectReason getReason() {
        return reason;
    }
}
