package com.SkyIsland.MobSpawn;

import com.SkyIsland.MobSpawn.mobs.CustomMob;

/**
 * Just a humble helper class that stores a mapping between mobtype and its spawn rate.
 * <p>Used in lieu of entry because entry is a nested, protected class. This is our equivalent that we can manipulate
 * exactly how we want.</p>
 * @author Skyler
 *
 */
public class MobToSpawn {
	public CustomMob mob;
	public int rate;
	
	/**
	 * Does exactly what you would think: creates the object with a map between CustomMob mob and the supplied rate.
	 * @param mob The CustomMob we are creating a mapping for
	 * @param rate The spawn rate of the linked CustomMob
	 * @version 1.0
	 */
	public MobToSpawn(CustomMob mob, int rate) {
		this.mob = mob;
		this.rate = rate;
	}
	
	/**
	 * Creates a mapping between the supplied CustomMob and its rate, which defaults to 100%.
	 * @param mob The CustomMob the mapping is being created for.
	 */
	public MobToSpawn(CustomMob mob) {
		this(mob, 100);
	}
}
