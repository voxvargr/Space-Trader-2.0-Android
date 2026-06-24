# Space Trader 2.0 - OG Port Expansion Pass 002

This pass restarts from the uploaded Codeberg/OG Android port and removes the previous "Expeditions as a separate bolt-on" direction. The new expansion is integrated into the core galaxy/system loop.

## Main direction

- Preserve the original Space Trader systems, goods, ships, encounters, quests, and map flow.
- Expand the new-game galaxy from 120 systems to 480 systems, roughly 4x the original count.
- Keep the original system-to-system trading loop, but make every solar system contain a local generated layer of bodies, contacts, risks, and side opportunities.
- Build toward No Man's Sky-style discovery, FTL-style route pressure/contracts, and eventually EVE-like guild/faction/economy depth.

## Implemented in this pass

### Galaxy expansion

- `GameState.MAXSOLARSYSTEM` is now 480.
- Galaxy dimensions were expanded to keep the map density close to the old feel.
- The first 120 named systems are preserved.
- Systems beyond the original name list receive deterministic generated names through `Main.GetSystemName(...)`.
- Old save loading now uses safe copy limits and seeds missing expanded systems instead of breaking.

### Local system layer

Every system now owns a generated local map with bodies such as:

- Rocky planets
- Ocean worlds
- Ice worlds
- Desert worlds
- Gas giants
- Asteroid belts
- Orbital stations
- Derelict vessels
- Ancient sites
- Pirate bases
- Trade moons

Each body can have:

- Dominant alien species
- Controlling guild
- Danger level
- Resource richness
- Market strength
- Quest/anomaly value
- Discovery/survey/depleted flags

### Aliens and guilds

Added 12 alien/species groups:

- Human Frontier
- Krellian
- Vorr Hive
- Auri Nomads
- Tzynn Clans
- Moro Collective
- Ithari
- Quorl
- Velori
- Nekkar
- Sable Synths
- Old Machine Cult

Added 6 guild/faction tracks:

- Explorers Guild
- Mercenary Compact
- Trade League
- Pirate Clans
- Miners Union
- Science Directorate

Systems now have controlling guilds, guild influence, conflict rating, economy focus, and discovery value.

### Integrated UI

- System Information now shows the local dominant species, controlling guild, and conflict level.
- System Information has a new **Local System** button.
- Navigation drawer has a **Local System** entry.
- Local System screen lets the player:
  - Run long scans
  - Explore bodies/stations/sites
  - Mine asteroids or salvage derelicts
  - Do local side trading
  - Hunt pirate activity
  - Take guild jobs

### Guild contracts

Added a first-pass guild contract system:

- Survey commissions
- Priority deliveries
- Bounty warrants
- Quiet cargo jobs
- Destination, deadline, reward, guild standing changes
- Completion/failure check on arrival after normal travel

### New progression resources

The player now tracks:

- Survey Data
- Salvage Parts
- Alien Relics
- Exotic Matter
- Bounty Vouchers
- Guild Standing per guild

### Debug tools

Added temporary development/debug tools in Options:

- Enable debug tools checkbox
- Open Debug Menu button
- Add credits
- Repair hull
- Refill fuel
- Toggle god hull
- Toggle debug travel/unlimited fuel
- Clear debt
- Clear active guild contract
- Jump to selected target
- Jump to random system
- Reveal current local system

Enabling debug tools marks the game as cheat mode through the existing cheat counter path.

## Files added

- `DataTypes/StellarBody.java`
- `FragmentLocalSystem.java`
- `FragmentDebugMenu.java`
- `res/layout/fragment_local_system.xml`
- `res/layout/fragment_debug_menu.xml`
- `ST2_EXPANSION_PASS002_NOTES.md`

## Files changed heavily

- `GameState.java`
- `Main.java`
- `DataTypes/SolarSystem.java`
- `DataTypes/SaveGame_v120.java`
- `FragmentSystemInformation.java`
- `FragmentWarpSystemInformation.java`
- `FragmentOptions.java`
- `NavigationDrawerFragment.java`
- `res/layout/fragment_system_information.xml`
- `res/layout/fragment_game_options.xml`
- `SpaceTrader/build.gradle`

## Validation done here

- XML resource files parse correctly.
- Basic Java brace balance check passes for modified files.
- Gradle wrapper line endings were normalized for Unix use.

A full Android Gradle build could not be completed in this sandbox because the Gradle wrapper needs to download Gradle 6.5 from `services.gradle.org`, and this environment has no internet access / no installed Android SDK.

## Good next pass ideas

- Replace simple popup outcomes with nested local menus per planet/station/base.
- Make stations have separate shipyards, black markets, guild halls, taverns, refineries, and faction agents.
- Add faction reputation consequences to classic pirate/police/trader encounters.
- Add more cargo categories and local-only commodities.
- Add equipment modules for scanners, mining lasers, drone bays, boarding pods, diplomacy arrays, cloaks, and cargo processors.
- Add multi-step quest chains from local bodies.
- Add star/system types and hazardous route modifiers.
- Add UI filters on the galactic chart for guilds, conflict, markets, and discovered anomalies.
- Eventually migrate the old fixed screen flow into a deeper activity/fragment stack while preserving the classic game.
