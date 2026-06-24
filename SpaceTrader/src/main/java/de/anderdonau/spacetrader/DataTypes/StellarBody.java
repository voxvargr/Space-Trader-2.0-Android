/*
 * Space Trader 2 expansion data.
 * Copyright (c) 2026 Space Trader 2 contributors
 * Licensed under the GNU General Public License, version 2 or later.
 */
package de.anderdonau.spacetrader.DataTypes;

import java.io.Serializable;

public class StellarBody implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int ROCKY_PLANET = 0;
	public static final int OCEAN_WORLD = 1;
	public static final int ICE_WORLD = 2;
	public static final int DESERT_WORLD = 3;
	public static final int GAS_GIANT = 4;
	public static final int ASTEROID_BELT = 5;
	public static final int ORBITAL_STATION = 6;
	public static final int DERELICT = 7;
	public static final int ANCIENT_SITE = 8;
	public static final int PIRATE_BASE = 9;
	public static final int TRADE_MOON = 10;

	public int type;
	public int orbit;
	public int species;
	public int controllingGuild;
	public int danger;
	public int resourceRichness;
	public int marketStrength;
	public int questLevel;
	public int anomalyLevel;
	public boolean discovered;
	public boolean surveyed;
	public boolean depleted;
	public boolean hasMarket;
	public boolean hasBase;
	public boolean hasRuins;
	public boolean hasPirates;
	public boolean hasDerelict;

	public StellarBody() {
	}

	public StellarBody(int type, int orbit, int species, int controllingGuild, int danger,
		int resourceRichness, int marketStrength, int questLevel, int anomalyLevel) {
		this.type = type;
		this.orbit = orbit;
		this.species = species;
		this.controllingGuild = controllingGuild;
		this.danger = danger;
		this.resourceRichness = resourceRichness;
		this.marketStrength = marketStrength;
		this.questLevel = questLevel;
		this.anomalyLevel = anomalyLevel;
		this.discovered = false;
		this.surveyed = false;
		this.depleted = false;
		this.hasMarket = marketStrength > 0 || type == ORBITAL_STATION || type == TRADE_MOON;
		this.hasBase = type == ORBITAL_STATION || type == TRADE_MOON || type == PIRATE_BASE;
		this.hasRuins = type == ANCIENT_SITE || anomalyLevel >= 5;
		this.hasPirates = type == PIRATE_BASE || controllingGuild == 3 || danger >= 7;
		this.hasDerelict = type == DERELICT;
	}

	public String typeName() {
		switch (type) {
			case ROCKY_PLANET:
				return "Rocky planet";
			case OCEAN_WORLD:
				return "Ocean world";
			case ICE_WORLD:
				return "Ice world";
			case DESERT_WORLD:
				return "Desert world";
			case GAS_GIANT:
				return "Gas giant";
			case ASTEROID_BELT:
				return "Asteroid belt";
			case ORBITAL_STATION:
				return "Orbital station";
			case DERELICT:
				return "Derelict vessel";
			case ANCIENT_SITE:
				return "Ancient site";
			case PIRATE_BASE:
				return "Pirate base";
			case TRADE_MOON:
				return "Trade moon";
			default:
				return "Unknown body";
		}
	}

	public String shortStatus() {
		String text = typeName();
		if (!discovered) {
			return "Unknown contact";
		}
		if (hasMarket) {
			text += ", market";
		}
		if (hasRuins) {
			text += ", ruins";
		}
		if (hasDerelict) {
			text += ", salvage";
		}
		if (hasPirates) {
			text += ", hostile";
		}
		if (depleted) {
			text += ", depleted";
		}
		return text;
	}
}
