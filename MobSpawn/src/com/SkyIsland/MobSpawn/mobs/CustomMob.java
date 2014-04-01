package com.SkyIsland.MobSpawn.mobs;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface CustomMob {

	public LivingEntity spawnMob(Location location);
	
	/**
	 * This function will return whether or not the mob can spawn in the passed location. This function should be
	 * expected to be called by the fathering plugin before any call to the spawnMob method.
	 * <p>Reasons this wouldn't work should include:
	 * <ul>
	 * <li>Not enough space in the desired location</li>
	 * <li>Location not desirable for the type of AI given to mob</li>
	 * <li>Added features -- like skeletal horsemen guards on the surface -- prevent the creation at location</li>
	 * </ul>
	 * It's up to each implementing class to figure out what rules will apply to what mobs.
	 * </p>
	 * <p>
	 * @param location
	 * @return
	 */
	//public boolean canSpawn(Location location);
}
