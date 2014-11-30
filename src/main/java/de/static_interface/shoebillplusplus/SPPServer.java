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

package de.static_interface.shoebillplusplus;

import de.static_interface.shoebillplusplus.Warning.WarningState;
import de.static_interface.shoebillplusplus.api.Server;
import de.static_interface.shoebillplusplus.api.plugin.PluginManager;
import de.static_interface.shoebillplusplus.api.plugin.SimplePluginManager;
import org.slf4j.Logger;

public class SPPServer implements Server {
    private static SPPServer instance;

    protected SPPServer(){}

    private PluginManager pluginManager;
    private WarningState warningState = WarningState.DEFAULT;

    public void init() {
        instance = this;
        pluginManager = new SimplePluginManager(this);
    }

    public static SPPServer get() {
        return instance;
    }

    @Override
    public Logger getLogger() {
        return ShoebillPlusPlusPlugin.getInstance().getLogger();
    }

    @Override
    public boolean isPrimaryThread() {
        return Thread.currentThread().equals(ShoebillPlusPlusPlugin.getInstance().getPrimaryThread());
    }

    @Override
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public WarningState getWarningState() {
        return warningState;
    }

    @Override
    public String getVersion() {
        return ShoebillPlusPlusPlugin.getInstance().getDescription().getVersion();
    }

    @Override
    public void shutdown() {
        net.gtaun.shoebill.object.Server.get().sendRconCommand("exit"); //todo: bad implementation?
    }

    @Override
    public String getServerName() {
        throw new RuntimeException("Currently not supported"); //todo: implement this
    }

    @Override
    public String getGamemodeName() {
        return net.gtaun.shoebill.object.Server.get().getGamemodeText();
    }
}
