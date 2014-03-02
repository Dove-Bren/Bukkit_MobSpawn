package com.SkyIsland.MobSpawn;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public final class MobConfigProcessor {
	
	final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * This method determines how a new entity(ies) will be generated
	 * @param Name The name of the entity
	 * @param Command Various attributes associated with the specified mob type
	 * @param Loc The location to generate the mob
	 */
	public static void SpawnMob (String Name, String Command, Location Loc, MobSpawn plugin) {
		StringTokenizer stringToken = new StringTokenizer(Command, " ");
		String tokenString = stringToken.nextToken();
		//Check for type of mob: Simple, Predefined, Complex
		if (tokenString.trim().compareTo("simple") == 0) {
			SimpleMobType.CustomEntity(EntityType.valueOf(Name.toUpperCase()), Loc);
		}
		if (tokenString.trim().compareTo("predefined") == 0) {
			//These predefined types are only here due to specific nature of horses
			switch(PredefinedMobType.valueOf(Name)) {
			case skeletonOnHorse:
				PredefinedMobType.spawnSkeletonOnHorse(Loc);
				break;
			case witherSkeletonOnHorse:
				PredefinedMobType.witherSkeletonOnHorse(Loc);
				break;
			case zombieOnHorse:
				PredefinedMobType.zombieOnHorse(Loc);
				break;
			default:
				System.err.println("Incorrect Predefined Type!");
				break;
			}
		}
		if (tokenString.trim().compareTo("double") == 0) {
			//Determine how complex types will be generated
			tokenString = stringToken.nextToken();
			tokenString.trim();
			tokenString.toUpperCase();
			String Vehicle = tokenString;
			if (!stringToken.hasMoreTokens()) {
				logger.info("Incorrect yml feed!");
				return;
			}
			tokenString = stringToken.nextToken();
			tokenString.trim();
			tokenString.toUpperCase();
			String Passenger = tokenString;
			ComplexMobType.CustomEntity(EntityType.valueOf(Vehicle), EntityType.valueOf(Passenger), Loc);
		}
	}
}

