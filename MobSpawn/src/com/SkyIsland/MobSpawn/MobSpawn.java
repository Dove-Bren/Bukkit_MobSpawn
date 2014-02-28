package com.SkyIsland.MobSpawn;

//import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class MobSpawn extends JavaPlugin {
	
	//we will create a couple PotionEffects (like invis) we can use with the mobs
	
		//this effect is invisibility, hopefully forever, with particles off
		protected PotionEffect invisQuietForever = new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, 1, true);
		
	
	//load up config files (a string-to-mob-id config file, for now
	YamlConfiguration mobIdLookup = new YamlConfiguration();
		
	@Override
	public void onEnable() {
		//runs when plugin is enabled;
		getLogger().info("Plugin enabled!");
		
		//need to specify the commands are to be run using the command processor
		MobSpawnCommandProcessor cmdProcess = new MobSpawnCommandProcessor(this); //create our command processor
		getCommand("spawn_mob").setExecutor(cmdProcess); //tie spawn_mob command to command processor
		getCommand("spawn_constants").setExecutor(cmdProcess); // " "
		
		//load up yaml files
		loadYaml();
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	/**
	 * Load up yaml files for easy parsing
	 * 
	 * 
	 */
	protected void loadYaml() {
		File path = new File(getDataFolder() + "/Resources");
		makeMobIdLookupTable(path);
		
		//mobIdLookup = YamlConfiguration.loadConfiguration(this.getResource("MobIdLookupTable.yml"));
		//mobIdLookup.addDefault("Names", new Integer(-1));
		
	}
	
	/**
	 * Creates the MobIdLookupTable
	 * 
	 * @param String local path to where it will be saved. Should leave off ending path separator
	 * @return whether or not it succeeded
	 */
	protected boolean makeMobIdLookupTable(File path) {

		
		if (!path.exists()) {
			//path doesn't exist, so create it
			
			if (!path.mkdirs()) {
				//failed; read-write permissions?
				getLogger().info("Failed to create directories up to the MobIdLookupTable!");
				return false;
			}
		}
		
		//path should have been created
		File pathName = new File(path.getPath() + File.separator + "MobIdLookupTable.yml");
		
		if (pathName.exists()) {
			//already exists
			mobIdLookup = new YamlConfiguration();
			try {
				mobIdLookup.load(pathName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		
		YamlConfiguration mobIdLookup = new YamlConfiguration();
		mobIdLookup.createSection("Types");
		mobIdLookup.set("Types.zombie", "simple");
		mobIdLookup.set("Types.zombie_on_zombie", "double ZOMBIE ZOMBIE");
		mobIdLookup.set("Types.skeleton", "skeleton");
		mobIdLookup.set("Types.skeleton_on_skeleton", "double SKELETON SKELETON");
		mobIdLookup.set("Types.totem_pole", "complex");
		mobIdLookup.set("Types.ghast_on_creeper", "double CREEPER GHAST true");
		mobIdLookup.set("Types.pig_mutated", "complex");
		
		
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
			getLogger().info("Failed to save yaml config file!");;
			return false;
		}
		
		
		return true;
		
	}
	
	
	
}
