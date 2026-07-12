/*
 * Copyright (c) 2014 Benjamin Schieder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */

package de.anderdonau.spacetrader;

import android.graphics.Typeface;
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
import de.anderdonau.spacetrader.DataTypes.Weapons;

public class FragmentBuyEquipment extends MyFragment {
	View rootView;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_buy_equipment, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		TextView tv = (TextView) rootView.findViewById(R.id.txtBuyEquipmentCash);
		tv.setText(String.format("Cash: %d cr.", gameState.Credits));

		ScrollView scroll = (ScrollView) rootView.findViewById(R.id.scrollView);
		TableLayout table = (TableLayout) scroll.getChildAt(0);
		table.removeAllViews();

		addHeader(table, "Weapons - slots " + gameState.FilledSlots(gameState.Ship.weapon) + "/" + gameState.EffectiveWeaponSlots() + " | total power " + gameState.Ship.TotalWeapons(-1, -1));
		for (int i = 0; i < GameState.MAXWEAPONTYPE; i++) {
			int price = gameState.BASEWEAPONPRICE(i);
			addEquipmentRow(table, Main.EQUIP_WEAPON, i, Weapons.mWeapons[i].name,
				"Power " + Weapons.mWeapons[i].power + " | TL " + Weapons.mWeapons[i].techLevel,
				price, Weapons.mWeapons[i].description);
		}

		addHeader(table, "Shields - slots " + gameState.FilledSlots(gameState.Ship.shield) + "/" + gameState.EffectiveShieldSlots() + " | capacity " + gameState.Ship.TotalShields());
		for (int i = 0; i < GameState.MAXSHIELDTYPE; i++) {
			int price = gameState.BASESHIELDPRICE(i);
			addEquipmentRow(table, Main.EQUIP_SHIELD, i, Shields.mShields[i].name,
				"Capacity " + Shields.mShields[i].power + " | TL " + Shields.mShields[i].techLevel,
				price, Shields.mShields[i].description);
		}

		addHeader(table, "Equipment - slots " + gameState.FilledSlots(gameState.Ship.gadget) + "/" + gameState.EffectiveGadgetSlots());
		for (int i = 0; i < GameState.MAXGADGETTYPE; i++) {
			int price = gameState.BASEGADGETPRICE(i);
			addEquipmentRow(table, Main.EQUIP_GADGET, i, Gadgets.mGadgets[i].name,
				gameState.gadgetComparisonLine(i), price, Gadgets.mGadgets[i].description);
		}
		return true;
	}

	private void addHeader(TableLayout table, String text) {
		TableRow row = new TableRow(getActivity());
		TextView header = new TextView(getActivity());
		header.setText(text);
		header.setTextSize(18);
		header.setPadding(4, 14, 4, 6);
		header.setTypeface(null, Typeface.BOLD);
		row.addView(header, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		table.addView(row);
	}

	private void addEquipmentRow(TableLayout table, final int kind, final int index, String name, String stats, int price, String description) {
		TableRow row = new TableRow(getActivity());
		row.setPadding(0, 2, 0, 2);

		STButton btn = new STButton(getActivity());
		btn.setText(gameState.hasBetterOrEqualGadget(index) && kind == Main.EQUIP_GADGET ? "Owned" : price > 0 ? "Buy" : "--");
		btn.setEnabled(price > 0 && !(kind == Main.EQUIP_GADGET && gameState.hasBetterOrEqualGadget(index)));
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((Main) getActivity()).BuyEquipmentDirect(kind, index);
			}
		});
		row.addView(btn, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

		TextView info = new TextView(getActivity());
		String priceText = price > 0 ? price + " cr." : "not sold here";
		info.setText(name + "\n" + stats + "\n" + priceText + (description == null || description.length() == 0 ? "" : "\n" + description));
		info.setPadding(6, 0, 4, 0);
		row.addView(info, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));

		table.addView(row);
	}
}
