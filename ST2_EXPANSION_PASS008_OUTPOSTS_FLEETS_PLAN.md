# Space Trader 2.0 - Pass 008 Planning: Outposts and Fleets

This is the next big expansion direction after the local-system, guild, travel-event, ship-customization, crew, and save/load passes.

The short version: turn the current "visit a system, do local work, leave" loop into "leave a mark on places, hire people to act without you, and eventually become a real power in the galaxy."

## Current hooks already in the game

- The galaxy now has 480 systems, each with generated bodies, stations, trade moons, derelicts, ruins, pirate bases, guild control, guild influence, conflict, markets, resources, and discovery value.
- The Local System screen already supports scans, exploration, mining/salvage, local trade, pirate hunting, diplomacy, smuggling contacts, module upgrades, and guild contracts.
- Persistent expansion resources already exist: survey data, salvage parts, alien relics, exotic matter, bounty vouchers, guild standing, and module levels.
- Crew/hirelings already have skills and current systems.
- Ships already have cargo, equipment, fuel, hull, crew, type data, and custom refits.
- Saves are now private-app-storage based and already have a v120 wrapper that can be extended again.
- The Debug Menu already supports development shortcuts and should be expanded alongside every new major system.

That means outposts and fleets should not be separate minigames. They should grow out of discovered local bodies, current guild politics, ship ownership, crew hiring, and daily travel/local-action time.

## Standing debug/playtest rule

Every major addition should include a matching debug cheat or playtest helper in the debug section.

Examples:

- Outposts: add credits/resources, create test outpost here, max outpost facilities, trigger outpost event, force raid, force production tick.
- Fleets: add test ship, add captain, finish active assignment, damage/repair fleet ship, force captain success/failure report.
- Factions: set guild standing, flip local control, raise/lower conflict, unlock charter eligibility.
- Endgame holdings: grant legacy holding, reset legacy status, preview ending path.

The goal is to make every expansion easy to test from a fresh save without hours of setup.

## Pillar 1: Outpost Management

### Core fantasy

Borrow the pacing shape of "A Dark Room", but translate it into Space Trader:

- Start with a claim beacon, a rented berth, or a rough survey camp.
- Gather basic resources through local actions and passing days.
- Build practical modules: shelter, power, storage, extractors, comms, docks.
- Attract workers, guards, technicians, brokers, scientists, and faction agents.
- Turn a tiny foothold into a productive outpost, then a colony, station district, freeport, or system seat.

The feel should be humble at first. The player should start with "we have a beacon and a pressure tent" and slowly grow into "this place has docks, warehouses, defense guns, contract offices, and my fleet uses it as a hub."

### Claim types

- Surface Outpost: built on rocky, desert, ocean, or ice worlds. Strong for population, science, farming/medicine, and long-term growth.
- Mining Camp: built on asteroid belts, mineral-rich bodies, or dangerous frontier worlds. Strong for ore, salvage, components, and industrial production.
- Station Lease: bought inside an orbital station. Expensive but safer, faster to start, and tightly tied to trade, repairs, crew, and factions.
- Trade Moon Holding: the natural successor to the old "buy a moon" idea. Strong for warehousing, trade routes, and prestige.
- Derelict Refit: convert a discovered derelict into a hidden base or salvage yard.
- Pirate Cove: illegal/smuggling-focused base, probably unavailable or dangerous unless Pirate standing is high.
- System Charter: later-game upgrade where the player has enough standing and money to control a whole system-level holding.

### Acquisition paths

- Buy a legal charter from the controlling guild or government.
- Claim an unowned discovered body after surveying it.
- Win a faction grant through guild standing or a quest chain.
- Seize or clear a pirate base, then decide whether to legitimize it or keep it hidden.
- Convert the retirement moon into a broader "legacy holding" endgame path.

### Early facilities

- Claim Beacon: establishes ownership and enables the Manage Outpost screen.
- Hab Module: allows staff to live there.
- Power Plant: required by most advanced modules.
- Storage Vault: holds produced resources and cargo.
- Extractor: produces ore/salvage based on local resource richness.
- Survey Office: turns local discovery into survey data and Science/Explorer standing.
- Comms Array: unlocks remote reports, fleet orders, and rumor handling.
- Docking Pad: lets fleets stop, refuel, repair, or load cargo.
- Market Office: creates local trade income and cargo opportunities.
- Security Post: reduces raid/sabotage risk.

### Mid and late facilities

- Refinery: turns ore/salvage into components for building.
- Ship Service Bay: repairs player and fleet ships at a discount.
- Warehouse Exchange: stores cargo and supports automated trade routes.
- Recruitment Hall: attracts better hirelings and future captains.
- Guild Office: anchors faction jobs and standing gains.
- Science Lab: improves relic/exotic-matter work and anomaly events.
- Barracks: supports patrol fleets, boarding crews, and defense.
- Sensor Net: reveals bodies/events in nearby systems.
- Shipyard Slip: later-game ship storage, refits, and maybe ship construction.
- Embassy/Admin Center: enables system charters, taxes, alliances, and faction leadership.
- Defense Grid: makes the place hard to raid or seize.

### Resources

Use existing resources first:

- Credits
- Survey Data
- Salvage Parts
- Alien Relics
- Exotic Matter
- Bounty Vouchers
- Guild Standing
- Cargo goods, especially ore, medicine, machines, robots, and luxury goods

Add only the minimum new resources needed:

- Staff: abstract population/workforce assigned to jobs.
- Power: facility capacity limiter.
- Components: built from salvage/ore and used for construction.
- Supplies: food/medicine/water-equivalent upkeep, using existing cargo goods where possible.
- Security: defense strength from guards, facilities, fleets, and local guild influence.

### Staff roles

Staff should feel like "villagers" from A Dark Room, but sci-fi:

- Workers: build and gather.
- Miners: produce ore/salvage.
- Engineers: maintain power, repairs, and construction.
- Brokers: produce trade leads and passive credits.
- Guards: reduce raids and losses.
- Scientists: produce survey data/relic breakthroughs.
- Medics: reduce disaster impact and support colony growth.
- Dockhands: improve fleet turnaround and cargo route efficiency.

Crew members and special NPCs can later become administrators, but the first version can use abstract staff counts.

### Time model

Do not use real-world idle timers at first. Production should advance on in-game time:

- Every local operations day.
- Every warp/day increment.
- Possibly every completed fleet assignment.

This keeps the game from becoming an offline-idle app and preserves Space Trader's turn-based rhythm.

### Outpost events

Outposts should create stories, not just income:

- Supply shortage.
- Pirate raid.
- Guild inspection.
- Worker strike or loyalty dispute.
- Discovery under the foundations.
- Smuggler offer.
- Faction asks to station troops.
- Trade boom.
- Local plague or machine failure.
- A captain requests berth, work, or refuge.

Events can use the current system's conflict, controlling guild, danger, market strength, body anomaly, and guild influence.

### UI shape

First version can live in the Local System screen:

- Discovered body row gets "Establish Outpost" when eligible.
- Existing player-owned bodies get "Manage Outpost".
- Manage screen shows status, storage, staff, facilities, production, upkeep, and buttons.

Later:

- Navigation drawer gets "Holdings".
- Holdings screen lists all outposts, production, warnings, stored cargo, and assigned fleets.
- System Information marks player-owned sites.

## Pillar 2: Fleets and Captains

### Core fantasy

Borrow from Mount & Blade Warband's party/caravan/companion feeling:

- You can own more than one ship.
- A trusted captain can command a second ship.
- Ships can follow you as a convoy or be sent away on assignments.
- Captains have skills, wages, loyalty, risk tolerance, and reputation consequences.
- Fleets make the player feel less like a lone trader and more like a rising power.

### First fleet concepts

- Owned Vessel: a ship the player owns but is not currently piloting.
- Captain: a hired crew member or special NPC assigned to an owned vessel.
- Fleet Order: what that ship is doing.
- Fleet Report: the result when the order finishes.

### Fleet orders

- Follow Convoy: travels with the player, adds cargo and combat support, but costs wages/fuel and may slow travel.
- Trade Run: send captain to buy/sell based on a known rumor or route.
- Courier Contract: low cargo, lower risk, reputation reward.
- Survey Run: generates survey data and discoveries in a target system.
- Mining Run: produces ore/salvage from known resource bodies.
- Bounty Patrol: reduces conflict/pirate pressure, earns bounties, risks damage.
- Smuggling Run: high profit, police-record and captain-loyalty risk.
- Resupply Outpost: moves cargo/supplies/components to a holding.
- Defend Holding: reduces raid risk at an outpost.
- Scout Rumor: investigates a travel rumor, possible trade lead, anomaly, or ambush.

### Captain mechanics

Use existing skill stats:

- Pilot: speed, route safety, escape chance.
- Fighter: combat survival and bounty success.
- Trader: trade-run profit and contract negotiation.
- Engineer: repair cost, breakdown chance, salvage yield.

Add later:

- Loyalty: affected by pay, danger, faction choices, illegal work, and personality.
- Morale: affected by repeated losses, underpayment, crew deaths, successful jobs.
- Trait/personality: cautious, ambitious, loyalist, smuggler, zealot, scientist, etc.

### Fleet risk

Assignments should not be guaranteed:

- A ship can be delayed.
- Cargo can be lost.
- Hull can be damaged.
- A captain can be captured, defect, quit, or call for rescue.
- A successful captain can gain fame, stronger skills, and better opportunities.

Risk should be readable before sending the order: "Low", "Moderate", "High", "Suicidal".

### Convoy gameplay

When ships travel with the player:

- Add cargo capacity if convoy logistics are enabled.
- Improve pirate deterrence and combat strength.
- Increase fuel/wage costs.
- Possibly slow travel based on the worst pilot/fuel range.
- Create more interesting encounter options: order escort to screen, split formation, sacrifice cargo, board enemy, pursue fleeing ship.

### UI shape

First version:

- Shipyard gains "Fleet Storage" or "Owned Ships".
- Personnel Roster can assign an idle hireling as captain.
- New Fleet screen lists owned vessels and active orders.

Later:

- Map/chart shows fleet positions.
- Local System and Holdings screens can assign ships directly to bodies/outposts.
- Reports arrive as travel/event popups or a Fleet Reports screen.

## Outposts plus fleets

The two systems should feed each other:

- Outposts provide docks, refuel, repairs, storage, staff, and mission boards.
- Fleets defend outposts, haul supplies, establish new claims, and connect trade routes.
- A station lease makes early fleet management easier.
- A remote mining camp becomes profitable only once a fleet can haul ore/components.
- A trade moon becomes a true hub when fleets run routes through it.
- A system charter becomes viable when the player has local outposts, defense, trade, and faction standing.

## Replacing the old moon retirement

The old victory path is "buy a moon, retire." Keep the charm, but broaden the fantasy.

Possible new endgame paths:

- Legacy Holding: buy or build a major trade moon/colony and retire as founder.
- Freeport Prince: control a profitable independent station network.
- Guild Chancellor: become leader or decisive patron of a guild/faction.
- Frontier Governor: hold a system charter with stable security, trade, and population.
- Shadow Magnate: run a hidden smuggling/pirate logistics empire.
- Explorer Laureate: found a science/explorer enclave around major discoveries.

The existing `MoonBought` flag can eventually become a compatibility shim for a richer "LegacyStatus" or "EndgameHolding" field.

## Implementation roadmap

### Pass 008A: Outpost data and one-body MVP

- Add an `Outpost` data type with system index, body index, type, level, facilities, staff, storage, production clock, security, and owner/guild/legal status.
- Add save support in a new save wrapper version.
- Let the player establish one basic outpost on a discovered eligible body.
- Add a Manage Outpost screen with build/storage/status text.
- Tick production on in-game days.
- Keep resources simple: credits, salvage, survey data, ore cargo, staff, power, components.

### Pass 008B: Outpost progression

- Add facility build tree.
- Add staff assignment.
- Add upkeep and event checks.
- Let outposts store cargo/resources.
- Let outposts affect local system values: market, conflict, guild standing, discovery, pirate pressure.

### Pass 009A: Fleet ownership MVP

- Add an `OwnedVessel` or `FleetShip` data type containing a serialized ship, captain index, current system, destination, order, due day, cargo/profit, hull/fuel, and status.
- Let the player keep old ships instead of always trading them in.
- Let the player assign a hireling captain.
- Add one simple order: Follow Convoy.
- Add one remote order: Local Trade Run.

### Pass 009B: Fleet assignments

- Add survey, mining, bounty, courier, smuggling, and resupply orders.
- Add daily assignment resolution.
- Add fleet reports.
- Add captain skill checks and risk summaries.
- Add rescue/capture/delay outcomes.

### Pass 010: Holdings and logistics

- Add Holdings screen.
- Add cargo/storage transfer between player ship, outposts, and fleet ships.
- Add automated trade routes between two known systems/outposts.
- Add outpost defense assignment.
- Add faction taxes, permits, and illegal hidden-base risks.

### Pass 011: Faction alliance and influence

- Make guild standing unlock charters, fleet licenses, restricted modules, and faction missions.
- Let factions request help, punish betrayal, and compete over systems.
- Add local political control drift based on player actions, fleets, outposts, and events.

### Pass 012: Empire/endgame layer

- Add system charters and governorship.
- Add faction leadership or patronage paths.
- Replace simple moon retirement with multiple legacy endings.
- Add high score categories for trader, pirate, explorer, governor, faction leader, and founder.

## First design choices to settle later

- How many outposts should a normal game support before the UI becomes annoying?
- Should old ships be stored for free, or only if the player has a station lease/outpost dock?
- Should fleet captains use the existing mercenary pool permanently, or do we need a separate captain roster?
- Should outpost production use cargo goods directly, abstract resources, or a hybrid?
- How harsh should fleet loss be?
- Should illegal/pirate holdings be a full playstyle or a risky side option?
- Does "system ownership" mean legal charter, economic dominance, military control, or all three?

## Suggested first playable slice

The smallest version worth building:

1. Add one outpost slot on discovered bodies.
2. Let the player build Claim Beacon, Hab Module, Extractor, Storage Vault, and Docking Pad.
3. Outpost produces small salvage/ore/survey output once per in-game day.
4. Add one random outpost event every few days.
5. Let the player store resources at the outpost.
6. Add a Fleet screen with one extra ship and one captain.
7. Let that captain either follow the player or run a simple trade/survey assignment.

That gives the player the new fantasy quickly: "I own a place, I have a person working for me, and the galaxy keeps moving while I do something else."
