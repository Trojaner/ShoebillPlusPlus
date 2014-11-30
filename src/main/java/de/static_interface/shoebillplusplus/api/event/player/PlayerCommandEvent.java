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
import de.static_interface.shoebillplusplus.api.event.Processable;

public class PlayerCommandEvent extends PlayerEvent<net.gtaun.shoebill.event.player.PlayerCommandEvent>
                                implements Cancellable, Processable {
    private boolean cancelled;
    private boolean proceesed;
    String command;
    public PlayerCommandEvent(net.gtaun.shoebill.event.player.PlayerCommandEvent base) {
        super(base);
        command = getBase().getCommand();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        if(proceesed) {
            return;
        }
        cancelled = cancel;
    }

    @Override
    public void setProcessed() {
        getBase().setProcessed();
        setCancelled(true);
        proceesed = true;
    }

    @Override
    public boolean isProcessed() {
        return proceesed;
    }

    @Override
    public int getResponse() {
        return getBase().getResponse();
    }

    public String getCommand() {
        return command;
    }
}
