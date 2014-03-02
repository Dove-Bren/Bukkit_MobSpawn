package com.SkyIsland.MobSpawn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Config {
	
	final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	
	/**
	 * Creates (or tries to) the config file, which stores essential information like global spawn rating.
	 * 
	 * @param String local path to where it will be saved. Should leave off ending path separator
	 * @return whether or not it succeeded
	 */
	protected static YamlConfiguration makeConfig(File path) throws FileNotFoundException, IOException, InvalidConfigurationException {
		if (!path.exists()) {
			if (!path.mkdirs()) {
				//failed; read-write permissions?
				logger.info("Failed to create directories up to the MobIdLookupTable!");
				return null;
			}
		}
		
		File pathName = new File(path.getPath() + File.separator + "Config.yml");
		YamlConfiguration configFile = new YamlConfiguration();
		if (pathName.exists()) {
			configFile.load(pathName);
			if (configFile.contains("Main.spawn_rate") && configFile.contains("Main.internalSpawnEvent")) {
				return configFile;
			}
			//it exists, but is corrupt or doesn't have the right stuff
			pathName.delete();
		}
		//if it gets here, needs to create the file
		
		configFile.createSection("Main");
		configFile.set("Main.internalSpawnEvent", false);
		configFile.set("Main.spawn_rate", 1.0); //this is how many times the regular rate
		
		configFile.save(pathName);
		
		
		
		return configFile;
	}
	
	
	/**
	 * Creates the MobIdLookupTable. Doesn't create one if it already exists
	 * 
	 * @param String local path to where it will be saved. Should leave off ending path separator
	 * @return whether or not it succeeded
	 */
	protected static YamlConfiguration makeMobIdLookupTable(File path) throws FileNotFoundException, IOException, InvalidConfigurationException {
		
		
		
		if (!path.exists()) {
			//path doesn't exist, so create it
			
			if (!path.mkdirs()) {
				//failed; read-write permissions?
				logger.info("Failed to create directories up to the MobIdLookupTable!");
				return null;
			}
		}
		
		//path should have been created
		File pathName = new File(path.getPath() + File.separator + "MobIdLookupTable.yml");
		YamlConfiguration mobIdLookup;
		
		if (pathName.exists()) {
			//already exists
			
			//make sure that the file is current
			
			
			mobIdLookup = new YamlConfiguration();
			try {
				mobIdLookup.load(pathName);
			} catch (FileNotFoundException e) {
				logger.info("Threw FILENOTFOUND exception while trying to load mobIdLookup config file. Make sure file is a .yml file!!!!");
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				logger.info("Threw FILENOTFOUND exception while trying to load mobIdLookup config file. Make sure file is a .yml file!!!!");
				e.printStackTrace();
				throw e;
			} catch (InvalidConfigurationException e) {
				logger.info("Threw FILENOTFOUND exception while trying to load mobIdLookup config file. Make sure file is a .yml file!!!!");
				e.printStackTrace();
				throw e;
			}
			if (mobIdLookup.contains("Types") && mobIdLookup.contains("Types.totem_pole") && mobIdLookup.contains("Rates") && mobIdLookup.contains("Definitions") && mobIdLookup.contains("Definitions.totem_pole.entity3")) {
				return mobIdLookup;
			}
			else {
				pathName.delete();
				//delete the current config file so it can make a brand new one below
			}
		}
		mobIdLookup = new YamlConfiguration(); 
		mobIdLookup.createSection("Types");
		mobIdLookup.set("Types.zombie", "simple");
		mobIdLookup.set("Types.zombie_on_zombie", "double ZOMBIE ZOMBIE");
		mobIdLookup.set("Types.skeleton", "skeleton");
		mobIdLookup.set("Types.skeleton_on_skeleton", "double SKELETON SKELETON");
		mobIdLookup.set("Types.totem_pole", "complex");
		mobIdLookup.set("Types.ghast_on_creeper", "double CREEPER GHAST true");
		mobIdLookup.set("Types.pig_mutated", "complex");
		
		mobIdLookup.createSection("Rates");
		mobIdLookup.set("Rates.zombie", 80);
		mobIdLookup.set("Rates.zombie_on_zombie", 50);
		mobIdLookup.set("Rates.skeleton", 70);
		mobIdLookup.set("Rates.skeleton_on_skeleton", 40);
		mobIdLookup.set("Rates.totem_pole", 8);
		mobIdLookup.set("Rates.ghast_on_creeper", 15);
		mobIdLookup.set("Rates.pig_mutated", 30);
		
		
		mobIdLookup.createSection("Definitions");
		
		mobIdLookup.createSection("totem_pole");
		mobIdLookup.set("Definitions.totem_pole.numberOfPieces", 5);
		mobIdLookup.set("Definitions.totem_pole.isBoss", true);
		mobIdLookup.set("Definitions.totem_pole.entity1", "BLAZE");
		mobIdLookup.set("Definitions.totem_pole.entity1Equips", "none none none none none");
		mobIdLookup.set("Definitions.totem_pole.entity1Name", "none");
		mobIdLookup.set("Definitions.totem_pole.entity1Hp", 9);
		mobIdLookup.set("Definitions.totem_pole.entity1PotionEffect", "none");
		mobIdLookup.set("Definitions.totem_pole.entity2", "BLAZE");
		mobIdLookup.set("Definitions.totem_pole.entity2Equips", "none none none none none");
		mobIdLookup.set("Definitions.totem_pole.entity2Name", "none");
		mobIdLookup.set("Definitions.totem_pole.entity2Hp", 9);
		mobIdLookup.set("Definitions.totem_pole.entity2PotionEffect", "none");
		mobIdLookup.set("Definitions.totem_pole.entity3", "BLAZE");
		mobIdLookup.set("Definitions.totem_pole.entity3Equips", "none none none none none");
		mobIdLookup.set("Definitions.totem_pole.entity3Name", "none");
		mobIdLookup.set("Definitions.totem_pole.entity3Hp", 9);
		mobIdLookup.set("Definitions.totem_pole.entity3PotionEffect", "none");
		mobIdLookup.set("Definitions.totem_pole.entity4", "BLAZE");
		mobIdLookup.set("Definitions.totem_pole.entity4Equips", "none none none none none");
		mobIdLookup.set("Definitions.totem_pole.entity4Name", "none");
		mobIdLookup.set("Definitions.totem_pole.entity4Hp", 9);
		mobIdLookup.set("Definitions.totem_pole.entity4PotionEffect", "none");
		mobIdLookup.set("Definitions.totem_pole.entity5", "BLAZE");
		mobIdLookup.set("Definitions.totem_pole.entity5Equips", "none none none none none");
		mobIdLookup.set("Definitions.totem_pole.entity5Name", "none");
		mobIdLookup.set("Definitions.totem_pole.entity5Hp", 9);
		mobIdLookup.set("Definitions.totem_pole.entity5PotionEffect", "none");
		
		mobIdLookup.createSection("pig_mutated");
		mobIdLookup.set("Definitions.pig_mutated.numberOfPieces", 2);
		mobIdLookup.set("Definitions.pig_mutated.isBoss", "false");
		mobIdLookup.set("Definitions.pig_mutated.entity1", "PIG");
		mobIdLookup.set("Definitions.pig_mutated.entity1Equips", "none none none none none");
		mobIdLookup.set("Definitions.pig_mutated.entity1Name", "");
		mobIdLookup.set("Definitions.pig_mutated.entity1Hp", 20);
		mobIdLookup.set("Definitions.pig_mutated.entity1PotionEffect", "none");
		mobIdLookup.set("Definitions.pig_mutated.entity2", "PIG");
		mobIdLookup.set("Definitions.pig_mutated.entity2Equips", "none none none none none");
		mobIdLookup.set("Definitions.pig_mutated.entity2Name", "Grumm");
		mobIdLookup.set("Definitions.pig_mutated.entity2Hp", 20);
		mobIdLookup.set("Definitions.pig_mutated.entity2PotionEffect", "none");
		
		
		try {
			mobIdLookup.save(pathName);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("Failed to save yaml config file!");;
			return null;
		}
		
		
		return mobIdLookup;
		
	}
}
