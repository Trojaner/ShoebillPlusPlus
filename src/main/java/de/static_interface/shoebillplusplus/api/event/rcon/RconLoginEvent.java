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

package de.static_interface.shoebillplusplus.api.event.rcon;

import de.static_interface.shoebillplusplus.api.event.ShoebillEvent;

public class RconLoginEvent extends ShoebillEvent<net.gtaun.shoebill.event.rcon.RconLoginEvent> {
    private String ip;
    private String password;
    private boolean success;

    public RconLoginEvent(net.gtaun.shoebill.event.rcon.RconLoginEvent base) {
        super(base);
        ip = getBase().getIp();
        password = getBase().getPassword();
        success = getBase().isSuccess();
    }

    public String getIp() {
        return ip;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSuccess() {
        return success;
    }
}
