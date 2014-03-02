package com.SkyIsland.MobSpawn;

import org.bukkit.Location;
import org.bukkit.entity.*;

public class SimpleMobType {
	/**
	 * This is the default constructor: e.g a 'default' mob
	 * @param Type The Type of mob to be spawned
	 * @param loc The location of the mob
	 * @return The generated entity
	 */
	public static Entity CustomEntity (EntityType Type, Location loc) {
		return loc.getWorld().spawnEntity(loc, Type);
	}
	/**
	 * This is a constructor for defining the entities general characteristics
	 * @param Type The type of mob to be spawned
	 * @param loc The location of the mob
	 * @param ticksLived The amount of ticks the mob has 'lived' (higher levels mean higher difficulty)
	 * @return The generated entity
	 */
	public static Entity CustomEntity (EntityType Type, Location loc, int ticksLived) {
		if (ticksLived < 0) {
			System.err.println("ERROR: No negative ticksLived parameter allowed!");
			System.err.println("Not applying ticksLived parameter");
			return loc.getWorld().spawnEntity(loc, Type);
		}
		Entity newEntity = loc.getWorld().spawnEntity(loc, Type);
		newEntity.setTicksLived(ticksLived);
		return newEntity;
	}
}
