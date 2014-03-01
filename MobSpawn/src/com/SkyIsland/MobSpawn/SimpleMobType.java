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
	public Entity CustomEntity (EntityType Type, Location loc) {
		return loc.getWorld().spawnEntity(loc, Type);
	}
	/**
	 * This is a constructor for defining the entities general characteristics
	 * @param Type The type of mob to be spawned
	 * @param loc The location of the mob
	 * @param fireResistance The amount of ticks a mob will remain on fire (lower levels mean higher difficulty)
	 * @param ticksLived The amount of ticks the mob has lived (higher levels mean higher difficulty)
	 * @return The generated entity
	 */
	public Entity CustomEntity (EntityType Type, Location loc, int fireResistance, int ticksLived) {
		if (fireResistance < 0 || ticksLived < 0) {
			System.err.println("ERROR: No negative fireResistance and ticksLived parameters allowed!");
			System.err.println("Not applying fireResistance and ticksLived parameters");
			return loc.getWorld().spawnEntity(loc, Type);
		}
		Entity newEntity = loc.getWorld().spawnEntity(loc, Type);
		newEntity.setFireTicks(fireResistance);
		newEntity.setTicksLived(ticksLived);
		return newEntity;
	}
}
