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

package de.static_interface.shoebillplusplus.api;

import de.static_interface.shoebillplusplus.Warning.WarningState;
import de.static_interface.shoebillplusplus.api.plugin.PluginManager;
import org.slf4j.Logger;

public interface Server {
    /**
     * Returns the primary logger associated with this server instance.
     *
     * @return Logger associated with this server
     */
    public Logger getLogger();

    /**
     * Checks the current thread against the expected primary thread for the
     * server.
     * <p>
     * <b>Note:</b> this method should not be used to indicate the current
     * synchronized state of the runtime. A current thread matching the main
     * thread indicates that it is synchronized, but a mismatch <b>does not
     * preclude</b> the same assumption.
     *
     * @return true if the current thread matches the expected primary thread,
     *     false otherwise
     */
    public boolean isPrimaryThread();

    /**
     * Gets the plugin manager for interfacing with plugins.
     *
     * @return a plugin manager for this Server instance
     */
    public PluginManager getPluginManager();

    /**
     * Gets the current warning state for the server.
     *
     * @return the configured warning state
     */
    public WarningState getWarningState();

    /**
     * Gets the version string of this server implementation.
     *
     * @return version of this server implementation
     */
    public String getVersion();

    /**
     * Shutdowns the server, stopping everything.
     */
    public void shutdown();

    /**
     * Get the name of this server.
     *
     * @return the name of this server
     */
    public String getServerName();

    /**
     * Get the name of the current gamemode
     *
     * @return the name of the current gamemode
     */
    public String getGamemodeName();
}
