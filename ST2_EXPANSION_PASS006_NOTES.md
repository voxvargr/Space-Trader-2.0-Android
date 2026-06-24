# Space Trader 2.0 - Expansion Pass 006

Focus: equipment depth, ship customization, and crew/hireling usefulness.

## Equipment expansion

- Expanded the normal equipment shop beyond the original three weapons/two shields/five gadgets.
- Added new weapons:
  - Ion cannon
  - Railgun
  - Plasma lance
  - EMP blaster
  - Missile rack
  - Particle beam
  - Quantum lance
- Added new shields:
  - Ablative armor field
  - Deflector matrix
  - Phase shield
  - Aegis barrier
- Added new gadgets/equipment:
  - Auto-repair Mk II / Mk III
  - Navigation Mk II / Mk III
  - Targeting Mk II / Mk III
  - Expanded cargo pods
  - Smuggler hold
  - Deep sensor analyzer
  - Shield harmonizer
  - Trade uplink
  - Drone control bay

## Upgrade paths and comparison info

- Buy Equipment now generates its list dynamically instead of being locked to the original fixed table.
- Equipment rows now show power/capacity, tech level, cost, description, and comparison text.
- Mk II / Mk III systems upgrade the existing lower-mark system in-place instead of taking a second slot.
- Examples:
  - Targeting Mk I gives fighter bonus +3.
  - Targeting Mk II gives fighter bonus +5 and replaces Mk I.
  - Targeting Mk III gives fighter bonus +7 and replaces Mk I/Mk II.
- Navigation, targeting, and repair equipment now affect ship skill calculations through their mark level.

## Ship customization

- Added a new Shipyard button: **Customize Current Ship**.
- Added a new Ship Customization screen.
- Current ship can be permanently refit with options like:
  - Trade 5 cargo bays for a weapon hardpoint.
  - Trade 5 cargo bays for a shield socket.
  - Trade 5 cargo bays for a gadget bay.
  - Convert an empty weapon hardpoint into a gadget bay.
  - Convert an empty shield socket into a weapon hardpoint.
  - Trade 5 cargo bays for reinforced bulkheads and +10 max hull.
- Buying a new ship resets these custom hull refits so each hull remains its own build.
- Saves now store custom slot/hull/cargo changes.

## Crew and hireling improvements

- Each arrival now refreshes the local mercenary market more aggressively, so ports should feel more alive.
- Local job boards can now produce specialists with stronger pilot/fighter/trader/engineer skill spikes.
- The Personnel Roster now chooses the strongest local candidate instead of simply showing the first one found.
- High-pilot hirelings can intervene during police contraband inspections and suggest taking the controls to flee before you submit to cargo scan.

## Equipment hooks into local-system mechanics

- Deep sensor analyzer improves system scans, exploration odds, and survey/data payouts.
- Drone control bay improves mining and derelict salvage output.
- Trade uplink improves local side-market trade rewards.
- Smuggler hold improves smuggling contact pricing and reduces police-record damage from black-market work.
- Existing module upgrades still stack with these ship-installed systems.

## Compatibility notes

- Old saves with 3 equipment slots should normalize into the new 6-slot arrays when loaded.
- New save fields are added through SaveGame_v120 and default to zero for older saves.
- Full Gradle build was not run in the sandbox because the Gradle wrapper tries to download Gradle and the environment has no network access.
