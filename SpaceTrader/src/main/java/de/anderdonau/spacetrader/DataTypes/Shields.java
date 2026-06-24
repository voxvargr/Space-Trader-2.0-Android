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

public class Shields {
	public static Shield[] mShields = {
		new Shield("Energy shield", GameState.ESHIELDPOWER, 5000, 5, 42,
			"Basic bubble shield. Light, cheap, and easy to repair."),
		new Shield("Reflective shield", GameState.RSHIELDPOWER, 20000, 6, 24,
			"Deflects beam fire better than standard energy shields."),
		new Shield("Ablative armor field", GameState.ABLATIVEARMORPOWER, 28000, 5, 18,
			"Hybrid armor/shield plating. Modest output but excellent at surviving hazards."),
		new Shield("Deflector matrix", GameState.DEFLECTORMATRIXPOWER, 54000, 6, 10,
			"Multi-angle shield grid with strong protection against pirates and debris."),
		new Shield("Phase shield", GameState.PHASESHIELDPOWER, 95000, 7, 5,
			"Advanced shield that bleeds some damage into subspace."),
		new Shield("Aegis barrier", GameState.AEGISBARRIERPOWER, 180000, 8, 1,
			"Rare heavy barrier normally seen on military escorts."),
		// The shields below can't be bought normally.
		new Shield("Lightning shield", GameState.LSHIELDPOWER, 45000, 8, 0,
			"A unique alien shield installation.")
	};

	public static class Shield {
		public String name;
		public int    power;
		public int    price;
		public int    techLevel;
		public int    chance; // Chance that this is fitted in a slot
		public String description;

		public Shield(String name, int power, int price, int techLevel, int chance) {
			this(name, power, price, techLevel, chance, "");
		}

		public Shield(String name, int power, int price, int techLevel, int chance, String description) {
			this.name = name;
			this.power = power;
			this.price = price;
			this.techLevel = techLevel;
			this.chance = chance;
			this.description = description;
		}
	}
}
