# Space Trader 2.0 - Expansion Pass 003

Base: OG Codeberg Android port + Expansion Pass 002 launcher/compile fixes.

## Focus
This pass starts the next phase: optimization of the new expansion layer and deeper mechanics that are integrated into the original system-to-system loop.

## Optimization changes
- `GameState.ensureExpansionState()` now verifies and seeds the 480-system expansion data once per load instead of doing a full 480-system validation every time the Local System screen refreshes.
- Added `ExpansionStateVerified` as a transient runtime guard so new games and loaded saves do not repeatedly walk the whole galaxy unless needed.
- Local System guild standing text now uses `StringBuilder` instead of repeated string concatenation during UI refresh.
- Local scans now reveal a limited number of contacts based on scanner level instead of always revealing every body in one click. This gives the local-system gameplay more pacing and avoids turning every generated system into a one-click reveal.

## Deeper mechanics added
- Added persistent ship-side expansion modules:
  - Scanner Suite
  - Mining Rig
  - Salvage Drones
  - Trade Network
  - Combat Tactics AI
  - Smuggler Hold
- Each module has levels 1-5.
- Modules use the new expansion resources as upgrade costs:
  - Credits
  - Survey Data
  - Salvage Parts
  - Alien Relics
  - Exotic Matter
- The Local System screen now has upgrade buttons for each module.

## Local action time
- Local actions now advance a local operations clock.
- Every 3 local actions advances the game by 1 day.
- Deadline contracts can now fail while the player spends too much time in-system.

## Contracts rebuilt
Guild contracts are now more objective-based instead of only completing on arrival.

- Delivery contracts load cargo and require delivery by deadline.
- Smuggling contracts use illegal cargo, interact with police record, and benefit from Smuggler Hold upgrades.
- Survey contracts require scanning/exploring sites in the destination system.
- Bounty contracts require pirate hunting in the destination system.
- Contract progress is saved.

## Local gameplay improvements
- Long Scan now scales with Scanner Suite and pilot skill.
- Exploration rewards now scale with Scanner Suite.
- Mining yield and hazard reduction now scale with Mining Rig.
- Derelict salvage yield now scales with Salvage Drones.
- Local Trade rewards and pricing now scale with Trade Network.
- Pirate hunting now scales with Combat Tactics AI and can reduce local conflict.
- Added Diplomacy action to trade survey data/credits for guild standing and possible conflict reduction.
- Added Smuggle Contact action for black-market cargo opportunities.

## Economy integration
- The original buy/sell price generation now receives a light Space Trader 2.0 local-economy modifier based on:
  - Economy focus
  - Guild influence
  - Pirate pressure
  - Science systems
  - Conflict level
- This keeps the original market loop intact while making new generated system traits matter.

## Debug menu additions
- Max ST2 modules button.
- Add ST2 resources button.

## Save compatibility
- Added new fields to the existing `SaveGame_v120` wrapper.
- Old saves should default module levels to 1 and contract progress to none/zero.

## Sandbox test status
- Full Android Gradle build could not be run here because the Gradle wrapper tries to download Gradle and the sandbox has no network access.
- Java brace-balance checks passed.
- Core GameState/DataTypes compiled against local stubs.
- FragmentLocalSystem and FragmentDebugMenu compiled against local Android stubs to catch Java syntax and method-reference errors.
