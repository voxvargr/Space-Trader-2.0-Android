/*
 * Space Trader 2 fleet management.
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
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

public class FragmentFleetManagement extends MyFragment {
	private LinearLayout container;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		View root = inflater.inflate(R.layout.fragment_fleet_management, parent, false);
		container = (LinearLayout) root.findViewById(R.id.fleetManagementContainer);
		update();
		return root;
	}

	@Override
	public boolean update() {
		if (container == null || gameState == null) {
			return false;
		}
		gameState.ensureExpansionState();
		gameState.sanitizeTravelDuties();
		container.removeAllViews();

		container.addView(label("Fleet Management", true));
		container.addView(label("Travel group: " + gameState.getPlayerTravelFleetSize() + " ship" + (gameState.getPlayerTravelFleetSize() == 1 ? "" : "s"), false));
		container.addView(label("Current ship: " + ShipTypes.ShipTypes[gameState.Ship.type].name, false));

		container.addView(label("Travel duties", true));
		addDuty("Repairs", GameState.TRAVELDUTY_REPAIR, "Engineering support and escort repair help");
		addDuty("Scanning", GameState.TRAVELDUTY_SCANNER, "Pilot-led route scanning and escape options");
		addDuty("Targeting", GameState.TRAVELDUTY_TARGETING, "Fighter support during fleet encounters");
		addDuty("Transmissions", GameState.TRAVELDUTY_TRANSMISSIONS, "Trader comms, bribes, and encounter signals");

		LinearLayout actions = row();
		actions.addView(button("Clear duties", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.clearTravelDuties();
				main.saveGame();
				update();
			}
		}));
		actions.addView(button("Hirelings", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				main.changeFragment(Main.FRAGMENTS.PERSONNEL_ROSTER);
			}
		}));
		container.addView(actions);

		container.addView(label("Crew aboard", true));
		for (int i = 0; i < GameState.MAXCREW; i++) {
			int crewIndex = gameState.Ship.crew[i];
			if (gameState.isTravelCrewAvailable(crewIndex)) {
				container.addView(label("Ship " + (i + 1) + ": " + crewName(crewIndex), false));
			}
		}
		return true;
	}

	private void addDuty(final String title, final int duty, String description) {
		container.addView(label(title + ": " + dutySummary(duty), false));
		container.addView(label(description, false));
		LinearLayout line = row();
		line.addView(button("Cycle " + title, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gameState.cycleTravelDuty(duty);
				main.saveGame();
				update();
			}
		}));
		container.addView(line);
	}

	private String dutySummary(int duty) {
		int crewIndex = gameState.getTravelDutyCrew(duty);
		if (!gameState.isTravelCrewAvailable(crewIndex)) {
			return "unassigned";
		}
		return crewName(crewIndex) + ", ship " + gameState.travelDutyShipNumber(duty) + ", bonus +" + gameState.travelDutySkillBonus(duty);
	}

	private String crewName(int crewIndex) {
		if (crewIndex < 0 || crewIndex >= gameState.Mercenary.length || gameState.Mercenary[crewIndex] == null) {
			return "Unassigned";
		}
		int nameIndex = gameState.Mercenary[crewIndex].nameIndex;
		if (nameIndex < 0 || nameIndex >= main.MercenaryName.length) {
			return "Crew " + crewIndex;
		}
		return main.MercenaryName[nameIndex];
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
