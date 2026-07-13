/*
 * Space Trader 2 mission hub.
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

public class FragmentMissions extends MyFragment {
	private LinearLayout container;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		View root = inflater.inflate(R.layout.fragment_missions, parent, false);
		container = (LinearLayout) root.findViewById(R.id.missionsContainer);
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

		container.addView(label("Mission Board", true));
		container.addView(label("Current system: " + main.GetSystemName(system), false));

		container.addView(label("Classic quests", true));
		container.addView(label(main.getOpenQuestsText().trim(), false));
		LinearLayout classicActions = row();
		classicActions.addView(button("Commander Status", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				main.changeFragment(Main.FRAGMENTS.COMMANDER_STATUS);
			}
		}));
		classicActions.addView(button("Quest Popup", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				main.CommanderStatusQuestsCallback(view);
			}
		}));
		container.addView(classicActions);

		container.addView(label("Guild contracts", true));
		if (gameState.ActiveContractType == GameState.CONTRACT_NONE) {
			container.addView(label("No active guild contract.", false));
			LinearLayout contractActions = row();
			contractActions.addView(button("Take Local Contract", new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					showResult("Guild Board", gameState.acceptLocalGuildContract());
				}
			}));
			container.addView(contractActions);
		} else {
			container.addView(label(gameState.contractName() + " for " + gameState.getGuildName(gameState.ActiveContractGuild), false));
			container.addView(label("Objective: " + gameState.activeContractObjectiveLine(), false));
			container.addView(label("Deadline: day " + gameState.ActiveContractDeadline + "   Reward: " + gameState.ActiveContractReward + " cr.", false));
		}
		LinearLayout localActions = row();
		localActions.addView(button("Open Local System", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				main.changeFragment(Main.FRAGMENTS.LOCAL_SYSTEM);
			}
		}));
		container.addView(localActions);

		container.addView(label("Outposts and stations", true));
		container.addView(label(holdingSummary(system), false));
		LinearLayout holdingActions = row();
		holdingActions.addView(button("Local System", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				main.changeFragment(Main.FRAGMENTS.LOCAL_SYSTEM);
			}
		}));
		holdingActions.addView(button("System Info", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				main.changeFragment(Main.FRAGMENTS.SYSTEM_INFORMATION);
			}
		}));
		container.addView(holdingActions);
		return true;
	}

	private String holdingSummary(SolarSystem system) {
		int holdings = 0;
		int markets = 0;
		int derelicts = 0;
		int ruins = 0;
		for (int i = 0; i < system.bodies.length; i++) {
			StellarBody body = system.bodies[i];
			if (body.hasBase || body.type == StellarBody.ORBITAL_STATION || body.type == StellarBody.TRADE_MOON) {
				holdings++;
			}
			if (body.hasMarket) {
				markets++;
			}
			if (body.hasDerelict) {
				derelicts++;
			}
			if (body.hasRuins) {
				ruins++;
			}
		}
		return "Local candidates: " + holdings + " bases/stations, " + markets + " markets, " + derelicts + " derelicts, " + ruins + " ancient sites. Inspect bodies in Local System to build this into owned holdings and station work.";
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
}
