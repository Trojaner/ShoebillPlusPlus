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

package de.static_interface.shoebillplusplus.api.event.object;

import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.PlayerObject;

public class PlayerObjectMovedEvent extends ObjectEvent<net.gtaun.shoebill.event.object.PlayerObjectMovedEvent>{
    public PlayerObjectMovedEvent(net.gtaun.shoebill.event.object.PlayerObjectMovedEvent base) {
        super(base);
    }

    public Player getPlayer() {
        return this.getObject().getPlayer();
    }

    @Override
    public PlayerObject getObject() {
        return (PlayerObject) super.getObject();
    }
}
