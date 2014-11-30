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

import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.object.Player;

public class UnoccupiedVehicleUpdateEvent extends VehicleEvent<net.gtaun.shoebill.event.vehicle.UnoccupiedVehicleUpdateEvent> {
    private Player player;
    private Location newLocation;
    private Vector3D velocity;

    public UnoccupiedVehicleUpdateEvent(net.gtaun.shoebill.event.vehicle.UnoccupiedVehicleUpdateEvent base) {
        super(base);
        player = base.getPlayer();
        newLocation = base.getNewLocation();
        velocity = base.getVelocity();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Location getNewLocation() {
        return this.newLocation.clone();
    }

    public Vector3D getVelocity() {
        return this.velocity.clone();
    }
}
