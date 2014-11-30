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

package de.static_interface.shoebillplusplus.api.event.server;

import de.static_interface.shoebillplusplus.api.event.ShoebillEvent;

public class IncomingConnectionEvent extends ShoebillEvent<net.gtaun.shoebill.event.server.IncomingConnectionEvent> {
    private final int playerId;
    private final String ipAddress;
    private final int port;

    public IncomingConnectionEvent(net.gtaun.shoebill.event.server.IncomingConnectionEvent base) {
        super(base);

        playerId = getBase().getPlayerId();
        ipAddress = getBase().getIpAddress();
        port = getBase().getPort();
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }
}
