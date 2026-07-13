/*
 * Space Trader 2 local-system travel interlude.
 * Copyright (c) 2026 Space Trader 2 contributors
 * Licensed under the GNU General Public License, version 2 or later.
 */
package de.anderdonau.spacetrader;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;
import de.anderdonau.spacetrader.DataTypes.StellarBody;

public class FragmentLocalTravel extends MyFragment {
	private LinearLayout container;
	private TextView logView;
	private Handler handler;
	private boolean started = false;
	private StringBuilder log = new StringBuilder();

	private final Runnable tickRunnable = new Runnable() {
		@Override
		public void run() {
			if (!isAdded() || gameState == null) {
				return;
			}
			if (!gameState.hasPendingLocalAction()) {
				main.changeFragment(Main.FRAGMENTS.LOCAL_SYSTEM);
				return;
			}
			appendLog(main.localTravelTickLine());
			gameState.PendingLocalActionTicks -= 1;
			if (gameState.PendingLocalActionTicks <= 0) {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (isAdded()) {
							main.finishLocalActionTravel();
						}
					}
				}, 650);
			} else {
				handler.postDelayed(tickRunnable, 850);
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		handler = new Handler();
		View root = inflater.inflate(R.layout.fragment_local_travel, parent, false);
		container = (LinearLayout) root.findViewById(R.id.localTravelContainer);
		update();
		startTravel();
		return root;
	}

	@Override
	public boolean update() {
		if (container == null || gameState == null) {
			return false;
		}
		container.removeAllViews();
		if (!gameState.hasPendingLocalAction()) {
			container.addView(label("Local Travel", true));
			container.addView(label("No local route is active.", false));
			return true;
		}
		SolarSystem system = gameState.SolarSystem[gameState.PendingLocalActionSystem];
		StellarBody body = system.bodies[gameState.PendingLocalActionBody];
		String action = gameState.PendingLocalActionType == GameState.LOCAL_ACTION_EXPLORE ? "Exploring" : "Mining / Salvage";
		container.addView(label(action + ": " + main.GetSystemName(system) + "-" + body.orbit, true));
		container.addView(label(body.shortStatus(), false));
		container.addView(label("Local flight ticks remaining: " + Math.max(1, gameState.PendingLocalActionTicks), false));
		logView = label(log.length() == 0 ? "Leaving the main traffic lane." : log.toString(), false);
		container.addView(logView);
		return true;
	}

	private void startTravel() {
		if (started || handler == null) {
			return;
		}
		started = true;
		handler.postDelayed(tickRunnable, 350);
	}

	private void appendLog(String line) {
		if (log.length() > 0) {
			log.append("\n");
		}
		log.append(line);
		if (logView != null) {
			logView.setText(log.toString());
		}
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

	@Override
	public void onDestroyView() {
		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
		}
		super.onDestroyView();
	}
}
