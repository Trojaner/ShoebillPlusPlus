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

package de.static_interface.shoebillplusplus.api.event.vehicle;

import net.gtaun.shoebill.object.Player;

public class VehicleUpdateEvent extends VehicleEvent<net.gtaun.shoebill.event.vehicle.VehicleUpdateEvent> {
    private Player player;
    private int playerSeat;

    public VehicleUpdateEvent(net.gtaun.shoebill.event.vehicle.VehicleUpdateEvent base) {
        super(base);
        player = getBase().getPlayer();
        playerSeat = getBase().getPlayerSeat();
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerSeat() {
        return playerSeat;
    }
}
