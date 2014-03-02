package com.SkyIsland.MobSpawn;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public final class MobConfigProcessor {
	
	final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
			SimpleMobType.CustomEntity(EntityType.valueOf(Name.toUpperCase()), Loc);
		}
		if (tokenString.trim().compareTo("predefined") == 0) {
			//These predefined types are only here due to the specific nature of horses
			switch(PredefinedMobType.valueOf(Name)) {
			case skeletonOnHorse:
				PredefinedMobType.spawnSkeletonOnHorse(Loc, new ItemStack(Material.BOW));
				break;
			case witherSkeletonOnHorse:
				PredefinedMobType.witherSkeletonOnHorse(Loc, new ItemStack(Material.IRON_SWORD));
				break;
			case zombieOnHorse:
				PredefinedMobType.zombieOnHorse(Loc, new ItemStack(Material.IRON_SWORD));
				break;
			default:
				System.err.println("Incorrect Predefined Type!");
				break;
			}
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
			ComplexMobType.CustomEntity(EntityType.valueOf(Vehicle), EntityType.valueOf(Passenger), Loc);
		}
		if (tokenString.trim().compareToIgnoreCase("complex") == 0) {
			complexParser.createFromDefinition(plugin, Name, Loc);
		}
	}
}

