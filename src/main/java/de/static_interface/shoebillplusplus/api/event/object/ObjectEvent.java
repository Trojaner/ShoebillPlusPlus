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

import de.static_interface.shoebillplusplus.api.event.ShoebillEvent;
import net.gtaun.shoebill.object.SampObject;

public abstract class ObjectEvent<T extends net.gtaun.shoebill.event.object.ObjectEvent> extends ShoebillEvent<T> {
    private SampObject object;

    public ObjectEvent(T base) {
        super(base);
        object = getBase().getObject();
    }

    public SampObject getObject() {
        return this.object;
    }
}
