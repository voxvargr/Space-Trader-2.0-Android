# Space Trader 2.0 - Expansion Pass 005

This pass is built on top of Pass004.

## Narrative Travel Events

The travel-event system still uses a large hidden roll range internally, but the player-facing wording no longer says "D1000", "roll", or "signal detail". Events now read like short in-world incidents instead of visible random-table output.

Examples of the new tone:

- Stellar hazards describe the route, the cockpit response, and the damage outcome.
- Contraband traders now appear as private-channel encounters instead of table entries.
- Gang fights, customs nets, convoy assists, rescue calls, black-market whispers, ancient arks, and space-time weather now include a more natural setup and consequence line.
- Encounter popups that lead into combat/trade now get a narrative intro before the normal encounter screen text.

## Travel Events Now Affect Systems

Travel incidents now feed back into the destination system instead of being isolated popups.

Depending on what happens during travel, the destination can have:

- Higher or lower conflict.
- Stronger guild influence.
- Increased discovery value.
- Newly revealed local contacts.
- Bodies with stronger anomaly, market, danger, derelict, pirate, or salvage flags.

For example, running into an ambush can increase pirate pressure and danger around a local body. Helping a convoy can strengthen the controlling guild and improve market activity. Finding derelict wreckage can mark a local contact as salvage-worthy.

## Expanded System Information

The System Information screen now includes a deeper multiline intel block:

- Travel advisory
- Local history
- Port rumors
- Opportunities
- Local layout summary

This makes every system feel less like a stat sheet and more like a place with traffic, rumors, history, and current pressure.

## Expanded Local System Screen

The Local System screen now shows:

- Route advisory
- Local history
- Port rumors
- Opportunities
- More descriptive body/site intel under each contact

Discovered bodies now describe what the place actually feels like: cargo moons, derelicts, old ruins, pirate bases, ice worlds, ocean worlds, belts, gas giants, and stations all get contextual descriptions.

## Newspapers Use Expansion Data

Local newspapers now pull from the expanded system data too. They can include port advisories and dockside rumors based on the system's route danger, guild control, anomalies, pirates, markets, and local opportunities.

## Notes

The older Pass004 notes still mention the D1000 implementation because that was the internal design of the previous build. In this pass, that wording has been removed from player-facing travel popups and encounter intros.
