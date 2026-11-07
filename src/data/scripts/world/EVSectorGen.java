package data.scripts.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.JumpPointAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;

import java.awt.Color;
import java.util.List;

/**
 * Builds the three Escape Velocity systems: Antares (Confederation), Satori (Rebellion) and
 * Levo (neutral). Positions are in hyperspace units; the vanilla core worlds sit roughly
 * between x = -20000 and x = 10000, so these systems form a separate cluster to the east.
 *
 * Shipped precompiled in jars/ev_classic.jar; plain Java so it can also run as a loose script.
 */
public class EVSectorGen implements SectorGeneratorPlugin {

    public static final String CONFED = "ev_confederation";
    public static final String REBEL = "ev_rebellion";
    public static final String INDIE = "independent";

    // ---- move the whole cluster by editing these ----
    public static final float ANTARES_X = 24000f, ANTARES_Y = -9000f;
    public static final float SATORI_X = 37000f, SATORI_Y = 5000f;
    public static final float LEVO_X = 30000f, LEVO_Y = -2000f;

    public void generate(SectorAPI sector) {
        generateAntares(sector);
        generateSatori(sector);
        generateLevo(sector);
    }

    // ------------------------------------------------------------------ Antares (Confederation)
    private void generateAntares(SectorAPI sector) {
        StarSystemAPI system = sector.createStarSystem("Antares");
        system.getLocation().set(ANTARES_X, ANTARES_Y);
        system.setBackgroundTextureFilename("graphics/backgrounds/background4.jpg");
        PlanetAPI star = system.initStar("ev_antares_star", "star_red_giant", 1000f, ANTARES_X, ANTARES_Y, 600f);
        star.setName("Antares");

        // New Providence: peaceful terran mining and farming world of the Astex Mining Corporation
        PlanetAPI providence = system.addPlanet("ev_new_providence", star, "New Providence", "terran",
                40f, 170f, 4200f, 320f);
        providence.setCustomDescriptionId("ev_planet_new_providence");
        // Antares Station: the small Confederate orbital garrison and refuelling base over New Providence
        SectorEntityToken station = system.addCustomEntity("ev_antares_station", "Antares Station",
                "station_side00", CONFED);
        station.setCircularOrbitPointingDown(providence, 90f, 420f, 30f);
        station.setCustomDescriptionId("ev_station_antares");

        // Luna: New Providence's only moon, a bright white barren world holding the Terran Naval Yards
        PlanetAPI luna = system.addPlanet("ev_luna", providence, "Luna", "barren-bombarded", 30f, 55f, 760f, 40f);
        luna.setCustomDescriptionId("ev_planet_luna");
        tint(luna, new Color(255, 255, 255), new Color(255, 255, 255, 0));

        // P-6564: uninhabited, irradiated gas giant under a corrosive, deep red atmosphere
        PlanetAPI p6564 = system.addPlanet("ev_p6564", star, "P-6564", "gas_giant", 210f, 320f, 7600f, 700f);
        p6564.setCustomDescriptionId("ev_planet_p6564");
        tint(p6564, new Color(215, 45, 40), new Color(255, 60, 40, 200));
        p6564.getMarket().addCondition("volatiles_diffuse");
        p6564.getMarket().addCondition("irradiated");
        p6564.getMarket().addCondition("toxic_atmosphere");
        // Ruby: P-6564's red moon, the Confederation's main military base and weapons testing ground
        PlanetAPI ruby = system.addPlanet("ev_ruby", p6564, "Ruby", "barren", 0f, 70f, 700f, 45f);
        ruby.setCustomDescriptionId("ev_planet_ruby");
        tint(ruby, new Color(255, 70, 60), new Color(255, 50, 40, 160));

        system.addRingBand(star, "misc", "rings_asteroids0", 256f, 2, Color.white, 256f, 5900f, 220f, null, null);
        system.addAsteroidBelt(star, 120, 5900f, 500f, 150f, 250f, "asteroid_belt", "Antares Belt");

        addMarket(CONFED, ruby, "Ruby", 6,
                new String[] {"population_6", "thin_atmosphere", "low_gravity", "cold", "ore_moderate",
                        "rare_ore_moderate", "regional_capital", "headquarters"},
                new String[] {"population", "megaport", "highcommand", "orbitalworks", "starfortress_mid",
                        "heavybatteries", "planetaryshield", "waystation"},
                true);
        addMarket(CONFED, providence, "New Providence", 7,
                new String[] {"population_7", "habitable", "mild_climate", "farmland_rich", "organics_common",
                        "ore_abundant", "rare_ore_moderate"},
                new String[] {"population", "spaceport", "farming", "mining", "refining", "lightindustry",
                        "commerce", "grounddefenses"},
                false);
        addMarket(CONFED, luna, "Luna", 4,
                new String[] {"population_4", "no_atmosphere", "low_gravity", "cold", "ore_sparse"},
                new String[] {"population", "spaceport", "orbitalworks", "militarybase", "battlestation_mid",
                        "grounddefenses"},
                true);
        addMarket(CONFED, station, "Antares Station", 4,
                new String[] {"population_4", "outpost"},
                new String[] {"population", "spaceport", "fuelprod", "militarybase", "battlestation_mid",
                        "waystation"},
                true);

        addRelayAndBuoy(system, star, CONFED, "Antares", 3000f);
        addJumpPoint(system, star, "ev_antares_jump", "Antares Jump Point", 5300f, 200f);
        finishSystem(system);
    }

    /** recolour a planet's texture (multiplied) and atmosphere, e.g. for Ruby's red ground and red sky */
    private static void tint(PlanetAPI planet, Color surface, Color atmosphere) {
        planet.getSpec().setPlanetColor(surface);
        planet.getSpec().setAtmosphereColor(atmosphere);
        planet.getSpec().setIconColor(surface);
        planet.getSpec().setGlowColor(atmosphere);
        planet.applySpecChanges();
    }

    // ------------------------------------------------------------------ Satori (Rebellion home system)
    private void generateSatori(SectorAPI sector) {
        StarSystemAPI system = sector.createStarSystem("Satori");
        system.getLocation().set(SATORI_X, SATORI_Y);
        system.setBackgroundTextureFilename("graphics/backgrounds/background2.jpg");
        PlanetAPI star = system.initStar("ev_satori_star", "star_orange", 650f, SATORI_X, SATORI_Y, 400f);

        // innermost: hot desert world with the Rebel repair and refuelling base
        PlanetAPI clotho = system.addPlanet("ev_clotho_prime", star, "Clotho Prime", "desert", 40f, 120f, 2200f, 180f);
        clotho.setCustomDescriptionId("ev_planet_clotho_prime");
        // the Rebel capital: an island world of rocky islands and open sea
        PlanetAPI palshife = system.addPlanet("ev_palshife", star, "Palshife", "water", 120f, 150f, 3600f, 300f);
        palshife.setCustomDescriptionId("ev_planet_palshife");
        // uridium mining under a toxic sky
        PlanetAPI spica = system.addPlanet("ev_spica", star, "Spica", "toxic", 220f, 130f, 5000f, 420f);
        spica.setCustomDescriptionId("ev_planet_spica");
        // uninhabited gas giant and its inhabited moon
        PlanetAPI persephone = system.addPlanet("ev_persephone", star, "Persephone", "gas_giant", 300f, 300f, 8000f, 800f);
        persephone.getMarket().addCondition("volatiles_diffuse");
        PlanetAPI rupert = system.addPlanet("ev_rupert", persephone, "Rupert", "barren", 0f, 50f, 600f, 40f);
        rupert.setCustomDescriptionId("ev_planet_rupert");
        // outermost: frozen, windswept desert where the Sojourner II crash-landed
        PlanetAPI turin = system.addPlanet("ev_turin", star, "Turin", "barren-desert", 340f, 110f, 10500f, 1200f);
        turin.setCustomDescriptionId("ev_planet_turin");

        system.addRingBand(star, "misc", "rings_asteroids0", 256f, 2, Color.white, 256f, 6400f, 220f, null, null);
        system.addAsteroidBelt(star, 120, 6400f, 500f, 150f, 250f, "asteroid_belt", "Satori Belt");

        addMarket(REBEL, palshife, "Palshife", 6,
                new String[] {"population_6", "habitable", "water_surface", "mild_climate", "organics_common",
                        "ore_sparse", "regional_capital", "headquarters"},
                new String[] {"population", "megaport", "highcommand", "heavyindustry", "lightindustry",
                        "aquaculture", "starfortress_mid", "heavybatteries", "waystation"},
                true);
        addMarket(REBEL, clotho, "Clotho Prime", 4,
                new String[] {"population_4", "hot", "thin_atmosphere", "ore_moderate", "rare_ore_sparse"},
                new String[] {"population", "spaceport", "fuelprod", "militarybase", "orbitalstation_mid",
                        "grounddefenses", "waystation"},
                true);
        addMarket(REBEL, spica, "Spica", 4,
                new String[] {"population_4", "toxic_atmosphere", "ore_moderate", "rare_ore_moderate"},
                new String[] {"population", "spaceport", "mining", "refining", "grounddefenses"},
                false);
        addMarket(REBEL, rupert, "Rupert", 3,
                new String[] {"population_3", "no_atmosphere", "low_gravity", "ore_sparse"},
                new String[] {"population", "spaceport", "mining"},
                false);
        addMarket(REBEL, turin, "Turin", 3,
                new String[] {"population_3", "very_cold", "extreme_weather", "thin_atmosphere", "ore_moderate",
                        "ruins_scattered"},
                new String[] {"population", "spaceport", "mining", "grounddefenses"},
                false);

        addRelayAndBuoy(system, star, REBEL, "Satori", 2900f);
        addJumpPoint(system, star, "ev_satori_jump", "Satori Jump Point", 4300f, 30f);
        finishSystem(system);
    }

    // ------------------------------------------------------------------ Levo (independents and pirates)
    // Neutral ground between Antares and Satori: an independent port and a pirate den.
    public static final String PIRATES = "pirates";

    private void generateLevo(SectorAPI sector) {
        StarSystemAPI system = sector.createStarSystem("Levo");
        system.getLocation().set(LEVO_X, LEVO_Y);
        system.setBackgroundTextureFilename("graphics/backgrounds/background6.jpg");
        PlanetAPI star = system.initStar("ev_levo_star", "star_yellow", 550f, LEVO_X, LEVO_Y, 350f);
        star.setName("Levo");

        // Levo I: the independent water world with the neutral Levo Spaceport on Locanda island
        PlanetAPI levo = system.addPlanet("ev_levo", star, "Levo I", "water", 75f, 140f, 2800f, 250f);
        levo.setCustomDescriptionId("ev_planet_levo");
        // Darven: uninhabited gas giant
        PlanetAPI darven = system.addPlanet("ev_darven", star, "Darven", "gas_giant", 230f, 280f, 6500f, 650f);
        darven.getMarket().addCondition("volatiles_diffuse");
        // Blackthorne: the pirate station orbiting Darven
        SectorEntityToken blackthorne = system.addCustomEntity("ev_blackthorne", "Blackthorne",
                "makeshift_station", PIRATES);
        blackthorne.setCircularOrbitPointingDown(darven, 45f, 520f, 40f);
        blackthorne.setCustomDescriptionId("ev_station_blackthorne");

        system.addAsteroidBelt(star, 90, 4600f, 400f, 150f, 250f, "asteroid_belt", "Levo Belt");
        system.addRingBand(star, "misc", "rings_asteroids0", 256f, 1, Color.white, 256f, 4600f, 220f, null, null);

        addMarket(INDIE, levo, "Levo I", 5,
                new String[] {"population_5", "habitable", "water_surface", "mild_climate", "organics_common",
                        "frontier"},
                new String[] {"population", "spaceport", "aquaculture", "lightindustry", "commerce",
                        "militarybase", "orbitalstation_mid", "grounddefenses", "waystation"},
                true);
        addMarket(PIRATES, blackthorne, "Blackthorne", 4,
                new String[] {"population_4", "free_market", "organized_crime", "stealth_minefields"},
                new String[] {"population", "spaceport", "militarybase", "orbitalstation", "waystation"},
                false);

        addRelayAndBuoy(system, star, INDIE, "Levo", 3600f);
        addJumpPoint(system, star, "ev_levo_jump", "Levo Jump Point", 3300f, 160f);
        finishSystem(system);
    }

    // ------------------------------------------------------------------ helpers
    private static MarketAPI addMarket(String factionId, SectorEntityToken entity, String name, int size,
                                       String[] conditions, String[] industries, boolean military) {
        MarketAPI market = Global.getFactory().createMarket(entity.getId() + "_market", name, size);
        market.setFactionId(factionId);
        market.setPrimaryEntity(entity);
        market.getTariff().modifyFlat("generator", market.getFaction().getTariffFraction());

        market.addSubmarket("open_market");
        market.addSubmarket("black_market");
        market.addSubmarket("storage");
        if (military) {
            market.addSubmarket("generic_military");
        }
        for (int i = 0; i < conditions.length; i++) {
            market.addCondition(conditions[i]);
        }
        for (int i = 0; i < industries.length; i++) {
            market.addIndustry(industries[i]);
        }

        Global.getSector().getEconomy().addMarket(market, true);
        entity.setMarket(market);
        entity.setFaction(factionId);

        List conds = market.getConditions();
        for (int i = 0; i < conds.size(); i++) {
            ((MarketConditionAPI) conds.get(i)).setSurveyed(true);
        }
        market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        market.reapplyIndustries();
        return market;
    }

    private static void addRelayAndBuoy(StarSystemAPI system, PlanetAPI star, String factionId, String name, float radius) {
        SectorEntityToken relay = system.addCustomEntity(system.getId() + "_relay", name + " Relay", "comm_relay", factionId);
        relay.setCircularOrbit(star, 90f, radius, 200f);
        SectorEntityToken buoy = system.addCustomEntity(system.getId() + "_buoy", name + " Nav Buoy", "nav_buoy", factionId);
        buoy.setCircularOrbit(star, 270f, radius, 200f);
    }

    private static void addJumpPoint(StarSystemAPI system, PlanetAPI star, String id, String name, float radius, float angle) {
        JumpPointAPI jp = Global.getFactory().createJumpPoint(id, name);
        jp.setCircularOrbit(star, angle, radius, 250f);
        jp.setStandardWormholeToHyperspaceVisual();
        system.addEntity(jp);
    }

    private static void finishSystem(StarSystemAPI system) {
        system.autogenerateHyperspaceJumpPoints(true, true);
        // clear hyperspace storms around the system (as vanilla does for core systems)
        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;
        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, minRadius + radius, 0f, 360f, 0.25f);
    }
}
