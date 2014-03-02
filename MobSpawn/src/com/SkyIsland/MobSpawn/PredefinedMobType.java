package com.SkyIsland.MobSpawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
public enum PredefinedMobType {
	//follow pattern below to add more predefined mobs sir william
	skeletonOnHorse (1),
	witherSkeletonOnHorse (2),
	zombieOnHorse (3);
	
	/*
	 * Instead of making a fourth spawn method (one for single spawn, one for double spawn, complex, and now predefined)
	 * you can make the predefined methods in this enum class.
	 * That way when you receive the type of mob to spawn (single, complex, etc) and it says predefined, you can
	 * just call the corresponding method in here!
	 * 
	 */
	
	int type;
	
	PredefinedMobType(int type) {
		this.type = type;
	}
	
	/**
	 * This constructor creates a Skeleton Horse with a Skeleton Rider (No item)
	 * @param loc The location for which the mobs will spawn at
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected static void spawnSkeletonOnHorse (Location loc) {
		Horse HorseVehicle;
		Skeleton SkeletonRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		SkeletonRider = (Skeleton) loc.getWorld().spawnEntity(HorseVehicle.getLocation(), EntityType.SKELETON);
		HorseVehicle.setPassenger(SkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates a Skeleton Horse with a Skeleton Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param SkeletonWeapon The item that will be in the Skeleton Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected void spawnSkeletonOnHorse (Location loc, ItemStack SkeletonWeapon) {
		Horse HorseVehicle;
		Skeleton SkeletonRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		SkeletonRider = (Skeleton) loc.getWorld().spawnEntity(HorseVehicle.getLocation(), EntityType.SKELETON);
		SkeletonRider.getEquipment().setItemInHand(SkeletonWeapon);
		HorseVehicle.setPassenger(SkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates a Skeleton Horse with a Wither Skeleton Rider (No item)
	 * @param loc The location for which the mobs will spawn at
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected static void witherSkeletonOnHorse (Location loc) {
		Horse HorseVehicle;
		Skeleton WitherSkeletonRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		WitherSkeletonRider = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
		WitherSkeletonRider.setSkeletonType(Skeleton.SkeletonType.WITHER);
		HorseVehicle.setPassenger(WitherSkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates a Skeleton Horse with a Wither Skeleton Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param SkeletonWeapon The item that will be in the Wither Skeleton Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected void witherSkeletonOnHorse (Location loc, ItemStack SkeletonWeapon) {
		Horse HorseVehicle;
		Skeleton WitherSkeletonRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		WitherSkeletonRider = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
		WitherSkeletonRider.setSkeletonType(Skeleton.SkeletonType.WITHER);
		WitherSkeletonRider.getEquipment().setItemInHand(SkeletonWeapon);
		HorseVehicle.setPassenger(WitherSkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates an Undead Horse with a Zombie Rider (No item)
	 * @param loc The location for which the mobs will spawn at
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected static void zombieOnHorse (Location loc) {
		Horse HorseVehicle;
		Zombie ZombieRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		ZombieRider = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		HorseVehicle.setPassenger(ZombieRider);
		HorseVehicle.setVariant(Horse.Variant.UNDEAD_HORSE);
		HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates an Undead Horse with a Zombie Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param ZombieWeapon The item that will be in the Zombie Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected void zombieOnHorse (Location loc, ItemStack ZombieWeapon) {
		Horse HorseVehicle;
		Zombie ZombieRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		ZombieRider = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		ZombieRider.getEquipment().setItemInHand(ZombieWeapon);
		HorseVehicle.setPassenger(ZombieRider);
		HorseVehicle.setVariant(Horse.Variant.UNDEAD_HORSE);
		HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
}
