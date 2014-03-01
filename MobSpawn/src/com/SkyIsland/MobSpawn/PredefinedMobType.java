package com.SkyIsland.MobSpawn;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public enum PredefinedMobType {
	//follow pattern below to add more predefined mobs sir willy
	skeletonOnHorse (1),
	witherSkeletonOnHorse (2),
	zombieOnHorse (3);
	
	/*
	 * Instead of making a fourth spawn method (one for single spawn, one for double spawn, complex, and now predefined)
	 * you can make the predefined methods in this enum class.
	 * That way when you recieve the type of mob to spawn (single, complex, etc) and it says predefined, you can
	 * just call the corresponding method in here!
	 * 
	 */
	
	int type;
	
	PredefinedMobType(int type) {
		this.type = type;
	}
	
	protected void spawnSkeletonOnHorse(Location loc) {
		Entity Horse;
		Entity SkeletonRider;
		
		Horse = loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		SkeletonRider = loc.getWorld().spawnEntity(Horse.getLocation(), EntityType.SKELETON);
		//....etc. you can take it from here
	}
}
