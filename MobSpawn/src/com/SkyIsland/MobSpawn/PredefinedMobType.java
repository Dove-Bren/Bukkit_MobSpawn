package com.SkyIsland.MobSpawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
public enum PredefinedMobType {
	//follow pattern below to add more predefined mobs sir william
	skeletonOnHorse (1),
	witherSkeletonOnHorse (2),
	zombieOnHorse (3),
	skeletonHorseSquad (4),
	witherSkeletonSquad (5),
	zombieOnHorseSquad (6);
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
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates a Skeleton Horse with a Skeleton Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param SkeletonWeapon The item that will be in the Skeleton Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected static void spawnSkeletonOnHorse (Location loc, ItemStack SkeletonWeapon) {
		Horse HorseVehicle;
		Skeleton SkeletonRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		SkeletonRider = (Skeleton) loc.getWorld().spawnEntity(HorseVehicle.getLocation(), EntityType.SKELETON);
		SkeletonRider.getEquipment().setItemInHand(SkeletonWeapon);
		HorseVehicle.setPassenger(SkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
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
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates a Skeleton Horse with a Wither Skeleton Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param SkeletonWeapon The item that will be in the Wither Skeleton Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected static void witherSkeletonOnHorse (Location loc, ItemStack SkeletonWeapon) {
		Horse HorseVehicle;
		Skeleton WitherSkeletonRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		WitherSkeletonRider = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
		WitherSkeletonRider.setSkeletonType(Skeleton.SkeletonType.WITHER);
		WitherSkeletonRider.getEquipment().setItemInHand(SkeletonWeapon);
		HorseVehicle.setPassenger(WitherSkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
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
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates an Undead Horse with a Zombie Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param ZombieWeapon The item that will be in the Zombie Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	protected static void zombieOnHorse (Location loc, ItemStack ZombieWeapon) {
		Horse HorseVehicle;
		Zombie ZombieRider;
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		ZombieRider = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		ZombieRider.getEquipment().setItemInHand(ZombieWeapon);
		HorseVehicle.setPassenger(ZombieRider);
		HorseVehicle.setVariant(Horse.Variant.UNDEAD_HORSE);
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	}
	
	/**
	 * This constructor creates a squad of Skeleton Horses with Riders armed with items
	 * In addition, a superior leader will also be spawned
	 * @param loc The location for the squad
	 */
	protected static void skeletonHorseSquad(Location loc) {
		Horse HorseVehicle;
		Skeleton SkeletonRider;
		int i;
		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
			HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
			HorseVehicle.setTamed(Boolean.TRUE);
			SkeletonRider = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			SkeletonRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(1));
			HorseVehicle.setPassenger(SkeletonRider);
			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
			HorseVehicle.setRemoveWhenFarAway(true);
		}
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc,  EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		SkeletonRider = (Skeleton)	loc.getWorld().spawnEntity(loc,EntityType.SKELETON);
		SkeletonRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(5));
		HorseVehicle.setPassenger(SkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
		HorseVehicle.setRemoveWhenFarAway(true);
	}
	
	/**
	 * This constructor creates a squad of Skeleton Horses with Wither Riders armed with items
	 * In addition, a superior leader will also be spawned
	 * @param loc The location for the squad
	 */
	protected static void witherHorseSquad(Location loc) {
		Horse HorseVehicle;
		Skeleton WitherSkeletonRider;
		int i;
		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
			HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
			HorseVehicle.setTamed(Boolean.TRUE);
			WitherSkeletonRider = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
			WitherSkeletonRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(1));
			WitherSkeletonRider.setSkeletonType(Skeleton.SkeletonType.WITHER);
			HorseVehicle.setPassenger(WitherSkeletonRider);
			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
			HorseVehicle.setRemoveWhenFarAway(true);
		}
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc,  EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		WitherSkeletonRider = (Skeleton)	loc.getWorld().spawnEntity(loc,EntityType.SKELETON);
		WitherSkeletonRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(5));
		HorseVehicle.setPassenger(WitherSkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
		HorseVehicle.setRemoveWhenFarAway(true);
	}
	
	/**
	 * This constructor creates a squad of Skeleton Horses with Wither Riders armed with items
	 * In addition, a superior leader will also be spawned
	 * @param loc The location for the squad
	 */
	protected static void ZombieHorseSquad(Location loc) {
		Horse HorseVehicle;
		Zombie ZombieRider;
		int i;
		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
			HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
			HorseVehicle.setTamed(Boolean.TRUE);
			ZombieRider = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			ZombieRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(1));
			HorseVehicle.setPassenger(ZombieRider);
			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
			HorseVehicle.setRemoveWhenFarAway(true);
		}
		HorseVehicle = (Horse) loc.getWorld().spawnEntity(loc,  EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		ZombieRider = (Zombie)	loc.getWorld().spawnEntity(loc,EntityType.ZOMBIE);
		ZombieRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(5));
		HorseVehicle.setPassenger(ZombieRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
		HorseVehicle.setRemoveWhenFarAway(true);
	}
}
