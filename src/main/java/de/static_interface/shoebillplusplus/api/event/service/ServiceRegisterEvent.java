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

package de.static_interface.shoebillplusplus.api.event.service;

import net.gtaun.shoebill.resource.Resource;
import net.gtaun.shoebill.service.Service;
import net.gtaun.shoebill.service.ServiceEntry;

public class ServiceRegisterEvent extends ServiceEvent<net.gtaun.shoebill.event.service.ServiceRegisterEvent> {
    private ServiceEntry previousServiceEntry;

    public ServiceRegisterEvent(net.gtaun.shoebill.event.service.ServiceRegisterEvent base) {
        super(base);
        previousServiceEntry = base.getPreviousServiceEntry();
    }

    public boolean isReregistering() {
        return this.getPreviousServiceEntry() != null;
    }

    public ServiceEntry getPreviousServiceEntry() {
        return this.previousServiceEntry;
    }

    public Resource getPreviousProvider() {
        return this.previousServiceEntry.getProvider();
    }

    public Class<? extends Service> getPreviousType() {
        return this.previousServiceEntry.getType();
    }

    public Service getPreviousService() {
        return this.previousServiceEntry.getService();
    }
}
