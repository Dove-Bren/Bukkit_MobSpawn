package com.SkyIsland.MobSpawn.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import com.SkyIsland.MobSpawn.InventoryCreator;
import com.SkyIsland.MobSpawn.PredefinedPotionEffect;
public enum PredefinedMob implements CustomMob{
	skeletonOnHorse(),
	witherSkeletonOnHorse(),
	zombieOnHorse(),
	skeletonHorseSquad(),
	witherSkeletonSquad(),
	zombieHorseSquad(),
	slimePyramid(),
	skeletonOnHorseOnBat();
	
	
	/**
	 * This constructor creates a Skeleton Horse with a Skeleton Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param SkeletonWeapon The item that will be in the Skeleton Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	@SuppressWarnings("unused")
	private LivingEntity skeletonOnHorse (Location location) {
		ItemStack SkeletonWeapon = InventoryCreator.RangedWeapon(1);
		Horse HorseVehicle;
		Skeleton SkeletonRider;
		HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		SkeletonRider = (Skeleton) location.getWorld().spawnEntity(HorseVehicle.getLocation(), EntityType.SKELETON);
		SkeletonRider.getEquipment().setItemInHand(SkeletonWeapon);
		HorseVehicle.setPassenger(SkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		return HorseVehicle;
	}
	
	/**
	 * This constructor creates a Skeleton Horse with a Wither Skeleton Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param SkeletonWeapon The item that will be in the Wither Skeleton Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	@SuppressWarnings("unused")
	private LivingEntity witherSkeletonOnHorse (Location location) {
		ItemStack SkeletonWeapon = InventoryCreator.RangedWeapon(3);
		Horse HorseVehicle;
		Skeleton WitherSkeletonRider;
		HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		WitherSkeletonRider = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
		WitherSkeletonRider.setSkeletonType(Skeleton.SkeletonType.WITHER);
		WitherSkeletonRider.getEquipment().setItemInHand(SkeletonWeapon);
		HorseVehicle.setPassenger(WitherSkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		return HorseVehicle;
	}
	
	/**
	 * This constructor creates an Undead Horse with a Zombie Rider armed with an item
	 * @param loc The location for which the mobs will spawn at
	 * @param ZombieWeapon The item that will be in the Zombie Rider's hand
	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
	 */
	@SuppressWarnings("unused")
	private LivingEntity zombieOnHorse (Location location) {
		ItemStack ZombieWeapon = new ItemStack(Material.IRON_SWORD);
		Horse HorseVehicle;
		Zombie ZombieRider;
		HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		ZombieRider = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
		ZombieRider.getEquipment().setItemInHand(ZombieWeapon);
		HorseVehicle.setPassenger(ZombieRider);
		HorseVehicle.setVariant(Horse.Variant.UNDEAD_HORSE);
		HorseVehicle.setRemoveWhenFarAway(true);
		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		return HorseVehicle;
	}
	
	/**
	 * This constructor creates a squad of Skeleton Horses with Riders armed with items
	 * In addition, a superior leader will also be spawned
	 * @param loc The location for the squad
	 */
	@SuppressWarnings("unused")
	private LivingEntity skeletonHorseSquad(Location location) {
		Horse HorseVehicle;
		Skeleton SkeletonRider;
		int i;
		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
			HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
			HorseVehicle.setTamed(Boolean.TRUE);
			SkeletonRider = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
			SkeletonRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(1));
			HorseVehicle.setPassenger(SkeletonRider);
			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
			HorseVehicle.setRemoveWhenFarAway(true);
		}
		HorseVehicle = (Horse) location.getWorld().spawnEntity(location,  EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		SkeletonRider = (Skeleton)	location.getWorld().spawnEntity(location,EntityType.SKELETON);
		SkeletonRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(5));
		HorseVehicle.setPassenger(SkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
		HorseVehicle.setRemoveWhenFarAway(true);
		
		return HorseVehicle;
	}
	
	/**
	 * This constructor creates a squad of Skeleton Horses with Wither Riders armed with items
	 * In addition, a superior leader will also be spawned
	 * @param loc The location for the squad
	 */
	@SuppressWarnings("unused")
	private LivingEntity witherSkeletonSquad(Location location) {
		Horse HorseVehicle;
		Skeleton WitherSkeletonRider;
		int i;
		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
			HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
			HorseVehicle.setTamed(Boolean.TRUE);
			WitherSkeletonRider = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
			WitherSkeletonRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(1));
			WitherSkeletonRider.setSkeletonType(Skeleton.SkeletonType.WITHER);
			HorseVehicle.setPassenger(WitherSkeletonRider);
			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
			HorseVehicle.setRemoveWhenFarAway(true);
		}
		HorseVehicle = (Horse) location.getWorld().spawnEntity(location,  EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		WitherSkeletonRider = (Skeleton)	location.getWorld().spawnEntity(location,EntityType.SKELETON);
		WitherSkeletonRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(5));
		HorseVehicle.setPassenger(WitherSkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
		HorseVehicle.setRemoveWhenFarAway(true);
		
		return HorseVehicle;
	}
	
	/**
	 * This constructor creates a squad of Skeleton Horses with Wither Riders armed with items
	 * In addition, a superior leader will also be spawned
	 * @param loc The location for the squad
	 */
	@SuppressWarnings("unused")
	private LivingEntity zombieHorseSquad(Location location) {
		Horse HorseVehicle;
		Zombie ZombieRider;
		int i;
		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
			HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
			HorseVehicle.setTamed(Boolean.TRUE);
			ZombieRider = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
			ZombieRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(1));
			HorseVehicle.setPassenger(ZombieRider);
			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
			HorseVehicle.setRemoveWhenFarAway(true);
		}
		HorseVehicle = (Horse) location.getWorld().spawnEntity(location,  EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		ZombieRider = (Zombie)	location.getWorld().spawnEntity(location,EntityType.ZOMBIE);
		ZombieRider.getEquipment().setItemInHand(InventoryCreator.RangedWeapon(5));
		HorseVehicle.setPassenger(ZombieRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
		HorseVehicle.setRemoveWhenFarAway(true);
		
		return HorseVehicle;
	}
	
	/**
	 * Spawns a slime pyramid, with height 5. 
	 * @param loc the location of the pyramid
	 */
	@SuppressWarnings("unused")
	private LivingEntity slimePyramid(Location location) {
		Slime currentSlime, oldSlime, firstSlime;
		int i;
		
		firstSlime = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
		currentSlime = firstSlime;
		currentSlime.setSize(5);
		
		for (i = 4; i > 0; i--) {
			oldSlime = currentSlime;
			currentSlime = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
			currentSlime.setSize(i);
			oldSlime.setPassenger(currentSlime);
		}
		
		return firstSlime;
	}
	
	/**
	 * Spawns a skeleton on a horse on a bat
	 * @param loc the location of the monstah
	 */
	@SuppressWarnings("unused")
	private LivingEntity skeletonOnHorseOnBat(Location location) {
		Horse HorseVehicle;
		Skeleton SkeletonRider;
		Bat bat;
		HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
		HorseVehicle.setTamed(Boolean.TRUE);
		SkeletonRider = (Skeleton) location.getWorld().spawnEntity(HorseVehicle.getLocation(), EntityType.SKELETON);
		SkeletonRider.getEquipment().setItemInHand(new ItemStack(Material.BOW));
		HorseVehicle.setPassenger(SkeletonRider);
		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
		HorseVehicle.setRemoveWhenFarAway(true);
		bat = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
		bat.setPassenger(HorseVehicle);
		bat.addPotionEffects(PredefinedPotionEffect.invisForever.getPotionEffects());
		
		return bat;
	}

	@Override
	public LivingEntity spawnMob(Location location) {
		try {
			return (LivingEntity) getClass().getMethod(name(), Location.class).invoke(this, location);
		} catch (Exception  e) {
			e.printStackTrace();
		}
		return null;
	}
}
