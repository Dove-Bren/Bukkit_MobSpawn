package com.SkyIsland.MobSpawn;

import java.util.StringTokenizer;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class MobConfigProcessor {
	public void SpawnMob (String Name, String Command, Location Loc) {
		StringTokenizer stringToken = new StringTokenizer(Command, " ");
		String tokenString = stringToken.nextToken();
		if (tokenString == "simple") {
			SimpleMobType.CustomEntity(EntityType.valueOf(Name), Loc);
		}
		if (tokenString == "predefined") {
			switch(Name) {
			case "skeletonOnHorse":
				PredefinedMobType.spawnSkeletonOnHorse(Loc);
				break;
			case "witherSkeletonOnHorse":
				PredefinedMobType.witherSkeletonOnHorse(Loc);
				break;
			case "zombieOnHorse":
				PredefinedMobType.zombieOnHorse(Loc);
				break;
			default:
				System.err.println("Incorrect Predefined Type!");
				break;
			}
		if (tokenString == "complex") {
			//Determine how complex types will be generated
		}
		}
	}
}
