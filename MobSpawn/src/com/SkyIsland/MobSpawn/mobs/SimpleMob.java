package com.SkyIsland.MobSpawn.mobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

/**
 * This class is a wrapper class for a mob and its %chance of spawning
 * @author Matthew
 *
 */
public class SimpleMob implements CustomMob{

	private EntityType type = null;
	private String name = null;
	private boolean isBoss = false;
	private int health = 10;
	private ArmorSet armor = null;
	
	public SimpleMob(EntityType entity) {
		this.type = entity;
	}
	
	public SimpleMob(EntityType entity, String name) {
		this(entity);
		this.name = name;
	}
	
	public SimpleMob(EntityType entity, String name, boolean isBoss) {
		this(entity, name);
		this.isBoss = isBoss;
	}
	
	public SimpleMob(EntityType entity, String name, boolean isBoss, int health) {
		this(entity, name, isBoss);
		this.health = health;
	}
	
	public SimpleMob(EntityType entity, String name, boolean isBoss, int health, ArmorSet armor) {
		this(entity, name, isBoss, health);
		this.armor = armor;
	}

	public EntityType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}

	public boolean isBoss() {
		return isBoss;
	}

	public int getHealth() {
		return health;
	}
	
	public ArmorSet getArmor() {
		return armor;
	}
	
	public LivingEntity spawnMob(Location location){
		LivingEntity entity =  (LivingEntity) location.getWorld().spawnEntity(location, getType());
		
		//set health
		entity.setHealth(health);
		
		//set name
		if (name != null) entity.setCustomName(name);

		//boss stuff
		entity.setRemoveWhenFarAway(!isBoss);
		
		//armor
		if (armor != null){
			if (armor.getHelmet() != null) entity.getEquipment().setHelmet(armor.getHelmet());
			if (armor.getChestplate() != null) entity.getEquipment().setChestplate(armor.getChestplate());
			if (armor.getLeggings() != null) entity.getEquipment().setLeggings(armor.getLeggings());
			if (armor.getBoots() != null) entity.getEquipment().setBoots(armor.getBoots());
		}
		
		return entity;
	}
	
}
