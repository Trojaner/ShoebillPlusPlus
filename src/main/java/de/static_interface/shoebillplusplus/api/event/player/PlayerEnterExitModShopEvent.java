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

import de.static_interface.shoebillplusplus.api.event.Cancellable;

public class PlayerEnterExitModShopEvent extends PlayerEvent<net.gtaun.shoebill.event.player.PlayerEnterExitModShopEvent>
                                         implements Cancellable {
    private boolean cancellable;
    private int enterExit;
    private int interiorId;

    public PlayerEnterExitModShopEvent(net.gtaun.shoebill.event.player.PlayerEnterExitModShopEvent base) {
        super(base);
        enterExit = getBase().getEnterExit();
        interiorId = getBase().getInteriorId();
    }

    @Override
    public boolean isCancelled() {
        return cancellable;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancellable = cancel;
    }

    public int getEnterExit() {
        return this.enterExit;
    }

    public int getInteriorId() {
        return this.interiorId;
    }
}
