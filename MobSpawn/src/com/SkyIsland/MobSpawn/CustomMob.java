package com.SkyIsland.MobSpawn;

import org.bukkit.entity.Entity;

/**
 * This class is a wrapper class for a mob and its %chance of spawning
 * @author Matthew
 *
 */
public class CustomMob {

	private Entity entity;
	private int spawnChance;
	
	public CustomMob(Entity entity, int spawnChance){
		this.entity = entity;
		this.spawnChance = spawnChance;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public int getSpawnChance() {
		return spawnChance;
	}
	
	public void setSpawnChance(int spawnChance) {
		this.spawnChance = spawnChance;
	}
	
}
