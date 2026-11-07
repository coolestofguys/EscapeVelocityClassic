package data.scripts.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.listeners.WeaponBaseRangeModifier;

/**
 * EV-style "cannon vs turret": Laser Cannons and Proton Cannons gain extra range when they are
 * installed in a fixed (hardpoint) mount instead of a rotating turret.
 *
 * Attached to those weapons through "everyFrameEffect" in their .wpn files. On its first frame the
 * plugin registers itself as the ship's range listener (one per ship).
 *
 * Deliberately a single top-level class with no inner classes: the game failed to load the old
 * nested listener class (EVFixedMountRange$RangeListener) from the refit screen and crashed.
 */
public class EVFixedMountRange implements EveryFrameWeaponEffectPlugin, WeaponBaseRangeModifier {

    /** extra base range, in percent, for these weapons in hardpoint mounts */
    public static final float HARDPOINT_RANGE_BONUS_PERCENT = 25f;

    private boolean done = false;

    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        if (done) {
            return;
        }
        done = true;
        try {
            ShipAPI ship = weapon == null ? null : weapon.getShip();
            if (ship != null && !ship.hasListenerOfClass(EVFixedMountRange.class)) {
                ship.addListener(this);
            }
        } catch (Throwable t) {
            // never take the game down over a range bonus
        }
    }

    public float getWeaponBaseRangePercentMod(ShipAPI ship, WeaponAPI weapon) {
        if (weapon == null || weapon.getSlot() == null || !weapon.getSlot().isHardpoint()) {
            return 0f;
        }
        String id = weapon.getId();
        if ("ev_laser_cannon".equals(id) || "ev_proton_cannon".equals(id)) {
            return HARDPOINT_RANGE_BONUS_PERCENT;
        }
        return 0f;
    }

    public float getWeaponBaseRangeMultMod(ShipAPI ship, WeaponAPI weapon) {
        return 1f;
    }

    public float getWeaponBaseRangeFlatMod(ShipAPI ship, WeaponAPI weapon) {
        return 0f;
    }
}
