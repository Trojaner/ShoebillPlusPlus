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

package de.static_interface.shoebillplusplus.api.event.destroyable;

import de.static_interface.shoebillplusplus.api.event.ShoebillEvent;
import net.gtaun.shoebill.object.Destroyable;

public class DestroyEvent extends ShoebillEvent<net.gtaun.shoebill.event.destroyable.DestroyEvent> {
    private Destroyable destroyable;

    public DestroyEvent(net.gtaun.shoebill.event.destroyable.DestroyEvent base) {
        super(base);
        destroyable = getBase().getDestroyable();
    }

    public Destroyable getDestroyable() {
        return this.destroyable;
    }
}
