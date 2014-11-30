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

import de.static_interface.shoebillplusplus.api.*;
import de.static_interface.shoebillplusplus.api.event.amx.*;
import de.static_interface.shoebillplusplus.api.event.checkpoint.*;
import de.static_interface.shoebillplusplus.api.event.destroyable.*;
import de.static_interface.shoebillplusplus.api.event.dialog.*;
import de.static_interface.shoebillplusplus.api.event.menu.*;
import de.static_interface.shoebillplusplus.api.event.object.*;
import de.static_interface.shoebillplusplus.api.event.player.*;
import de.static_interface.shoebillplusplus.api.event.rcon.*;
import de.static_interface.shoebillplusplus.api.event.resource.*;
import de.static_interface.shoebillplusplus.api.event.server.*;
import de.static_interface.shoebillplusplus.api.event.service.*;
import de.static_interface.shoebillplusplus.api.event.vehicle.*;
import de.static_interface.shoebillplusplus.api.plugin.*;
import net.gtaun.shoebill.resource.*;
import net.gtaun.util.event.*;

@SuppressWarnings("CodeBlock2Expr")
public class ShoebillPlusPlusPlugin extends Plugin {

    private EventManagerNode eventManagerNode;
    private Thread primaryThread;
    private static ShoebillPlusPlusPlugin instance;

    public Thread getPrimaryThread() {
        return primaryThread;
    }

    public static ShoebillPlusPlusPlugin getInstance() {
        return instance;
    }

    @Override
    protected void onEnable() throws Throwable {
        primaryThread = Thread.currentThread();
        instance = this;
        new PlusServer().init();

        eventManagerNode = getEventManager().createChildNode();

        registerEvents(eventManagerNode, PlusServer.get().getPluginManager());
    }

    private void registerEvents(EventManagerNode eventManagerNode, PluginManager pluginManager) {
        Server server = PlusServer.get();

        /*
         * Amx Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.amx.AmxLoadEvent.class, (e) -> {
            pluginManager.callEvent(new AmxLoadEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.amx.AmxUnloadEvent.class, (e) -> {
            pluginManager.callEvent(new AmxUnloadEvent(e));
        });


        /*
         * Checkpoint Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.checkpoint.CheckpointEnterEvent.class, (e) -> {
            CheckpointEnterEvent event = new CheckpointEnterEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.checkpoint.CheckpointLeaveEvent.class, (e) -> {
            CheckpointLeaveEvent event = new CheckpointLeaveEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.checkpoint.RaceCheckpointEnterEvent.class, (e) -> {
            RaceCheckpointEnterEvent event = new RaceCheckpointEnterEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.checkpoint.RaceCheckpointLeaveEvent.class, (e) -> {
            RaceCheckpointLeaveEvent event = new RaceCheckpointLeaveEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });


        /*
         * Destroyable Event
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.destroyable.DestroyEvent.class, (e) -> {
            pluginManager.callEvent(new DestroyEvent(e));
        });


        /*
         * Dialog Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.dialog.DialogCloseEvent.class, (e) -> {
            DialogCloseEvent event = new DialogCloseEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.dialog.DialogResponseEvent.class, (e) -> {
            DialogResponseEvent event = new DialogResponseEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled() && !event.isProcessed()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.dialog.DialogShowEvent.class, (e) -> {
            DialogShowEvent event = new DialogShowEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });


        /*
         * Menu Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.menu.MenuExitedEvent.class, (e) -> {
            MenuExitedEvent event = new MenuExitedEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.menu.MenuSelectedEvent.class, (e) -> {
            MenuSelectedEvent event = new MenuSelectedEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });


        /*
         * Object Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.object.ObjectMovedEvent.class, (e) -> {
            pluginManager.callEvent(new ObjectMovedEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.object.PlayerObjectMovedEvent.class, (e) -> {
            pluginManager.callEvent(new PlayerObjectMovedEvent(e));
        });


        /*
         * Player Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerClickMapEvent.class, (e) -> {
            PlayerClickMapEvent event = new PlayerClickMapEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerClickPlayerEvent.class, (e) -> {
            PlayerClickPlayerEvent event = new PlayerClickPlayerEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerClickPlayerTextDrawEvent.class, (e) -> {
            PlayerClickPlayerTextDrawEvent event = new PlayerClickPlayerTextDrawEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled() && !event.isProcessed()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerClickTextDrawEvent.class, (e) -> {
            PlayerClickTextDrawEvent event = new PlayerClickTextDrawEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled() && !event.isProcessed()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerCommandEvent.class, (e) -> {
            PlayerCommandEvent event = new PlayerCommandEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled() && !event.isProcessed()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerConnectEvent.class, (e) -> {
            pluginManager.callEvent(new PlayerConnectEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerDeathEvent.class, (e) -> {
            PlayerDeathEvent event = new PlayerDeathEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerDisconnectEvent.class, (e) -> {
            pluginManager.callEvent(new PlayerDisconnectEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerEditAttachedObjectEvent.class, (e) -> {
            PlayerEditAttachedObjectEvent event = new PlayerEditAttachedObjectEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerEditObjectEvent.class, (e) -> {
            PlayerEditObjectEvent event = new PlayerEditObjectEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerEditPlayerObjectEvent.class, (e) -> {
            PlayerEditPlayerObjectEvent event = new PlayerEditPlayerObjectEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerEnterExitModShopEvent.class, (e) -> {
            PlayerEnterExitModShopEvent event = new PlayerEnterExitModShopEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerGiveDamageEvent.class, (e) -> {
            PlayerGiveDamageEvent event = new PlayerGiveDamageEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerInteriorChangeEvent.class, (e) -> {
            pluginManager.callEvent(new PlayerInteriorChangeEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerKeyStateChangeEvent.class, (e) -> {
            pluginManager.callEvent(new PlayerKeyStateChangeEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerKillEvent.class, (e) -> {
            PlayerKillEvent event = new PlayerKillEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerPickupEvent.class, (e) -> {
            PlayerPickupEvent event = new PlayerPickupEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerRequestClassEvent.class, (e) -> {
            PlayerRequestClassEvent event = new PlayerRequestClassEvent(e);
            pluginManager.callEvent(event);
            if (!event.isAllowed()) {
                e.disallow();
            } else if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerRequestSpawnEvent.class, (e) -> {
            PlayerRequestSpawnEvent event = new PlayerRequestSpawnEvent(e);
            pluginManager.callEvent(event);
            if (!event.isAllowed()) {
                e.disallow();
            } else if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerSelectObjectEvent.class, (e) -> {
            PlayerSelectObjectEvent event = new PlayerSelectObjectEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerSelectPlayerObjectEvent.class, (e) -> {
            PlayerSelectPlayerObjectEvent event = new PlayerSelectPlayerObjectEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerSpawnEvent.class, (e) -> {
            PlayerSpawnEvent event = new PlayerSpawnEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerStateChangeEvent.class, (e) -> {
            pluginManager.callEvent(new PlayerStateChangeEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerStreamInEvent.class, (e) -> {
            pluginManager.callEvent(new PlayerStreamInEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerStreamOutEvent.class, (e) -> {
            pluginManager.callEvent(new PlayerStreamOutEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerTakeDamageEvent.class, (e) -> {
            PlayerTakeDamageEvent event = new PlayerTakeDamageEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerTextEvent.class, (e) -> {
            PlayerTextEvent event = new PlayerTextEvent(e);
            pluginManager.callEvent(event);
            if (!event.isAllowed()) {
                e.disallow();
            } else if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerUpdateEvent.class, (e) -> {
            if(e.getPlayer().getUpdateCount() % server.getUpdateInterval() != 0) {
                return;
            }
            PlayerUpdateEvent event = new PlayerUpdateEvent(e);
            pluginManager.callEvent(event);
            if (!event.isAllowed()) {
                e.disallow();
            } else if (event.isCancelled()) {
                e.interrupt();
            }

        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.player.PlayerWeaponShotEvent.class, (e) -> {
            PlayerWeaponShotEvent event = new PlayerWeaponShotEvent(e);
            pluginManager.callEvent(event);
            if (!event.isAllowed()) {
                e.disallow();
            } else if (event.isCancelled()) {
                e.interrupt();
            }
        });


        /*
         * Rcon Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.rcon.RconCommandEvent.class, (e) -> {
            RconCommandEvent event = new RconCommandEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.rcon.RconLoginEvent.class, (e) -> {
            pluginManager.callEvent(new RconLoginEvent(e));
        });


        /*
         * Resource Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.resource.ResourceDisableEvent.class, (e) -> {
            pluginManager.callEvent(new ResourceDisableEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.resource.ResourceEnableEvent.class, (e) -> {
            pluginManager.callEvent(new ResourceEnableEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.resource.ResourceLoadEvent.class, (e) -> {
            pluginManager.callEvent(new ResourceLoadEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.resource.ResourceUnloadEvent.class, (e) -> {
            pluginManager.callEvent(new ResourceUnloadEvent(e));
        });


        /*
         * Server Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.server.IncomingConnectionEvent.class, (e) -> {
            pluginManager.callEvent(new IncomingConnectionEvent(e));
        });


        /*
         * Service Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.service.ServiceRegisterEvent.class, (e) -> {
            pluginManager.callEvent(new ServiceRegisterEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.service.ServiceUnregisterEvent.class, (e) -> {
            pluginManager.callEvent(new ServiceUnregisterEvent(e));
        });


        /*
         * Vehicle Events
         */
        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.TrailerUpdateEvent.class, (e) -> {
            TrailerUpdateEvent event = new TrailerUpdateEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.UnoccupiedVehicleUpdateEvent.class, (e) -> {
            pluginManager.callEvent(new UnoccupiedVehicleUpdateEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleCreateEvent.class, (e) -> {
            pluginManager.callEvent(new VehicleCreateEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleDeathEvent.class, (e) -> {
            VehicleDeathEvent event = new VehicleDeathEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleDestroyEvent.class, (e) -> {
            pluginManager.callEvent(new VehicleDestroyEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleEnterEvent.class, (e) -> {
            VehicleEnterEvent event = new VehicleEnterEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleExitEvent.class, (e) -> {
            VehicleExitEvent event = new VehicleExitEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleModEvent.class, (e) -> {
            pluginManager.callEvent(new VehicleModEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehiclePaintjobEvent.class, (e) -> {
            pluginManager.callEvent(new VehiclePaintjobEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleResprayEvent.class, (e) -> {
            pluginManager.callEvent(new VehicleResprayEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleSpawnEvent.class, (e) -> {
            VehicleSpawnEvent event = new VehicleSpawnEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleStreamInEvent.class, (e) -> {
            pluginManager.callEvent(new VehicleStreamInEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleStreamOutEvent.class, (e) -> {
            pluginManager.callEvent(new VehicleStreamOutEvent(e));
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleUpdateDamageEvent.class, (e) -> {
            VehicleUpdateDamageEvent event = new VehicleUpdateDamageEvent(e);
            pluginManager.callEvent(event);
            if (event.isCancelled()) {
                e.interrupt();
            }
        });

        eventManagerNode.registerHandler(net.gtaun.shoebill.event.vehicle.VehicleUpdateEvent.class, (e) -> {
            pluginManager.callEvent(new VehicleUpdateEvent(e));
        });
    }

    @Override
    protected void onDisable() throws Throwable {
        eventManagerNode.destroy();
        eventManagerNode = null;
        instance = null;
    }
}
