/*
 * Temporary Space Trader 2 development/debug menu.
 * Copyright (c) 2026 Space Trader 2 contributors
 * Licensed under the GNU General Public License, version 2 or later.
 */
package de.anderdonau.spacetrader;

import android.graphics.Typeface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Popup;
import de.anderdonau.spacetrader.DataTypes.Shields;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

public class FragmentDebugMenu extends MyFragment {
	private LinearLayout container;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		View root = inflater.inflate(R.layout.fragment_debug_menu, parent, false);
		container = (LinearLayout) root.findViewById(R.id.debugMenuContainer);
		update();
		return root;
	}

	@Override
	public boolean update() {
		container.removeAllViews();
		container.addView(label("Debug Menu", true));
		if (!gameState.DebugEnabled) {
			container.addView(label("Debug tools are disabled. Go to Options and enable debug tools first.", false));
			return true;
		}
		container.addView(label("Temporary testing tools. Disable before serious play. Enabling this through Options marks the game as cheat mode for scoring.", false));
		container.addView(label("Credits: " + gameState.Credits + " | Hull: " + gameState.Ship.hull + "/" + gameState.Ship.GetHullStrength() + " | Fuel: " + gameState.Ship.GetFuel() + "/" + gameState.Ship.GetFuelTanks(), false));
		container.addView(label("God Hull: " + (gameState.DebugGodHull ? "ON" : "OFF") + " | Unlimited Debug Travel: " + (gameState.DebugUnlimitedFuel ? "ON" : "OFF") + " | Encounter Cheats: " + (gameState.DebugEncounterCheats ? "ON" : "OFF"), false));

		LinearLayout row1 = row();
		row1.addView(button("+10k credits", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.Credits += 10000;
				done("Added 10,000 credits.");
			}
		}));
		row1.addView(button("+100k credits", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.Credits += 100000;
				done("Added 100,000 credits.");
			}
		}));
		container.addView(row1);

		LinearLayout row2 = row();
		row2.addView(button("Repair hull", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.Ship.hull = gameState.Ship.GetHullStrength();
				done("Hull repaired.");
			}
		}));
		row2.addView(button("Refill fuel", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.Ship.fuel = gameState.Ship.GetFuelTanks();
				done("Fuel refilled.");
			}
		}));
		container.addView(row2);

		LinearLayout row3 = row();
		row3.addView(button(gameState.DebugGodHull ? "God hull OFF" : "God hull ON", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.DebugGodHull = !gameState.DebugGodHull;
				if (gameState.DebugGodHull) {
					gameState.Ship.hull = gameState.Ship.GetHullStrength();
				}
				done("God hull " + (gameState.DebugGodHull ? "enabled." : "disabled."));
			}
		}));
		row3.addView(button(gameState.DebugUnlimitedFuel ? "Debug travel OFF" : "Debug travel ON", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.DebugUnlimitedFuel = !gameState.DebugUnlimitedFuel;
				if (gameState.DebugUnlimitedFuel) {
					gameState.Ship.fuel = gameState.Ship.GetFuelTanks();
				}
				done("Unlimited debug travel " + (gameState.DebugUnlimitedFuel ? "enabled." : "disabled."));
			}
		}));
		container.addView(row3);

		LinearLayout row3b = row();
		row3b.addView(button(gameState.DebugEncounterCheats ? "Encounter cheat buttons OFF" : "Encounter cheat buttons ON", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.DebugEncounterCheats = !gameState.DebugEncounterCheats;
				done("Encounter cheat buttons " + (gameState.DebugEncounterCheats ? "enabled. Travel encounters will show debug KO and surrender/plunder buttons." : "disabled."));
			}
		}));
		container.addView(row3b);

		LinearLayout row4 = row();
		row4.addView(button("Clear debt", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.Debt = 0;
				done("Debt cleared.");
			}
		}));
		row4.addView(button("Clear contract", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.clearGuildContract();
				done("Active guild contract cleared.");
			}
		}));
		container.addView(row4);

		LinearLayout row5 = row();
		row5.addView(button("Jump to target", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				debugJumpToWarpTarget();
			}
		}));
		row5.addView(button("Random system", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.WarpSystem = gameState.GetRandom(GameState.MAXSOLARSYSTEM);
				main.WarpSystem = gameState.SolarSystem[gameState.WarpSystem];
				debugJumpToWarpTarget();
			}
		}));
		container.addView(row5);

		LinearLayout row6 = row();
		row6.addView(button("Reveal current local system", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				for (int i = 0; i < gameState.SolarSystem[gameState.Mercenary[0].curSystem].bodies.length; i++) {
					gameState.SolarSystem[gameState.Mercenary[0].curSystem].bodies[i].discovered = true;
					gameState.SolarSystem[gameState.Mercenary[0].curSystem].bodies[i].surveyed = true;
				}
				done("Current local system revealed.");
			}
		}));
		container.addView(row6);

		LinearLayout row7 = row();
		row7.addView(button("Max ST2 modules", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.ScannerLevel = 5;
				gameState.MiningRigLevel = 5;
				gameState.SalvageDroneLevel = 5;
				gameState.TradeNetworkLevel = 5;
				gameState.CombatTacticsLevel = 5;
				gameState.SmugglerHoldLevel = 5;
				done("All Space Trader 2 modules set to level 5.");
			}
		}));
		row7.addView(button("+ST2 resources", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.SurveyData += 50;
				gameState.SalvageParts += 50;
				gameState.AlienRelics += 10;
				gameState.ExoticMatter += 5;
				gameState.BountyVouchers += 10;
				done("Added Space Trader 2 testing resources.");
			}
		}));
		container.addView(row7);

		LinearLayout row8 = row();
		row8.addView(button("Max commander stats", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				maxCommanderStats();
			}
		}));
		row8.addView(button("Grant debug ship", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				grantDebugShip();
			}
		}));
		container.addView(row8);
		return true;
	}

	private void maxCommanderStats() {
		gameState.CheatCounter = Math.max(gameState.CheatCounter, 3);
		gameState.Mercenary[0].pilot = GameState.MAXSKILL;
		gameState.Mercenary[0].fighter = GameState.MAXSKILL;
		gameState.Mercenary[0].trader = GameState.MAXSKILL;
		gameState.Mercenary[0].engineer = GameState.MAXSKILL;
		done("Commander pilot, fighter, trader, and engineer skills set to max.");
	}

	private void grantDebugShip() {
		gameState.CheatCounter = Math.max(gameState.CheatCounter, 3);
		gameState.Credits = Math.max(gameState.Credits, 9999999);
		gameState.Debt = 0;
		gameState.Ship.type = ShipTypes.GRASSHOPPER;
		gameState.CustomWeaponSlots = GameState.MAXWEAPON;
		gameState.CustomShieldSlots = GameState.MAXSHIELD;
		gameState.CustomGadgetSlots = GameState.MAXGADGET;
		gameState.CustomCargoDelta = Math.max(gameState.CustomCargoDelta, 1000);
		gameState.CustomHullBonus = Math.max(gameState.CustomHullBonus, 1000);
		gameState.DebugGodHull = true;
		gameState.DebugUnlimitedFuel = true;
		gameState.DebugEncounterCheats = true;
		gameState.ScannerLevel = 5;
		gameState.MiningRigLevel = 5;
		gameState.SalvageDroneLevel = 5;
		gameState.TradeNetworkLevel = 5;
		gameState.CombatTacticsLevel = 5;
		gameState.SmugglerHoldLevel = 5;
		gameState.SurveyData = Math.max(gameState.SurveyData, 999);
		gameState.SalvageParts = Math.max(gameState.SalvageParts, 999);
		gameState.AlienRelics = Math.max(gameState.AlienRelics, 999);
		gameState.ExoticMatter = Math.max(gameState.ExoticMatter, 999);
		gameState.BountyVouchers = Math.max(gameState.BountyVouchers, 999);

		gameState.Mercenary[0].pilot = GameState.MAXSKILL;
		gameState.Mercenary[0].fighter = GameState.MAXSKILL;
		gameState.Mercenary[0].trader = GameState.MAXSKILL;
		gameState.Mercenary[0].engineer = GameState.MAXSKILL;

		for (int i = 0; i < GameState.MAXTRADEITEM; i++) {
			gameState.Ship.cargo[i] = 0;
			gameState.BuyingPrice[i] = 0;
		}
		for (int i = 0; i < GameState.MAXWEAPON; i++) {
			gameState.Ship.weapon[i] = i < GameState.MAXWEAPONTYPE + GameState.EXTRAWEAPONS ? i : -1;
		}
		for (int i = 0; i < GameState.MAXSHIELD; i++) {
			if (i < GameState.MAXSHIELDTYPE + GameState.EXTRASHIELDS) {
				gameState.Ship.shield[i] = i;
				gameState.Ship.shieldStrength[i] = Shields.mShields[i].power;
			} else {
				gameState.Ship.shield[i] = -1;
				gameState.Ship.shieldStrength[i] = 0;
			}
		}
		for (int i = 0; i < GameState.MAXGADGET; i++) {
			gameState.Ship.gadget[i] = i < GameState.MAXGADGETTYPE + GameState.EXTRAGADGETS ? i : -1;
		}
		gameState.Ship.crew[0] = 0;
		for (int i = 1; i < GameState.MAXCREW; i++) {
			gameState.Ship.crew[i] = -1;
		}
		gameState.Ship.hull = gameState.Ship.GetHullStrength();
		gameState.Ship.fuel = gameState.Ship.GetFuelTanks();
		done("Debug ship granted with every weapon, shield, and gadget slot filled, plus huge cargo space and maxed testing stats.");
	}

	private void debugJumpToWarpTarget() {
		if (gameState.WarpSystem < 0 || gameState.WarpSystem >= GameState.MAXSOLARSYSTEM) {
			Toast.makeText(main, "No target selected. Pick a target on the galactic chart first.", Toast.LENGTH_SHORT).show();
			return;
		}
		gameState.DebugUnlimitedFuel = true;
		main.WarpSystem = gameState.SolarSystem[gameState.WarpSystem];
		main.DoWarp(false);
	}

	private TextView label(String text, boolean heading) {
		TextView tv = new TextView(main);
		tv.setText(text);
		tv.setTextSize(heading ? 18 : 14);
		if (heading) {
			tv.setTypeface(Typeface.DEFAULT_BOLD);
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
		b.setTextColor(Color.BLACK);
		b.setBackgroundResource(R.drawable.st2_button_retro);
		b.setPadding(6, 3, 6, 3);
		b.setOnClickListener(listener);
		b.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		return b;
	}

	private void done(String text) {
		main.saveGame();
		main.addPopup(new Popup(main, "Debug", text, "", "OK", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				main.cbShowNextPopup.execute(popup, view);
				update();
			}
		}));
	}
}
