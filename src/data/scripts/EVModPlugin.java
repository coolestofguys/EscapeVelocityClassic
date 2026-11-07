package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import data.scripts.world.EVMarketShips;
import data.scripts.world.EVSectorGen;

/**
 * Escape Velocity Classic - mod plugin.
 *
 * Shipped precompiled in jars/ev_classic.jar (source kept in src/ for reference).
 * Kept to plain Java (no lambdas/generics/annotations) so it can also run as a loose script.
 */
public class EVModPlugin extends BaseModPlugin {

    public static final String CONFED = "ev_confederation";
    public static final String REBEL = "ev_rebellion";

    public void onNewGame() {
        SectorAPI sector = Global.getSector();
        // In a Nexerelin random sector, Nexerelin places the factions itself.
        if (!isCorvusMode()) {
            return;
        }
        new EVSectorGen().generate(sector);
        setRelations(sector);
    }

    /** market ship-stock rules (exclusives only in military markets; Levo sells mostly EV ships) */
    public void onGameLoad(boolean newGame) {
        if (!Global.getSector().getListenerManager().hasListenerOfClass(EVMarketShips.class)) {
            Global.getSector().getListenerManager().addListener(new EVMarketShips());
        }
    }

    private static void setRelations(SectorAPI sector) {
        FactionAPI confed = sector.getFaction(CONFED);
        FactionAPI rebel = sector.getFaction(REBEL);
        if (confed == null || rebel == null) {
            return;
        }
        // the war
        confed.setRelationship(REBEL, -1f);   // -100: total war
        // everyone else
        confed.setRelationship("pirates", -0.65f);
        confed.setRelationship("luddic_path", -0.65f);
        confed.setRelationship("independent", 0.1f);
        rebel.setRelationship("pirates", -0.3f);
        rebel.setRelationship("luddic_path", -0.5f);
        rebel.setRelationship("independent", 0.1f);
    }

    /** true when not running Nexerelin, or when Nexerelin is using the normal (Corvus) sector. */
    private static boolean isCorvusMode() {
        if (!Global.getSettings().getModManager().isModEnabled("nexerelin")) {
            return true;
        }
        try {
            ClassLoader cl = Global.getSettings().getScriptClassLoader();
            Class mgrClass = cl.loadClass("exerelin.campaign.SectorManager");
            Object mgr = mgrClass.getMethod("getManager", new Class[0]).invoke(null, new Object[0]);
            if (mgr != null) {
                Object result = mgrClass.getMethod("isCorvusMode", new Class[0]).invoke(mgr, new Object[0]);
                return ((Boolean) result).booleanValue();
            }
            Class setupClass = cl.loadClass("exerelin.campaign.ExerelinSetupData");
            Object setup = setupClass.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            return setupClass.getField("corvusMode").getBoolean(setup);
        } catch (Throwable t) {
            // could not read Nexerelin's sector mode: assume the normal sector
            return true;
        }
    }
}
