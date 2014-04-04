package com.SkyIsland.MobSpawn.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import com.SkyIsland.MobSpawn.additions.ArmorSet;
import com.SkyIsland.MobSpawn.additions.CustomPotionEffect;

/**
 * This class is a wrapper class for a mob and its %chance of spawning
 * @author Matthew
 *
 */
public class SimpleMob implements CustomMob{

	//changed these fields from private to protected, so subclasses can keep and use the same values
	protected EntityType type = null;
	protected String name = null;
	protected boolean isBoss = false;
	protected int health = 10;
	protected ArmorSet armor = null;
	protected CustomPotionEffect potionEffect = null;
	
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
	
	public SimpleMob(EntityType entity, String name, boolean isBoss, int health, ArmorSet armor, CustomPotionEffect potionEffect) {
		this(entity, name, isBoss, health, armor);
		this.potionEffect = potionEffect;
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
	
	public CustomPotionEffect getPotionEffect() {
		return potionEffect;
	}
	
	public LivingEntity spawnMob(Location location){
		LivingEntity entity =  (LivingEntity) location.getWorld().spawnEntity(location, getType());
		
		//set health
		entity.setMaxHealth(health);
		
		//set name
		if (name != null && !name.equalsIgnoreCase("none")) entity.setCustomName(name);

		//boss stuff
		entity.setRemoveWhenFarAway(!isBoss);
		
		//armor
		if (armor != null){
			if (armor.getHelmet() != null) entity.getEquipment().setHelmet(armor.getHelmet());
			if (armor.getChestplate() != null) entity.getEquipment().setChestplate(armor.getChestplate());
			if (armor.getLeggings() != null) entity.getEquipment().setLeggings(armor.getLeggings());
			if (armor.getBoots() != null) entity.getEquipment().setBoots(armor.getBoots());
			if (armor.getSword() != null) entity.getEquipment().setItemInHand(armor.getSword());
		}
		
		//potion effects
		if (potionEffect != null && potionEffect.getPotionEffects() != null){
			for (PotionEffect effect: potionEffect.getPotionEffects()){
				entity.addPotionEffect(effect);
			}
		}
		
		return entity;
	}
	
	/**
	 * Returns whether or not this mob can spawn at the passed location
	 * @param location Where we want to check if the mob can spawn. This should be the location of the mob's feet.
	 */
	public boolean canSpawn(Location location) {
		
		switch (this.type) {
		case GIANT:
			//giants should only spawn outside; no way are they fitting in a cave.
			//etc.
			//if location.getBlock().
			break;
		case GHAST:
			//ghasts are larger and need their own check. said to be 4x4x4
			return checkArea(location, 4, 4, 4);
		case SLIME:
		case MAGMA_CUBE:
			//slimes have varying sizes...
			//we'll assume a size of 4 (Large slime) which has width 2.4
			return checkArea(location, 3, 3, 3);
		case HORSE:
			//we'll use a 2x2x2 cube to be sure
			return checkArea(location, 2, 2, 2);
		default:
			//rest of regular-sized mobs that we don't have to special checks for. Check a 1x2x1 area (x,y,z)
			return checkArea(location, 1, 2, 1);
		}
		
		return false;
	}
	
	/**
	 * Checks a cube of width X, height Y, and length Z for all air blocks. Returns as soon as it finds something else
	 * @param w the width of the box
	 * @param h the height of the box
	 * @param l the length of the box
	 * @return if all those blocks are air blocks
	 * 
	 */
	private boolean checkArea(Location location, int w, int h, int l) {
		int i, j, k;
		for (i = 0; i < w; i++) {
			for (j = 0; j < h; j++) {
				for (k = 0; k < l; k++) {
					//yucky. tripple nested brah. sick.
					if (location.add(i, j, k).getBlock().getType() != Material.AIR) {
						return false;
					}
				}
			}
		}
		
		//after all the for looping. It never returned false, so it must be okay.
		return true;
	}
	
}
