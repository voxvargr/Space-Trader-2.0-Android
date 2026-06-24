# Space Trader 2.0 - Expansion Pass 004

Base: `SpaceTrader2_OGPort_ExpansionPass003_MechanicsOptimization.zip`

## Focus

This pass expands travel into a more eventful deep-space experience and adds temporary debug combat shortcuts for testing.

## D1000 Travel Event Layer

Travel now rolls against a new D1000-style event table before the original police / pirate / trader encounter logic.

The D1000 system uses:

- 1-80: stellar hazards and nearby supernova / flare damage
- 81-160: sensor anomalies and survey data
- 161-240: derelict salvage and loose cargo
- 241-320: traders trying to offload goods / contraband mid-route
- 321-400: gang fights and ships fleeing faction violence
- 401-480: pirate ambushes
- 481-560: customs sweeps and police inspections
- 561-640: alien convoy encounters and guild standing changes
- 641-720: distress calls, some real and some pirate traps
- 721-800: black-market contacts and contraband loading
- 801-880: space-time weather that can shift route length
- 881-950: faction battlefields, salvage, or combat
- 951-1000: catastrophic deep-space events, ancient arks, exotic matter, and heavy damage hazards

Each roll also generates a procedural signal-detail line from actor/action/object/complication tables, giving hundreds/thousands of possible event descriptions even before future hand-authored entries are added.

Travel-event frequency is capped per trip so long flights feel active without becoming a popup flood. Dangerous/high-conflict destinations and pirate-heavy governments make events more likely; better scanner modules slightly reduce nuisance events.

## Debug Encounter Cheats

The Debug Menu now has a toggle:

- `Encounter cheat buttons ON/OFF`

When enabled, encounter screens show:

- `Debug KO` - instantly disables the opponent and applies win/reward logic.
- `Debug Plunder` - forces pirate/trader surrender and opens plunder.

These only appear while normal debug tools are enabled.

## Retro Button Styling

Added a global retro button background:

- beveled gray Windows 95-style shape
- dark border
- white top/left highlight
- pressed-state inversion
- disabled-state gray

The custom `STButton` class now applies this background automatically, so most game buttons pick up the style everywhere. The app theme also sets regular Android buttons to the same retro style for screens that still use native `Button` widgets.

## Files touched

- `Main.java`
  - Added D1000 travel-event logic.
  - Added debug encounter callbacks.
  - Added travel-event intro text for generated encounters.
- `GameState.java`
  - Added `DebugEncounterCheats`.
  - Added `LastTravelEventIntro`.
- `SaveGame_v120.java`
  - Saves the new debug setting and last event intro safely.
- `FragmentDebugMenu.java`
  - Added encounter cheat toggle.
  - Styled dynamic debug buttons.
- `FragmentEncounter.java`
  - Added debug KO/plunder buttons.
  - Displays travel-event intro text above the normal encounter text.
- `fragment_encounter.xml`
  - Added debug buttons.
- `STButton.java`
  - Applies retro style programmatically.
- `styles.xml`
  - Applies retro style to normal Android buttons.
- `drawable/st2_button_retro.xml`
  - New button selector.

## Build note

The sandbox still cannot run the full Gradle build because the Gradle wrapper tries to download Gradle and this environment has no network access. XML parsing and Java brace-balance checks passed.
