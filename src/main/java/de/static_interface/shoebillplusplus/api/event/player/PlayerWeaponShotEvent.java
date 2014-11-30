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

import de.static_interface.shoebillplusplus.api.event.Allowable;
import de.static_interface.shoebillplusplus.api.event.Cancellable;
import net.gtaun.shoebill.constant.BulletHitType;
import net.gtaun.shoebill.constant.WeaponModel;
import net.gtaun.shoebill.data.Vector3D;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.PlayerObject;
import net.gtaun.shoebill.object.SampObject;

public class PlayerWeaponShotEvent extends PlayerEvent<net.gtaun.shoebill.event.player.PlayerWeaponShotEvent>
                                   implements Cancellable, Allowable {

    private boolean cancelled;
    private boolean allowed;
    private final WeaponModel weapon;
    private final BulletHitType hitType;
    private final int hitId;
    private final Vector3D position;

    public PlayerWeaponShotEvent(net.gtaun.shoebill.event.player.PlayerWeaponShotEvent base) {
        super(base);
        allowed = true;
        weapon = getBase().getWeapon();
        hitType = getBase().getHitType();
        hitId = getBase().getHitPlayer().getId();
        position = getBase().getPosition();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public void allow() {
        allowed = true;
        setCancelled(true);
    }

    @Override
    public void disallow() {
        allowed = false;
        setCancelled(true);
    }

    @Override
    public boolean isAllowed() {
        return allowed;
    }

    @Override
    public int getResponse() {
        return getBase().getResponse();
    }

    public WeaponModel getWeapon() {
        return weapon;
    }

    public BulletHitType getHitType() {
        return hitType;
    }

    public Vector3D getPosition() {
        return position.clone();
    }

    public SampObject getHitObject() {
        return SampObject.get(this.hitId);
    }

    public PlayerObject getHitPlayerObject() {
        return PlayerObject.get(this.getPlayer(), this.hitId);
    }

    public Player getHitPlayer() {
        return Player.get(this.hitId);
    }
}
