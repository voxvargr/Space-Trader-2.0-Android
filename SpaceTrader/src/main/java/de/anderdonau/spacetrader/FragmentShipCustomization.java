/*
 * Space Trader 2.0 expansion fragment.
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 */

package de.anderdonau.spacetrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.STButton;

public class FragmentShipCustomization extends MyFragment {
    View rootView;

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.gameState = (GameState) getArguments().getSerializable("gamestate");
        rootView = inflater.inflate(R.layout.fragment_ship_customization, container, false);
        update();
        return rootView;
    }

    @Override
    public boolean update() {
        TextView cash = (TextView) rootView.findViewById(R.id.txtShipCustomizationCash);
        cash.setText(String.format("Cash: %d cr.", gameState.Credits));

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.layoutShipCustomization);
        layout.removeAllViews();

        addText(layout, gameState.shipCustomizationSummary());
        addText(layout, "Shipyards can permanently refit your current hull. Buying a new ship resets these custom modifications, so use this when you want to specialize the ship you already have.");
        addText(layout, "Filled cargo bays: " + gameState.Ship.FilledCargoBays() + "/" + gameState.Ship.TotalCargoBays());
        addText(layout, "Installed slots: weapons " + gameState.FilledSlots(gameState.Ship.weapon) + "/" + gameState.EffectiveWeaponSlots() +
            ", shields " + gameState.FilledSlots(gameState.Ship.shield) + "/" + gameState.EffectiveShieldSlots() +
            ", gadgets " + gameState.FilledSlots(gameState.Ship.gadget) + "/" + gameState.EffectiveGadgetSlots());

        addButton(layout, 0, "Trade 5 cargo bays for a weapon hardpoint - 4000 cr.");
        addButton(layout, 1, "Trade 5 cargo bays for a shield socket - 3500 cr.");
        addButton(layout, 2, "Trade 5 cargo bays for a gadget bay - 3000 cr.");
        addButton(layout, 3, "Convert 1 empty weapon hardpoint into a gadget bay - 2500 cr.");
        addButton(layout, 4, "Convert 1 empty shield socket into a weapon hardpoint - 3000 cr.");
        addButton(layout, 5, "Trade 5 cargo bays for +10 max hull - 5000 cr.");

        addText(layout, "Tip: sell/remove equipment first if you want to convert an occupied weapon or shield slot.");
        return true;
    }

    private void addText(LinearLayout layout, String text) {
        TextView tv = new TextView(getActivity());
        tv.setText(text);
        tv.setTextSize(16);
        tv.setPadding(4, 6, 4, 6);
        layout.addView(tv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void addButton(LinearLayout layout, final int option, String text) {
        STButton button = new STButton(getActivity());
        button.setText(text);
        button.setSingleLine(false);
        button.setPadding(6, 6, 6, 6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Main) getActivity()).ShipCustomizationDirect(option);
            }
        });
        layout.addView(button, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}
