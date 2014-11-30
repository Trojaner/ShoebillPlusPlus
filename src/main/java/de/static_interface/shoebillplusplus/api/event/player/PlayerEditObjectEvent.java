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
import net.gtaun.shoebill.constant.ObjectEditResponse;
import net.gtaun.shoebill.object.SampObject;

public class PlayerEditObjectEvent extends PlayerEvent<net.gtaun.shoebill.event.player.PlayerEditObjectEvent> implements Cancellable {
    private boolean cancelled;
    private SampObject object;
    private ObjectEditResponse editResponse;

    public PlayerEditObjectEvent(net.gtaun.shoebill.event.player.PlayerEditObjectEvent base) {
        super(base);
        object = getBase().getObject();
        editResponse = getBase().getEditResponse();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public SampObject getObject() {
        return object;
    }

    public ObjectEditResponse getEditResponse() {
        return editResponse;
    }
}
