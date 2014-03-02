package com.SkyIsland.MobSpawn;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class ComplexMobType {
	/**
	 * This constructor creates a 'vehicle' entity along with its 'passenger'
	 * @param VehicleType The vehicle type (the Bottom)
	 * @param PassengerType The passenger type (the Top)
	 * @param Loc The location of both entities
	 * @return The Vehicle Entity
	 */
	public static Entity CustomEntity (EntityType VehicleType, EntityType PassengerType, Location Loc) {
		Entity VehicleEntity = Loc.getWorld().spawnEntity(Loc, VehicleType);
		Entity PassengerEntity = Loc.getWorld().spawnEntity(Loc, PassengerType);
		VehicleEntity.setPassenger(PassengerEntity);
		return VehicleEntity;
	}
	/**
	 * This constructor creates a 'vehicle' entity along with its 'passenger' as well as a "ticks lived" parameter for difficulty
	 * @param VehicleType The vehicle type (the Bottom)
	 * @param PassengerType The passenger type (the Top)
	 * @param Loc The location of both entities
	 * @param ticksLived The amount of ticks the mob has 'lived' (higher levels mean higher difficulty)
	 * @return The Vehicle Entity
	 */
	public static Entity CustomEntity (EntityType VehicleType, EntityType PassengerType, Location Loc, int ticksLived) {
		Entity VehicleEntity = Loc.getWorld().spawnEntity(Loc, VehicleType);
		Entity PassengerEntity = Loc.getWorld().spawnEntity(Loc, PassengerType);
		VehicleEntity.setPassenger(PassengerEntity);
		VehicleEntity.setTicksLived(ticksLived);
		PassengerEntity.setTicksLived(ticksLived);
		return VehicleEntity;
	}
	
	/**
	 * This constructor creates a 'vehicle' entity along with its 'passenger' with a potion effect applied to it
	 * @param VehicleType The vehicle type (the Bottom)
	 * @param PassengerType The passenger type (the Top)
	 * @param Loc The location of both entities
	 * @param PotionEffect The potion effect to be applied to the passenger
	 * @return The Vehicle Entity
	 */
	public static Entity CustomEntity (EntityType VehicleType, EntityType PassengerType, Location Loc, String PotionEffect) {
		Entity VehicleEntity = Loc.getWorld().spawnEntity(Loc, VehicleType);
		Entity PassengerEntity = Loc.getWorld().spawnEntity(Loc, PassengerType);
		VehicleEntity.setPassenger(PassengerEntity);
		//True for Potion effect default to invisForever
		if(PotionEffect.compareTo("true") == 0) {
			PredefinedPotionEffect.invisForever.potionEffect.apply((LivingEntity) PassengerEntity);
		}
		else {
			PredefinedPotionEffect.valueOf(PotionEffect).potionEffect.apply((LivingEntity) PassengerEntity);
		}
		return VehicleEntity;
	}
	
	/**
	 * This constructor creates a passenger from an already existing 'vehicle'
	 * @param Vehicle The Vehicle entity
	 * @param PassengerType The type of passenger to be created
	 * @return The created Passenger Entity
	 */
	public static Entity MountedEntity (Entity Vehicle, EntityType PassengerType) {
		Location Loc = Vehicle.getLocation();
		Entity PassengerEntity = Loc.getWorld().spawnEntity(Loc, PassengerType);
		Vehicle.setPassenger(PassengerEntity);
		return PassengerEntity;
	}
	
	/**
	 * This constructor creates a passenger from an already existing 'vehicle' as well as a "tickes lived" parameter for difficulty
	 * @param Vehicle The vehicle entity
	 * @param PassengerType The type of passenger to be created
	 * @param ticksLived The amount of ticks the mob as 'lived' (higher levels mean higher difficulty)
	 * @return The created Passenger Entity
	 */
	public static Entity MountedEntity (Entity Vehicle, EntityType PassengerType, int ticksLived) {
		Location Loc = Vehicle.getLocation();
		Entity PassengerEntity = Loc.getWorld().spawnEntity(Loc, PassengerType);
		Vehicle.setPassenger(PassengerEntity);
		PassengerEntity.setTicksLived(ticksLived);
		return PassengerEntity;
	}
}
