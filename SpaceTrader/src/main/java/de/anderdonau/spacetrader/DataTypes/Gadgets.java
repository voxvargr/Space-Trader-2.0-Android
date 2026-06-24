/*
 * Copyright (c) 2014 Benjamin Schieder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */

package de.anderdonau.spacetrader.DataTypes;

public class Gadgets {
	public static Gadget[] mGadgets = {
		new Gadget("5 extra cargo bays", 2500, 4, 32,
			"Adds five cargo bays. Can be installed multiple times if you have gadget slots."),
		new Gadget("Auto-repair system Mk I", 7500, 5, 18,
			"Improves engineer performance and slowly restores damage in flight."),
		new Gadget("Navigating system Mk I", 15000, 6, 18,
			"Improves pilot performance while fleeing, docking, and threading hazards."),
		new Gadget("Targeting system Mk I", 25000, 6, 18,
			"Improves fighter performance and weapon lock speed."),
		new Gadget("Cloaking device", 100000, 7, 4,
			"Makes weak enemies and some patrols miss you if your engineer can keep it tuned."),
		new Gadget("Auto-repair system Mk II", 34000, 6, 9,
			"Stronger repair routines. Replaces Mk I without needing another slot."),
		new Gadget("Auto-repair system Mk III", 90000, 8, 3,
			"Military-grade damage control. Replaces Mk I/Mk II."),
		new Gadget("Navigating system Mk II", 42000, 6, 9,
			"Better flight computers. Replaces Mk I."),
		new Gadget("Navigating system Mk III", 110000, 8, 3,
			"Predictive route AI and microjump correction. Replaces Mk I/Mk II."),
		new Gadget("Targeting system Mk II", 56000, 7, 9,
			"Smarter target prediction. Replaces Mk I."),
		new Gadget("Targeting system Mk III", 140000, 8, 3,
			"Fleet-grade fire control. Replaces Mk I/Mk II."),
		new Gadget("Expanded cargo pods", 18000, 5, 14,
			"Adds ten cargo bays but occupies a gadget slot."),
		new Gadget("Smuggler hold", 45000, 6, 8,
			"Hides a small amount of contraband and helps during customs inspections."),
		new Gadget("Deep sensor analyzer", 52000, 6, 8,
			"Improves survey reads, anomaly discovery, and travel-event rewards."),
		new Gadget("Shield harmonizer", 65000, 7, 7,
			"Improves shield recharge behavior and makes stacked shields more stable."),
		new Gadget("Trade uplink", 72000, 7, 7,
			"Improves trader skill for prices and helps find off-market deals."),
		new Gadget("Drone control bay", 85000, 7, 5,
			"Supports mining, salvage, boarding, and derelict work."),
		// The gadgets below can't be bought normally.
		new Gadget("Fuel compactor", 30000, 8, 0,
			"A unique device that compresses fuel reserves into a larger effective tank.")
	};

	public static class Gadget {
		public String name;
		public int    price;
		public int    techLevel;
		public int    chance; // Chance that this is fitted in a slot
		public String description;

		public Gadget(String name, int price, int techLevel, int chance) {
			this(name, price, techLevel, chance, "");
		}

		public Gadget(String name, int price, int techLevel, int chance, String description) {
			this.name = name;
			this.price = price;
			this.techLevel = techLevel;
			this.chance = chance;
			this.description = description;
		}

	}
}
