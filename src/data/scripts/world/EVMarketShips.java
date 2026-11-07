package data.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.SubmarketInteractionListener;
import com.fs.starfarer.api.campaign.listeners.SubmarketUpdateListener;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.DModManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Stock rules for EV markets (user's request):
 *  - Confederation and Rebellion markets sell their faction-exclusive hulls, weapon (Neutron
 *    Blaster / Mass Driver) and fighter LPC (Patrol Ship / Manta) only in the military market. The
 *    hulls carry the vanilla "req_military" tag; this class also removes the weapon and LPC (and
 *    any exclusive hull) from the open and black markets.
 *  - The Levo markets (Levo I and Blackthorne) sell mostly the shared EV ships, plus shared EV
 *    weapons and LPCs: each time the game restocks their open or black market, all but MAX_OTHER
 *    non-EV ships are removed and empty EV hulls, EV weapons and LPCs are added.
 *
 * Runs when the player opens a market (and when the game reports a submarket restock). Plain Java
 * with no inner classes (the game failed to load a nested class once and crashed).
 */
public class EVMarketShips implements SubmarketInteractionListener, SubmarketUpdateListener {

    public static final String CONFED = "ev_confederation";
    public static final String REBEL = "ev_rebellion";

    public static final String[] CONFED_ONLY = {"ev_gunboat", "ev_confed_frigate", "ev_confed_cruiser"};
    public static final String[] REBEL_ONLY = {"ev_rebel_destroyer", "ev_escort_carrier", "ev_rebel_cruiser"};

    /** the shared EV hulls Levo stocks, with relative weights */
    public static final String[] LEVO_HULLS = {"ev_shuttlecraft", "ev_courier", "ev_clipper", "ev_defender",
            "ev_rapier", "ev_light_freighter", "ev_corvette", "ev_argosy", "ev_bulk_freighter", "ev_kestrel"};
    public static final float[] LEVO_WEIGHTS = {10f, 8f, 10f, 10f, 7f, 8f, 6f, 6f, 4f, 3f};
    public static final String[] LEVO_MARKETS = {"ev_levo_market", "ev_blackthorne_market"};
    public static final int MAX_OTHER = 2;

    public static final String[] SHARED_WEAPONS = {"ev_laser_cannon", "ev_proton_cannon", "ev_missile_rack",
            "ev_heavy_rocket", "ev_torpedo"};
    public static final int[] SHARED_WEAPON_STACK = {6, 3, 6, 6, 3};
    public static final String[] SHARED_WINGS = {"ev_hawk_wing", "ev_lightning_wing"};
    public static final String[] CONFED_ITEMS_W = {"ev_neutron_blaster"};
    public static final String[] CONFED_ITEMS_F = {"ev_patrol_wing"};
    public static final String[] REBEL_ITEMS_W = {"ev_mass_driver"};
    public static final String[] REBEL_ITEMS_F = {"ev_manta_wing"};

    /** per market+submarket: the ship ids seen after our last pass, to tell a fresh restock apart */
    private final Map lastSeen = new HashMap();
    private final Random random = new Random();

    public void reportPlayerOpenedSubmarket(SubmarketAPI submarket,
                                            SubmarketInteractionListener.SubmarketInteractionType type) {
        process(submarket);
    }

    public void reportSubmarketCargoAndShipsUpdated(SubmarketAPI submarket) {
        process(submarket);
    }

    private void process(SubmarketAPI submarket) {
        try {
            if (submarket == null || submarket.getMarket() == null) {
                return;
            }
            String spec = submarket.getSpecId();
            if (!"open_market".equals(spec) && !"black_market".equals(spec)) {
                return;                                  // military markets and storage are left alone
            }
            CargoAPI cargo = submarket.getCargoNullOk();
            if (cargo == null || cargo.getMothballedShips() == null) {
                return;
            }
            FleetDataAPI ships = cargo.getMothballedShips();
            MarketAPI market = submarket.getMarket();
            String faction = market.getFactionId();
            if (CONFED.equals(faction)) {
                removeHulls(ships, CONFED_ONLY);
                removeItems(cargo, CONFED_ITEMS_W, CONFED_ITEMS_F);
            } else if (REBEL.equals(faction)) {
                removeHulls(ships, REBEL_ONLY);
                removeItems(cargo, REBEL_ITEMS_W, REBEL_ITEMS_F);
            }
            if (isLevo(market.getId())) {
                stockLevo(market, spec, cargo, ships);
            }
        } catch (Throwable t) {
            // never break the trade screen over ship stock
        }
    }

    private static boolean isLevo(String marketId) {
        for (int i = 0; i < LEVO_MARKETS.length; i++) {
            if (LEVO_MARKETS[i].equals(marketId)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hullIn(FleetMemberAPI m, String[] hulls) {
        if (m.getHullSpec() == null) {
            return false;
        }
        String id = m.getHullSpec().getBaseHullId();
        if (id == null) {
            id = m.getHullId();
        }
        for (int i = 0; i < hulls.length; i++) {
            if (hulls[i].equals(id) || (m.getHullId() != null && m.getHullId().startsWith(hulls[i]))) {
                return true;
            }
        }
        return false;
    }

    private static void removeHulls(FleetDataAPI ships, String[] hulls) {
        List members = ships.getMembersListCopy();
        for (int i = 0; i < members.size(); i++) {
            FleetMemberAPI m = (FleetMemberAPI) members.get(i);
            if (hullIn(m, hulls)) {
                ships.removeFleetMember(m);
            }
        }
    }

    private static void removeItems(CargoAPI cargo, String[] weapons, String[] wings) {
        for (int i = 0; i < weapons.length; i++) {
            int n = cargo.getNumWeapons(weapons[i]);
            if (n > 0) {
                cargo.removeWeapons(weapons[i], n);
            }
        }
        for (int i = 0; i < wings.length; i++) {
            int n = cargo.getNumFighters(wings[i]);
            if (n > 0) {
                cargo.removeFighters(wings[i], n);
            }
        }
    }

    private void stockLevo(MarketAPI market, String spec, CargoAPI cargo, FleetDataAPI ships) {
        String key = market.getId() + "/" + spec;
        List members = ships.getMembersListCopy();
        Set seen = (Set) lastSeen.get(key);
        boolean restocked = !members.isEmpty();
        if (seen != null) {
            for (int i = 0; i < members.size(); i++) {
                if (seen.contains(((FleetMemberAPI) members.get(i)).getId())) {
                    restocked = false;                   // same stock as last time: already done
                    break;
                }
            }
        }
        if (restocked) {
            // keep only a couple of non-EV ships
            int others = 0;
            for (int i = 0; i < members.size(); i++) {
                FleetMemberAPI m = (FleetMemberAPI) members.get(i);
                if (hullIn(m, LEVO_HULLS)) {
                    continue;
                }
                others++;
                if (others > MAX_OTHER) {
                    ships.removeFleetMember(m);
                }
            }
            int add = 4 + market.getSize() / 2 + random.nextInt(3);
            boolean black = "black_market".equals(spec);
            for (int i = 0; i < add; i++) {
                // "_Hull" is the empty variant the game makes for every hull: ships are sold
                // unarmed, like vanilla market ships
                addShip(ships, pickLevoHull() + "_Hull", black);
            }
            // shared EV weapons and fighter LPCs
            int stacks = 3 + random.nextInt(3);
            for (int i = 0; i < stacks; i++) {
                int w = random.nextInt(SHARED_WEAPONS.length);
                cargo.addWeapons(SHARED_WEAPONS[w], 1 + random.nextInt(SHARED_WEAPON_STACK[w]));
            }
            int wings = 1 + random.nextInt(2);
            for (int i = 0; i < wings; i++) {
                cargo.addFighters(SHARED_WINGS[random.nextInt(SHARED_WINGS.length)], 1 + random.nextInt(2));
            }
        }
        Set now = new HashSet();
        List after = ships.getMembersListCopy();
        for (int i = 0; i < after.size(); i++) {
            now.add(((FleetMemberAPI) after.get(i)).getId());
        }
        lastSeen.put(key, now);
    }

    private String pickLevoHull() {
        float total = 0f;
        for (int i = 0; i < LEVO_WEIGHTS.length; i++) {
            total += LEVO_WEIGHTS[i];
        }
        float r = random.nextFloat() * total;
        for (int i = 0; i < LEVO_HULLS.length; i++) {
            r -= LEVO_WEIGHTS[i];
            if (r <= 0f) {
                return LEVO_HULLS[i];
            }
        }
        return LEVO_HULLS[0];
    }

    /** like the vanilla market stock: mothballed, 50% CR, a few D-mods on second-hand hulls */
    private void addShip(FleetDataAPI ships, String variantId, boolean blackMarket) {
        FleetMemberAPI m = Global.getFactory().createFleetMember(FleetMemberType.SHIP, variantId);
        if (m == null) {
            return;
        }
        int dmods = random.nextFloat() < (blackMarket ? 0.7f : 0.4f) ? 1 + random.nextInt(2) : 0;
        if (dmods > 0) {
            DModManager.setDHull(m.getVariant());
            DModManager.addDMods(m, true, dmods, random);
        }
        m.getRepairTracker().setMothballed(true);
        m.getRepairTracker().setCR(0.5f);
        ships.addFleetMember(m);
    }

    /** List of ids, for debugging in the console */
    public List describe() {
        return new ArrayList(lastSeen.keySet());
    }
}
