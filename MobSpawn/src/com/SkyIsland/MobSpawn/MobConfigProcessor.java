package com.SkyIsland.MobSpawn;

import java.util.StringTokenizer;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public final class MobConfigProcessor {
	/**
	 * This method determines how a new entity(ies) will be generated
	 * @param Name The name of the entity
	 * @param Command Various attributes associated with the specified mob type
	 * @param Loc The location to generate the mob
	 */
	public static void SpawnMob (String Name, String Command, Location Loc) {
		StringTokenizer stringToken = new StringTokenizer(Command, " ");
		String tokenString = stringToken.nextToken();
		//Check for type of mob: Simple, Predefined, Complex
		if (tokenString.trim().compareTo("simple") == 0) {
			SimpleMobType.CustomEntity(EntityType.valueOf(Name), Loc);
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
		if (tokenString.trim().compareTo("complex") == 0) {
			//Determine how complex types will be generated
			tokenString = stringToken.nextToken();
			tokenString.toLowerCase();
			
		}
		}
	}
}

