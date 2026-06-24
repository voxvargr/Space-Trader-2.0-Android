/*
 * Copyright (c) 2014 Benjamin Schieder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */

package de.anderdonau.spacetrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.Gadgets;
import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.STButton;
import de.anderdonau.spacetrader.DataTypes.Shields;
import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.Weapons;

public class FragmentSellEquipment extends MyFragment {
	View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_sell_equipment, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		TextView tv = (TextView) rootView.findViewById(R.id.txtSellEquipmentCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));
		ScrollView scroll = (ScrollView) rootView.findViewById(R.id.scrollView);
		TableLayout table = (TableLayout) scroll.getChildAt(0);
		table.removeAllViews();
		Ship ship = gameState.Ship;

		addHeader(table, "Weapons");
		for (int i = 0; i < GameState.MAXWEAPON; i++) {
			if (ship.weapon[i] >= 0) {
				addRow(table, Main.EQUIP_WEAPON, i, Weapons.mWeapons[ship.weapon[i]].name, gameState.WEAPONSELLPRICE(i));
			}
		}
		addHeader(table, "Shields");
		for (int i = 0; i < GameState.MAXSHIELD; i++) {
			if (ship.shield[i] >= 0) {
				addRow(table, Main.EQUIP_SHIELD, i, Shields.mShields[ship.shield[i]].name + " (" + ship.shieldStrength[i] + "/" + Shields.mShields[ship.shield[i]].power + ")", gameState.SHIELDSELLPRICE(i));
			}
		}
		addHeader(table, "Equipment");
		for (int i = 0; i < GameState.MAXGADGET; i++) {
			if (ship.gadget[i] >= 0) {
				addRow(table, Main.EQUIP_GADGET, i, Gadgets.mGadgets[ship.gadget[i]].name, gameState.GADGETSELLPRICE(i));
			}
		}
		return true;
	}

	private void addHeader(TableLayout table, String text) {
		TableRow row = new TableRow(getActivity());
		TextView header = new TextView(getActivity());
		header.setText(text);
		header.setTextSize(18);
		header.setTypeface(null, 1);
		header.setPadding(4, 14, 4, 6);
		row.addView(header, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		table.addView(row);
	}

	private void addRow(TableLayout table, final int kind, final int slot, String name, int price) {
		TableRow row = new TableRow(getActivity());
		STButton btn = new STButton(getActivity());
		btn.setText("Sell");
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((Main) getActivity()).SellEquipmentDirect(kind, slot);
			}
		});
		row.addView(btn, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

		TextView info = new TextView(getActivity());
		info.setText(name + "\n" + price + " cr.");
		info.setPadding(6, 0, 4, 0);
		row.addView(info, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
		table.addView(row);
	}
}
