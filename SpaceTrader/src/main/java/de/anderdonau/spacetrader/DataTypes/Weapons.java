/*
 * Copyright (c) 2014 Benjamin Schieder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */

package de.anderdonau.spacetrader.DataTypes;

import de.anderdonau.spacetrader.GameState;

public class Weapons {
	public static Weapon[] mWeapons = {
		new Weapon("Pulse laser", GameState.PULSELASERPOWER, 2000, 5, 34,
			"Cheap, reliable starter laser. Good for small pirate craft."),
		new Weapon("Beam laser", GameState.BEAMLASERPOWER, 12500, 6, 25,
			"Steady beam weapon with better accuracy and mid-range damage."),
		new Weapon("Military laser", GameState.MILITARYLASERPOWER, 35000, 7, 16,
			"Classic high-output military armament."),
		new Weapon("Ion cannon", GameState.IONCANNONPOWER, 52000, 6, 12,
			"Disrupts shields and ship electronics. Lower raw hull damage than a railgun."),
		new Weapon("Railgun", GameState.RAILGUNPOWER, 64000, 6, 10,
			"Kinetic weapon that punches through damaged shields and light hulls."),
		new Weapon("Plasma lance", GameState.PLASMALANCEPOWER, 90000, 7, 8,
			"Heavy burst weapon. Excellent against armored raiders."),
		new Weapon("EMP blaster", GameState.EMPBLASTERPOWER, 76000, 7, 8,
			"Weakens an enemy's ability to keep firing, but needs another weapon to finish fights."),
		new Weapon("Missile rack", GameState.MISSILERACKPOWER, 115000, 7, 6,
			"High alpha strike weapon. Expensive, brutal, and favored by mercenary ships."),
		new Weapon("Particle beam", GameState.PARTICLEBEAMPOWER, 155000, 8, 4,
			"Advanced beam weapon that performs well against shields and hull alike."),
		new Weapon("Quantum lance", GameState.QUANTUMLANCEPOWER, 240000, 8, 2,
			"Experimental capital-grade weapon. Rare and extremely expensive."),
		// The weapons below cannot be bought through normal equipment shops.
		new Weapon("Morgan's laser", GameState.MORGANLASERPOWER, 50000, 8, 0,
			"A unique weapon with illegal modifications and a frightening burn profile.")
	};

	public static class Weapon {
		public String name;
		public int    power;
		public int    price;
		public int    techLevel;
		public int    chance; // Chance that this is fitted in a slot
		public String description;

		public Weapon(String name, int power, int price, int techLevel, int chance) {
			this(name, power, price, techLevel, chance, "");
		}

		public Weapon(String name, int power, int price, int techLevel, int chance, String description) {
			this.name = name;
			this.power = power;
			this.price = price;
			this.techLevel = techLevel;
			this.chance = chance;
			this.description = description;
		}
	}
}
