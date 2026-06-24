/*
 * Space Trader 2 local-system gameplay layer.
 * Copyright (c) 2026 Space Trader 2 contributors
 * Licensed under the GNU General Public License, version 2 or later.
 */
package de.anderdonau.spacetrader;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Popup;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;
import de.anderdonau.spacetrader.DataTypes.StellarBody;
import de.anderdonau.spacetrader.DataTypes.Tradeitems;

public class FragmentLocalSystem extends MyFragment {
	private LinearLayout container;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		View root = inflater.inflate(R.layout.fragment_local_system, parent, false);
		container = (LinearLayout) root.findViewById(R.id.localSystemContainer);
		update();
		return root;
	}

	@Override
	public boolean update() {
		if (container == null || gameState == null) {
			return false;
		}
		gameState.ensureExpansionState();
		container.removeAllViews();
		final SolarSystem system = gameState.SolarSystem[gameState.Mercenary[0].curSystem];

		container.addView(label("Local System: " + main.GetSystemName(system), true));
		container.addView(label("Dominant species: " + gameState.getAlienSpeciesName(system.dominantSpecies), false));
		container.addView(label("Controlling guild: " + gameState.getGuildName(system.controllingGuild), false));
		container.addView(label("Conflict: " + system.conflictLevel + "/10   Discovery value: " + system.discoveryValue + "   Local time: " + gameState.LocalActionClock + "/3", false));
		container.addView(label("Route advisory: " + gameState.systemTravelAdvisory(system), false));
		container.addView(label("Local history: " + gameState.systemHistoryLine(system), false));
		container.addView(label("Port rumors: " + gameState.systemRumorLine(system), false));
		container.addView(label("Opportunities: " + gameState.systemOpportunityLine(system), false));
		container.addView(label("Resources: Survey Data " + gameState.SurveyData + " | Salvage " + gameState.SalvageParts + " | Relics " + gameState.AlienRelics + " | Exotic " + gameState.ExoticMatter + " | Bounties " + gameState.BountyVouchers, false));
		container.addView(label("Modules: " + gameState.moduleSummary(), false));
		container.addView(label("Guild standing: " + guildStandingLine(), false));
		if (gameState.ActiveContractType != GameState.CONTRACT_NONE) {
			container.addView(label("Active contract: " + gameState.contractName() + " for " + gameState.getGuildName(gameState.ActiveContractGuild) + " by day " + gameState.ActiveContractDeadline, false));
			container.addView(label("Objective: " + gameState.activeContractObjectiveLine(), false));
		} else {
			container.addView(label("Active contract: none", false));
		}

		LinearLayout topButtons = row();
		topButtons.addView(button("Long Scan", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				scanSystem(system);
			}
		}));
		topButtons.addView(button("Guild Job", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showResult("Guild Board", gameState.acceptLocalGuildContract());
			}
		}));
		container.addView(topButtons);

		LinearLayout secondButtons = row();
		secondButtons.addView(button("Local Trade", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				localTrade(system);
			}
		}));
		secondButtons.addView(button("Hunt Pirates", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				huntPirates(system);
			}
		}));
		container.addView(secondButtons);

		LinearLayout thirdButtons = row();
		thirdButtons.addView(button("Diplomacy", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				diplomacy(system);
			}
		}));
		thirdButtons.addView(button("Smuggle Contact", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				smuggleContact(system);
			}
		}));
		container.addView(thirdButtons);

		container.addView(label("Ship system upgrades", true));
		LinearLayout up1 = row();
		up1.addView(button("Scanner", upgradeClick(0)));
		up1.addView(button("Mining", upgradeClick(1)));
		container.addView(up1);
		LinearLayout up2 = row();
		up2.addView(button("Salvage", upgradeClick(2)));
		up2.addView(button("Trade", upgradeClick(3)));
		container.addView(up2);
		LinearLayout up3 = row();
		up3.addView(button("Combat", upgradeClick(4)));
		up3.addView(button("Smuggle", upgradeClick(5)));
		container.addView(up3);

		container.addView(label("Bodies, stations, and contacts", true));
		for (int i = 0; i < system.bodies.length; i++) {
			final int bodyIndex = i;
			final StellarBody body = system.bodies[i];
			container.addView(label(bodyTitle(system, body), false));
			LinearLayout bodyButtons = row();
			bodyButtons.addView(button("Explore", new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					exploreBody(system, body, bodyIndex);
				}
			}));
			bodyButtons.addView(button("Mine/Salvage", new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mineOrSalvage(system, body, bodyIndex);
				}
			}));
			container.addView(bodyButtons);
		}
		return true;
	}

	private TextView label(String text, boolean heading) {
		TextView tv = new TextView(main);
		tv.setText(text);
		tv.setTextSize(heading ? 18 : 14);
		if (heading) {
			tv.setTypeface(Typeface.DEFAULT_BOLD);
			int pad = (int) (8 * getResources().getDisplayMetrics().density);
			tv.setPadding(0, pad, 0, pad / 2);
		}
		return tv;
	}

	private LinearLayout row() {
		LinearLayout ll = new LinearLayout(main);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		return ll;
	}

	private Button button(String text, View.OnClickListener listener) {
		Button b = new Button(main);
		b.setText(text);
		b.setAllCaps(false);
		b.setOnClickListener(listener);
		b.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		return b;
	}

	private View.OnClickListener upgradeClick(final int module) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showResult("Upgrade", gameState.upgradeModule(module));
			}
		};
	}

	private String guildStandingLine() {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < GameState.MAXGUILD; i++) {
			if (i > 0) {
				text.append(" | ");
			}
			text.append(gameState.getGuildName(i).replace(" Guild", "").replace(" Compact", "").replace(" League", "")).append(" ").append(gameState.GuildStanding[i]);
		}
		return text.toString();
	}

	private String bodyTitle(SolarSystem system, StellarBody body) {
		String name = main.GetSystemName(system) + "-" + body.orbit;
		String text = name + ": " + body.shortStatus();
		if (body.discovered) {
			text += " | " + gameState.getAlienSpeciesName(body.species) + " | " + gameState.getGuildName(body.controllingGuild);
			text += " | danger " + body.danger + " | resources " + body.resourceRichness + " | market " + body.marketStrength;
		}
		text += "\n  " + gameState.bodyIntelLine(system, body);
		return text;
	}

	private void scanSystem(SolarSystem system) {
		int discovered = 0;
		int scanLimit = 2 + gameState.ScannerLevel + gameState.Ship.PilotSkill() / 4 + (gameState.Ship.HasGadget(GameState.SENSORANALYZER) ? 2 : 0);
		for (int i = 0; i < system.bodies.length && discovered < scanLimit; i++) {
			if (!system.bodies[i].discovered) {
				system.bodies[i].discovered = true;
				discovered++;
			}
		}
		int data = Math.max(1, discovered + system.discoveryValue / 50 + gameState.ScannerLevel / 2 + (gameState.Ship.HasGadget(GameState.SENSORANALYZER) ? 1 : 0));
		gameState.SurveyData += data;
		gameState.GuildStanding[GameState.GUILD_EXPLORERS] += 1;
		String result = "Scanned " + main.GetSystemName(system) + ". New contacts: " + discovered + ". Survey data +" + data + ".";
		result += gameState.advanceActiveContractProgress(GameState.CONTRACT_SURVEY, Math.max(1, discovered));
		result += spendLocalTime(1);
		showResult("Long Range Scan", result);
	}

	private void exploreBody(SolarSystem system, StellarBody body, int bodyIndex) {
		body.discovered = true;
		body.surveyed = true;
		int roll = gameState.GetRandom(100);
		int credits = 50 + system.discoveryValue + body.anomalyLevel * 80 + gameState.Ship.PilotSkill() * 15 + gameState.ScannerLevel * 35 + (gameState.Ship.HasGadget(GameState.SENSORANALYZER) ? 125 : 0);
		String result = "Explored " + main.GetSystemName(system) + "-" + body.orbit + ". ";
		if (body.hasRuins || roll < body.anomalyLevel * (8 + gameState.ScannerLevel + (gameState.Ship.HasGadget(GameState.SENSORANALYZER) ? 3 : 0))) {
			gameState.AlienRelics += 1;
			gameState.ExoticMatter += body.anomalyLevel + gameState.ScannerLevel >= 8 ? 1 : 0;
			gameState.GuildStanding[GameState.GUILD_SCIENTISTS] += 2;
			gameState.Credits += credits;
			result += "You found alien ruins and sold first-look rights for " + credits + " credits.";
		} else if (body.hasDerelict) {
			int salvage = 1 + gameState.GetRandom(3 + Math.max(1, body.resourceRichness) + gameState.SalvageDroneLevel + (gameState.Ship.HasGadget(GameState.DRONECONTROL) ? 2 : 0));
			gameState.SalvageParts += salvage;
			gameState.GuildStanding[GameState.GUILD_EXPLORERS] += 1;
			result += "You mapped a derelict and recovered " + salvage + " salvage parts.";
		} else {
			int data = 1 + body.anomalyLevel / 3 + gameState.ScannerLevel / 2 + (gameState.Ship.HasGadget(GameState.SENSORANALYZER) ? 1 : 0);
			gameState.SurveyData += data;
			gameState.Credits += credits / 2;
			gameState.GuildStanding[GameState.GUILD_EXPLORERS] += 1;
			result += "Survey data +" + data + " and sold charts for " + (credits / 2) + " credits.";
		}
		result += gameState.advanceActiveContractProgress(GameState.CONTRACT_SURVEY, 1);
		if (body.danger > gameState.Ship.PilotSkill() + gameState.Ship.EngineerSkill() / 2 + gameState.ScannerLevel && !(gameState.DebugEnabled && gameState.DebugGodHull)) {
			int damage = Math.max(1, 1 + gameState.GetRandom(5 + body.danger * 2) - gameState.ScannerLevel);
			gameState.Ship.hull = Math.max(1, gameState.Ship.hull - damage);
			result += " The landing was rough: hull lost " + damage + ".";
		}
		result += spendLocalTime(1);
		showResult("Exploration", result);
	}

	private void mineOrSalvage(SolarSystem system, StellarBody body, int bodyIndex) {
		body.discovered = true;
		if (body.depleted) {
			showResult("Mine/Salvage", "This location has already been stripped for now.");
			return;
		}
		String result;
		if (body.hasDerelict) {
			int salvage = 2 + gameState.GetRandom(4 + Math.max(1, body.resourceRichness) + gameState.SalvageDroneLevel * 2 + (gameState.Ship.HasGadget(GameState.DRONECONTROL) ? 3 : 0));
			gameState.SalvageParts += salvage;
			gameState.Credits += salvage * (80 + gameState.SalvageDroneLevel * 15);
			gameState.GuildStanding[GameState.GUILD_EXPLORERS] += 1;
			result = "Recovered " + salvage + " salvage parts from the derelict and sold scrap manifests for " + (salvage * (80 + gameState.SalvageDroneLevel * 15)) + " credits.";
		} else if (body.resourceRichness > 0 || body.type == StellarBody.ASTEROID_BELT) {
			int ore = 1 + gameState.GetRandom(2 + body.resourceRichness + gameState.MiningRigLevel * 2 + (gameState.Ship.HasGadget(GameState.DRONECONTROL) ? 2 : 0));
			int free = gameState.Ship.TotalCargoBays() - gameState.Ship.FilledCargoBays();
			int loaded = Math.min(free, ore);
			gameState.Ship.cargo[GameState.ORE] += loaded;
			gameState.BuyingPrice[GameState.ORE] += loaded * 10;
			gameState.SalvageParts += Math.max(0, ore - loaded);
			gameState.GuildStanding[GameState.GUILD_MINERS] += 1;
			result = "Mined " + ore + " ore-equivalent. Loaded " + loaded + " ore into cargo; extra yield became salvage parts.";
		} else {
			result = "There is nothing worth mining here.";
		}
		if (gameState.GetRandom(100) < 25 + body.danger * 4 - gameState.MiningRigLevel * 4 && !(gameState.DebugEnabled && gameState.DebugGodHull)) {
			int damage = Math.max(1, 1 + gameState.GetRandom(8 + body.danger) - gameState.MiningRigLevel);
			gameState.Ship.hull = Math.max(1, gameState.Ship.hull - damage);
			result += " Mining hazards damaged hull by " + damage + ".";
		}
		body.resourceRichness = Math.max(0, body.resourceRichness - Math.max(1, 3 - gameState.MiningRigLevel / 2));
		body.depleted = body.resourceRichness <= 0 || body.hasDerelict;
		result += spendLocalTime(1);
		showResult("Mine/Salvage", result);
	}

	private void localTrade(SolarSystem system) {
		int bestMarket = 0;
		for (int i = 0; i < system.bodies.length; i++) {
			bestMarket = Math.max(bestMarket, system.bodies[i].marketStrength);
		}
		int good = system.economyFocus;
		int reward = 100 + bestMarket * 90 + gameState.Ship.TraderSkill() * 25 + gameState.GuildStanding[GameState.GUILD_TRADERS] * 4 + gameState.TradeNetworkLevel * 70 + (gameState.Ship.HasGadget(GameState.TRADEUPLINK) ? 150 : 0);
		String result;
		if (bestMarket <= 0) {
			result = "There is no meaningful local market in this system.";
		} else if (gameState.Ship.cargo[good] > 0) {
			gameState.Ship.cargo[good] -= 1;
			gameState.Credits += reward;
			gameState.GuildStanding[GameState.GUILD_TRADERS] += 2;
			result = "Local brokers wanted " + Tradeitems.mTradeitems[good].name + ". Sold 1 unit through side channels for " + reward + " credits.";
		} else {
			int cost = Math.max(10, reward / (3 + gameState.TradeNetworkLevel / 2));
			if (gameState.Credits >= cost && gameState.Ship.FilledCargoBays() < gameState.Ship.TotalCargoBays()) {
				gameState.Credits -= cost;
				gameState.Ship.cargo[good] += 1;
				gameState.BuyingPrice[good] += cost;
				gameState.GuildStanding[GameState.GUILD_TRADERS] += 1;
				result = "Bought 1 unit of locally favored " + Tradeitems.mTradeitems[good].name + " for " + cost + " credits.";
			} else {
				result = "The market has a lead on " + Tradeitems.mTradeitems[good].name + ", but you need cargo space and credits.";
			}
		}
		result += spendLocalTime(1);
		showResult("Local Trade", result);
	}

	private void huntPirates(SolarSystem system) {
		int piratePressure = system.guildInfluence[GameState.GUILD_PIRATES] + system.conflictLevel * 5;
		if (piratePressure < 35) {
			showResult("Hunt Pirates", "No strong pirate signature here. Try a more dangerous system.");
			return;
		}
		int combat = gameState.Ship.FighterSkill() + gameState.Ship.PilotSkill() + gameState.Ship.TotalWeapons(-1, -1) / 20 + gameState.CombatTacticsLevel * 2;
		int difficulty = 8 + system.conflictLevel + gameState.GetRandom(12);
		int reward = 300 + piratePressure * 12 + gameState.GetRandom(500) + gameState.CombatTacticsLevel * 85;
		String result;
		if (combat + gameState.GetRandom(12) >= difficulty || (gameState.DebugEnabled && gameState.DebugGodHull)) {
			gameState.Credits += reward;
			gameState.PirateKills += 1;
			gameState.ReputationScore += 2;
			gameState.BountyVouchers += 1;
			gameState.GuildStanding[GameState.GUILD_MERCENARIES] += 2;
			gameState.GuildStanding[GameState.GUILD_PIRATES] -= 2;
			system.conflictLevel = Math.max(0, system.conflictLevel - 1);
			result = "You cleared a pirate cell and collected " + reward + " credits. Conflict in this system dropped to " + system.conflictLevel + "/10.";
			result += gameState.advanceActiveContractProgress(GameState.CONTRACT_BOUNTY, 1);
		} else {
			int damage = Math.max(1, 5 + gameState.GetRandom(15 + system.conflictLevel * 2) - gameState.CombatTacticsLevel * 2);
			gameState.Ship.hull = Math.max(1, gameState.Ship.hull - damage);
			gameState.GuildStanding[GameState.GUILD_MERCENARIES] -= 1;
			result = "The pirate cell fought harder than expected. You escaped, but hull dropped by " + damage + ".";
		}
		result += spendLocalTime(1);
		showResult("Hunt Pirates", result);
	}

	private void diplomacy(SolarSystem system) {
		int guild = system.controllingGuild;
		int costCredits = 150 + Math.max(0, gameState.GuildStanding[guild]) * 5;
		String result;
		if (gameState.SurveyData >= 1 && gameState.Credits >= costCredits) {
			gameState.SurveyData -= 1;
			gameState.Credits -= costCredits;
			gameState.GuildStanding[guild] += 2;
			if (system.conflictLevel > 0 && gameState.GetRandom(100) < 30 + gameState.ScannerLevel * 5) {
				system.conflictLevel -= 1;
				result = "You shared survey intel with " + gameState.getGuildName(guild) + ". Standing +2 and local conflict dropped to " + system.conflictLevel + "/10.";
			} else {
				result = "You shared survey intel with " + gameState.getGuildName(guild) + ". Standing +2.";
			}
		} else {
			result = "Diplomacy needs 1 survey data and " + costCredits + " credits for translation, docking, and bribes.";
		}
		result += spendLocalTime(1);
		showResult("Diplomacy", result);
	}

	private void smuggleContact(SolarSystem system) {
		int good = gameState.GetRandom(100) < 55 ? GameState.NARCOTICS : GameState.FIREARMS;
		int cost = Math.max(75, gameState.StandardPrice(good, system.size, system.techLevel, system.politics, system.specialResources));
		cost = (cost * Math.max(45, 125 - gameState.SmugglerHoldLevel * 10 - (gameState.Ship.HasGadget(GameState.SMUGGLERHOLD) ? 15 : 0))) / 100;
		String result;
		if (gameState.Ship.FilledCargoBays() >= gameState.Ship.TotalCargoBays()) {
			result = "You found a black-market contact, but your cargo bays are full.";
		} else if (gameState.Credits < cost) {
			result = "A black-market contact offers " + Tradeitems.mTradeitems[good].name + " for " + cost + " credits, but you cannot afford it.";
		} else {
			gameState.Credits -= cost;
			gameState.Ship.cargo[good] += 1;
			gameState.BuyingPrice[good] += cost;
			gameState.GuildStanding[GameState.GUILD_PIRATES] += 1;
			gameState.PoliceRecordScore -= Math.max(0, 2 - gameState.SmugglerHoldLevel / 2 - (gameState.Ship.HasGadget(GameState.SMUGGLERHOLD) ? 1 : 0));
			result = "Bought 1 hidden unit of " + Tradeitems.mTradeitems[good].name + " for " + cost + " credits. Pirate standing +1.";
		}
		result += spendLocalTime(1);
		showResult("Smuggle Contact", result);
	}

	private String spendLocalTime(int ticks) {
		gameState.LocalActionClock += Math.max(0, ticks);
		if (gameState.LocalActionClock >= 3) {
			gameState.LocalActionClock -= 3;
			main.IncDays(1);
			String contract = gameState.checkGuildContractArrival();
			return " A local operations day passed." + (contract.length() > 0 ? " " + contract : "");
		}
		return "";
	}

	private void showResult(String title, String message) {
		main.addPopup(new Popup(main, title, message, "", "OK", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				main.cbShowNextPopup.execute(popup, view);
				update();
			}
		}));
		main.saveGame();
	}
}
