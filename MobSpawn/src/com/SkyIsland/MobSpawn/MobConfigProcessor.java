package com.SkyIsland.MobSpawn;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public final class MobConfigProcessor {
	
	final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Entity helper;
	/**
	 * This method determines how a new entity(ies) will be generated
	 * @param Name The name of the entity
	 * @param Command Various attributes associated with the specified mob type
	 * @param Loc The location to generate the mob
	 */
	public static void SpawnMob (String Name, String Command, Location Loc, MobSpawn plugin) {
		StringTokenizer stringToken = new StringTokenizer(Command, " ");
		String tokenString = stringToken.nextToken();
		//Check for type of mob: Simple, Predefined, Complex
		if (tokenString.trim().compareTo("simple") == 0) {
			helper =  SimpleMobType.CustomEntity(EntityType.valueOf(Name.toUpperCase()), Loc);
			if (helper.getType().compareTo(EntityType.SKELETON) == 0) {
				((LivingEntity) helper).getEquipment().setItemInHand(InventoryCreator.RangedWeapon((int) (2 + Math.random() * 3)));
			}
		}
		if (tokenString.trim().compareTo("predefined") == 0) {
			PredefinedMobType.spawnLocalized(PredefinedMobType.valueOf(Name), Loc);
			
			
		}
		if (tokenString.trim().compareTo("double") == 0) {
			//Determine how complex types will be generated
			tokenString = stringToken.nextToken();
			tokenString.trim();
			tokenString.toUpperCase();
			String Vehicle = tokenString;
			if (!stringToken.hasMoreTokens()) {
				logger.info("Incorrect yml feed!");
				return;
			}
			tokenString = stringToken.nextToken();
			tokenString.trim();
			tokenString.toUpperCase();
			String Passenger = tokenString;
			if (!stringToken.hasMoreTokens())
				ComplexMobType.CustomEntity(EntityType.valueOf(Vehicle), EntityType.valueOf(Passenger), Loc, InventoryCreator.RangedWeapon((int) (2 + Math.random() * 3)));
			else {
				tokenString = stringToken.nextToken();
				ComplexMobType.CustomEntity(EntityType.valueOf(Vehicle), EntityType.valueOf(Passenger), Loc, tokenString);
			}
		}
		if (tokenString.trim().compareToIgnoreCase("complex") == 0) {
			complexParser.createFromDefinition(plugin, Name, Loc);
		}
	}
}

