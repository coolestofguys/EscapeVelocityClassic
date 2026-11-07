# Escape Velocity Classic for Starsector (v0.5.0)

This mod adds the Confederation and the Rebellion from the original *Escape Velocity* (Ambrosia
Software, 1996) to Starsector 0.98a. You get their ships and weapons, the main civilian EV
hulls, and three EV star systems. There are no storylines.

This is a free, non-commercial fan mod. It is not affiliated with or endorsed by Ambrosia
Software. *Escape Velocity* and its names, ship designs, art and sounds belong to their original
creators (Matt Burch and Ambrosia Software). Most ship art is new, drawn for this mod.

## Install

1. Copy the `EscapeVelocityClassic` folder into `Starsector/mods/`.
2. Enable **Escape Velocity Classic** in the launcher.
3. **Start a new game.** The star systems are only created when a new campaign starts.

Nexerelin is optional and supported:

- **Corvus (normal) sector:** the mod builds its own systems.
- **Random sector:** Nexerelin places the two factions itself.
- **Playable factions:** both factions appear as starting factions, with EV starting fleets.

## What's in it

### Factions

| Faction | Notes |
|---|---|
| **Confederation** (`ev_confederation`) | Core worlds; home system Antares, Navy headquarters at Ruby. Blue. Strong shields, neutron blasters, carriers. |
| **Rebellion** (`ev_rebellion`) | Outer colonies, capital at Palshife in the Satori system. Rust-red. Fast ships and heavy torpedo loads. |

The two factions start hostile to each other. Both are hostile to pirates and the Luddic Path,
and slightly friendly with independents.

**Who flies what:**

- **Confederation only:** Confed Gunboat, Confederate Superfrigate, Confederate Cruiser.
- **Rebellion only:** Rebel Destroyer, Escort Carrier, Rebel Cruiser.
- **Everyone** (both EV factions, independents and pirates, e.g. at Levo): Shuttlecraft, Courier,
  Clipper, Defender, Rapier, Light Freighter, Corvette, Argosy, Bulk Freighter, Kestrel (a pirate
  favourite, as in EV).
- **Vanilla ships (up to about a quarter of a fleet):** Confederation fleets are about 80% EV ships
  (shared EV hulls plus Confederation exclusives) and about 20% vanilla **high-tech or midline**
  hulls; Rebellion fleets the same with vanilla **low-tech or midline** hulls. This is done with
  per-hull frequency weights worked out from the vanilla fleet roles, so it's an average, not a
  hard cap. Roles no EV ship fills (phase ships, liners, utility ships) are always vanilla.

**Where EV ships and gear are sold:**

- Ships are sold **unarmed**, like vanilla market ships.
- Confederation and Rebellion markets sell their **exclusive** hulls (vanilla `req_military`
  tag), weapon and fighter LPC only in the **military market**: Neutron Blaster and Confed Patrol
  Ship LPC at Confederate military markets, Mass Driver and Manta LPC at Rebel military markets.
  Their open and black markets carry the shared EV ships, weapons and LPCs (Laser Cannon, Proton
  Cannon, Missile Rack, Heavy Rocket Launcher, Torpedo Launcher; Hawk and Lightning LPCs), which
  are also given extra sell weight in all their markets.
- **Levo I** and **Blackthorne** sell mostly shared EV ships: each restock keeps at most two
  other ships and adds six or so empty EV hulls, a few stacks of shared EV weapons and one or two
  Hawk/Lightning LPCs (`data.scripts.world.EVMarketShips`).

**Loadouts:** every EV hull has the `no_autofit` tag, so NPC fleets of any faction always fly it
with its Standard EV loadout. Vanilla hulls in EV fleets are fitted from vanilla blueprints
(Confederation: base, midline, high-tech and missile; Rebellion: base, low-tech and missile), so
they carry vanilla weapons. The EV weapons stay the factions' priority weapons. The tag doesn't
affect your own refit screen or autofit button.

**Blueprints:** every EV ship, weapon and fighter wing is tagged `rare_bp`, so each can turn up
as its own single blueprint while exploring and salvaging (faction-exclusive ones included). The
fighter-only weapons are never dropped or sold.

### Star systems

All three systems sit in a cluster east of the vanilla core worlds.

| System | Owner | Worlds |
|---|---|---|
| Antares | Confederation | Red giant star Antares. New Providence (terran, size 7: Astex mining and farming), orbited by Antares Station (size 4 Confederate garrison and refuelling base: fuel production, military base, battlestation) and its moon Luna (bright white barren moon, size 4, low gravity; the Terran Naval Yards: orbital works, military base, battlestation); P-6564 (uninhabited, irradiated gas giant with a corrosive deep red atmosphere) with its red moon Ruby (size 6, the Confederation's main military base, capital and weapons testing ground: high command, orbital works, star fortress, planetary shield); Antares Belt |
| Satori | Rebellion | Clotho Prime (desert, size 4, repair and refuelling base), Palshife (island world, size 6, capital), Spica (toxic, size 4, uridium mines), Persephone (uninhabited gas giant) with its moon Rupert (barren, size 3), Turin (frozen barren-desert, size 3) |
| Levo | Independent and pirates (neutral ground) | Star Levo. Levo I (independent water world, size 5, the neutral Levo Spaceport on Locanda island; military base and orbital station), Darven (uninhabited gas giant) with Blackthorne (pirate station, size 4, free port with organized crime and stealth minefields; military base), Levo Belt |


### Ships

| EV ship | Starsector size | Faction | Default loadout |
|---|---|---|---|
| Shuttlecraft | Frigate | civilian | Laser Turret |
| Courier | Frigate (small fuel tanker: 240 fuel, 80% of a Dram, at half its supply cost and fuel use; Civilian-grade Hull built in) | civilian | Laser Turret (rear, point defence) |
| Light Freighter | Destroyer (fuel tanker, Argosy-sized: 550 fuel, 250 cargo; Civilian-grade Hull and Surveying Equipment built in) | civilian | 2 Laser Turrets (spine domes) |
| Defender | Frigate | civilian | 2 Laser Cannons (forward-locked, between the prongs); 360° shield |
| Clipper | Frigate (4 DP, between the Defender and the Gunboat) | civilian | Proton Cannon (nose, forward-locked), 2 Missile Racks (hidden under the wings; stand-ins for EV's Javelins); blue engines |
| Rapier | Destroyer (light, 8 DP) | civilian | Proton Cannon (nose, forward-locked), 2 Heavy Rocket Launchers (hidden under the wings), Torpedo Launcher (hidden under the spine), Laser Turret (rear, point defence); blue engines |
| Corvette | Destroyer | civilian | 2 Proton Cannons (forward-locked, beside the spine), Heavy Rocket Launcher (nose), 2 Missile Racks (flanks), 4 Laser Turrets (rear domes); built-in Hardened Subsystems; High Energy Focus system |
| Argosy | Destroyer (combat freighter, Civilian-grade Hull) | civilian | 2 Laser Cannons (forward-fin hardpoints), 2 Laser Turrets (rear wings, point defence), 1 Hawk wing |
| Kestrel | Cruiser | civilian | 2 Proton Turrets (spine), 2 Laser Turrets (wing point defence), 2 Missile Racks (wing tips), hidden Torpedo Launcher under the nose, 2 Lightning wings |
| Bulk Freighter | Destroyer (heavy freighter, Civilian-grade Hull) | civilian | Laser Turret (rear engine block, point defence) |
| Confed Gunboat | Frigate | Confederation | 2 Laser Cannons (wing-claw hardpoints), Heavy Rocket Launcher (nose), Laser Turret (rear point defence) |
| Confederate Superfrigate (EV's Confederate Frigate) | Cruiser | Confederation | 2 Proton Cannons (wing-prong hardpoints), 2 Proton Turrets, 2 Laser Turrets (point defence), 2 Torpedo Launchers (wing silos), 2 Missile Racks (nose fins), Heavy Rocket Launcher (nose); blue engines |
| Confederate Cruiser | Capital | Confederation | Neutron Blaster, 2 Proton Cannons (nose hardpoints), 2 Proton Turrets, 4 Laser Turrets, 2 Torpedo Launchers (wing turrets), 2 Patrol wings; system Fortress Shield |
| Rebel Destroyer | Destroyer | Rebellion | Mass Driver and 2 Laser Cannons (forward-locked hardpoints), Laser Turret (rear, point defence), 2 Missile Racks (hidden under the rear wings); Missile Autoforge system |
| Escort Carrier | Cruiser (light carrier; red-painted Bulk Freighter hull, as in EV) | Rebellion | No guns; 6 Manta wings (24 Mantas) from 4 bays plus a built-in Converted Cargo Bay (+2 bays; slower, frailer fighters); 35 OP; hull, armour, flux and speed no better than the Bulk Freighter (high-risk, low-cost fighter swarm) |
| Rebel Cruiser | Light capital | Rebellion | 2 Proton Turrets, 2 Laser Turrets, 4 Laser Cannons, 4 Torpedo Launchers, 2 Missile Racks, 4 Manta wings (16 Mantas) |

**Simulator:** every EV ship's Standard variant is in the simulator's Default opponent list (`data/campaign/sim_opponents.csv`), so you can fight them from any refit screen. The Confederation and Rebellion also have their own tabs in the advanced simulator, which unlock once you've fought that faction.

The Manta, Lightning, Hawk and Confed Patrol Ship exist only as fighter wings, launched from carriers' bays; there are no standalone ship versions. Each fills a different role, priced against vanilla wings:

| Wing | Role | Craft | OP | Hull / armour | Speed | Weapons | Vanilla comparison |
|---|---|---|---|---|---|---|---|
| Manta | Interceptor | 4 | 4 | 200 / 20 | 360 | Point-defence Laser Cannon (shoots missiles and fighters first) | Faster, harder-hitting Talon (2 OP) |
| Hawk | Light Fighter | 3 | 6 | 400 / 50 | 230 | 2 Laser Cannons; Decoy Flare Launcher system (vanilla `flarelauncher_fighter`, as on the Broadsword) | Slow, sturdy generalist, like a Gladius (6 OP) |
| Confed Patrol Ship | Shielded Fighter | 2 | 15 | 450 / 50 + front shield | 250 | Proton Cannon (fighter: 225 per shot, ~190 per second, 550 range), 2 guided missiles | The only shielded fighter; between a Warthog (12 OP) and a Cobra (15 OP) |
| Lightning | Strike Fighter (bomber AI) | 1 | 15 | 1,000 / 150 | 330 | 2 Laser Cannons, 2 heavy rockets, 2 guided missiles | Single armoured striker like the Cobra (15 OP); stays within 1,500 of its carrier |

Fighters carry lighter fighter-only weapons, as vanilla fighters do. Their missile and rocket launchers are built into the airframe, so no launcher is drawn on the fighter. Missiles and rockets reload at the carrier.

Every Standard variant spends its OP: after weapons and wings, it maxes vents and capacitors, then puts any OP left over into Hardened Shields, Flux Distributor, Armored Weapon Mounts or Auxiliary Thrusters (whichever combination uses the most). At most 3 OP is left unspent on any ship.

**About the loadouts:**

- **Taken from EV:** the Confederate Cruiser, Confederate Superfrigate, Rebel Destroyer, Rapier
  and Kestrel loadouts.
- **Best guesses:** the rest of the loadouts. Edit `data/variants/*.variant` if you remember them
  differently.
- **Stats:** rebalanced to Starsector scale, not copied 1:1. EV ships are almost all shields
  and no armour, which doesn't play well in Starsector.

### Weapons

Each weapon uses its own recolour of a vanilla sprite, made inside the mod (your Starsector install isn't changed), so it's easy to tell apart from base-game guns:

- **Laser Cannon:** IR Pulse Laser body with green bands
- **Proton Cannon:** Pulse Laser body in navy gunmetal with electric-blue bands
- **Neutron Blaster:** Plasma Cannon body in dark gunmetal with red emitter lights
- **Missile Rack:** slim light steel-grey single-rail rack
- **Heavy Rocket Launcher:** white-painted Reaper launcher, firing white Reaper-style rockets with a white glow
- **Mass Driver:** Rebel-red recolour of the vanilla mass driver sprite with steel rails, firing hot orange-white slugs
- **Torpedo Launcher:** grey Typhoon Reaper Launcher, like EV's grey torpedo launcher

Firing glows use the projectile colours (green, blue, red). The sounds are EV's own.

| Weapon | Size / type | Role |
|---|---|---|
| Laser Cannon | Small energy | Steady stream of light green bolts: strong against shields, fighters and missiles, weak against armour |
| Proton Cannon | Medium energy | Slower, heavier blue bolts with long reach, especially from a hardpoint |
| Neutron Blaster | Large energy | A huge, fast red bolt every 2 s: the shortest-ranged large gun, but the most flux-efficient one, so it can keep firing with shields up once it closes in (Confederate Cruiser). Confederation only |
| Missile Rack | Small missile | Eight light guided missiles instead of a Harpoon's three heavy ones |
| Heavy Rocket Launcher | Small missile | Five fast, armoured unguided rockets: more shots than a Hammer, harder-hitting than an Annihilator |
| Torpedo Launcher | Medium missile | Very slow but armoured homing torpedoes that turn harder than any vanilla missile |
| Mass Driver | Medium ballistic | Rebel slug thrower: heavy kinetic slugs that strip shields, with long reach but slow, dodgeable shots. Rebellion only |

**Cannon vs turret, as in EV:**

- **Laser and Proton Cannons** get **+25% range** in a fixed hardpoint mount compared with a
  rotating turret.
- **How it works:** EV sold each of these as a forward cannon or a swivel turret firing the same
  bolt, and this recreates that.
- **Where it shows:** the bonus applies in combat. The refit screen shows the base (turret) range,
  and the weapon tooltip notes the bonus.

## Balance against the base game

Numbers were set against the vanilla 0.98a data files.

EV guns are heavy guns: because EV ships carry few mounts, the Laser Cannon, Proton Cannon, Neutron Blaster and Mass Driver were scaled up by 50% in damage and OP, but only 20% in flux. Damage per OP stays in line with vanilla, so a vanilla ship can mount them but pays full price; EV ships spend the OP they would otherwise put into extra mounts. The smaller flux increase rewards mounting such an expensive gun and keeps each EV ship's flux balance close to what it was. Missiles and fighter weapons are unchanged.

| EV weapon | Damage per shot | Damage per second | Flux per damage | Range (hardpoint) | OP | Closest vanilla weapon |
|---|---|---|---|---|---|---|
| Laser Cannon | 45 | 225 | 0.64 | 500 (625) | 6 | IR Pulse Laser: 50 per shot, 150 per second, 0.8, 500, 5 OP |
| Proton Cannon | 340 | 378 | 0.88 | 650 (812) | 15 | Pulse Laser: 100, 300, 0.8, 600, 10 OP; Heavy Blaster: 500, 500, 1.44, 600, 12 OP |
| Neutron Blaster | 1,800 | 900 | 0.48 | 600 | 36 | Plasma Cannon: 500, 750, 1.1, 700, 30 OP |
| Mass Driver (kinetic) | 300 | 375 | 0.72 | 900 (slug speed 550) | 18 | Heavy Autocannon: 100, 214, 0.9, 800, 10 OP; Hypervelocity Driver: 275, 138, 1.27, 1,000, 13 OP |

| EV missile | Damage × ammo | Speed | Missile HP | Tracking | OP | Closest vanilla weapon |
|---|---|---|---|---|---|---|
| Missile Rack | 300 × 8 | 330 | 120 | Good | 4 | Harpoon: 750 × 3, 300, 150, Medium, 4 OP |
| Heavy Rocket Launcher | 800 × 5 | 800 | 150 | None | 3 | Hammer: 1,500 × 2, 500, 350, 2 OP; Reaper: 4,000 × 1, 400, 500, 2 OP; Annihilator: 200 × 50, 400, 50, 4 OP |
| Torpedo Launcher | 1,500 × 6 | 175 | 1,000 | Excellent (turn rate 160) | 10 | Harpoon Pod: 750 × 12, 300, 150, 10 OP; Typhoon: 4,000 × 6 unguided, 400, 500, 10 OP |

| Fighter wing | Craft | Hull / armour | Speed | Shield | OP | Closest vanilla wing |
|---|---|---|---|---|---|---|
| Confed Patrol Ship | 2 | 450 / 50 | 250 | front 150° (0.8 efficiency) | 15 | Xyphos: 2 × 450 / 50, 160, omni, 18 OP |
| Manta | 4 | 200 / 20 | 360 | none | 4 | Talon: 4 × 200 / 25, 325, none, 2 OP |
| Hawk | 3 | 400 / 50 | 230 | none (decoy flares) | 6 | Gladius: 2 × 500 / 75, 300, none, 6 OP |
| Lightning | 1 | 1,000 / 150 | 330 | none | 15 | Cobra: 1 × 400 / 25, 160, omni, 15 OP |

The Rebel Cruiser is a light capital: 12,000 hull, 1,100 armour, 240 OP and a speed of 50.

### Pricing: fewer guns, cheaper ships

EV ships carry far fewer weapon mounts than Starsector ships of the same class (EV ships had 4 to 8 guns, one missile rack and one torpedo launcher). Instead of making the weapons stronger, each EV hull is priced by how much it carries:

1. Score its mounts: small = 1, medium = 2, large = 4.
2. Add 3 for each fighter bay.
3. Set deployment points to about that total, then adjust up for an unusually tough, fast or flux-rich hull. Civilian freighters are priced higher for their cargo role, as in vanilla.

Vanilla combat ships come out at about 0.6 to 1.0 deployment points per mount point, so EV fleets field more hulls, each tougher and longer-lasting than its gun count suggests. The weapons stay balanced against vanilla per OP and per flux, so they behave the same on any ship.

| Ship | Mount points + bays | Deployment points | Fleet points |
|---|---|---|---|
| Kestrel | 10 + 2 bays | 16 | 11 |
| Confederate Superfrigate | 17 | 19 (tanky brawler) | 15 |
| Escort Carrier | 4 bays + Converted Cargo Bay (6 total), no guns (glass carrier) | 19 | 15 |
| Rebel Cruiser | 20 + 4 bays | 29 | 21 |
| Confederate Cruiser | 20 + 2 bays | 36 (heavier hull and flux than an Onslaught) | 25 |

## Ship sprites

The Confederate Cruiser, Confederate Superfrigate, Rebel Cruiser, Kestrel, Confed Gunboat, Rebel Destroyer, Argosy, Bulk Freighter, Defender, Corvette, Rapier, Clipper, Light Freighter, Courier and Escort Carrier (a red repaint of the Bulk Freighter) use new art drawn for this mod, with empty weapon sockets painted on. The Shuttlecraft and the fighters still use the original EV art, taken from the nose-up frame of each ship in the *EV Classic for Nova* port and upscaled automatically to Starsector sizes, so they look soft. New art for them is planned.

Faithful to EV:

- The **Escort Carrier** uses the Bulk Freighter's sprite. EV did the same.
- The fighter wings use EV's art for those ships, at fighter scale.

`sprite_sheet.png` in this folder shows every hull and fighter wing, grouped by faction. Each
sprite's file is named after its hull id, in `graphics/ships/ev/`.

To swap in better upscales:

- **Format:** one PNG per ship, with a transparent background and the nose pointing **up**.
- **Naming:** name each file after the hull id, e.g. `ev_confed_cruiser.png`.
- **Size:** aim for roughly the size shown on the sheet. Starsector draws 1 pixel = 1 unit.

The build script traces each sprite's outline into a collision shape that follows the hull: up to 40 points, concave where the art is. It also snaps the
weapon mounts onto the hull and moves the engines to the rear edge.

If you edit a sprite yourself without regenerating, keep the same pixel size. Otherwise the
bounds and mounts in `data/hulls/<id>.ship` won't line up.

## Sounds

Every weapon fires with its original EV sound, decoded from the *EV Classic for Nova* data and converted to OGG in `sounds/ev/`. Adjust the volumes in `data/config/sounds.json`.

## Engine flames

The Confederate Cruiser uses a custom blue-white engine style, `EV_CONFED_BLUE`, to match its art. It's defined in `data/config/engine_styles.json`, where you can change the colour or glow size.

All Rebellion ships (Rebel Destroyer, Escort Carrier, Rebel Cruiser and the Manta fighters) use vanilla's `LOW_TECH` style: an orange flame with smoke trails.

## Tweaking

- **Balance:** change the values in `data/hulls/ship_data.csv` and
  `data/weapons/weapon_data.csv`. Both are plain CSV, so any spreadsheet program can edit them.
- **Fleet mix:** change the weights in `data/world/factions/default_ship_roles.json`.
- **System positions:** set at the top of `src/data/scripts/world/EVSectorGen.java`. These are
  compiled into `jars/ev_classic.jar`.
- **Relationships:** set in `src/data/scripts/EVModPlugin.java`.
