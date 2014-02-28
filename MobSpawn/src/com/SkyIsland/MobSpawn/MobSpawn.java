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
				getLogger().info("Throw FILE NOT FOUND exception!");
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
		mobIdLookup.createSection("Names");
		mobIdLookup.set("Names.zombie", 1);
		mobIdLookup.set("Names.skeleton", 2);
		mobIdLookup.set("Names.creeper", 3);
		mobIdLookup.set("Names.ghast", 4);
		mobIdLookup.set("Names.blaze", 5);
		mobIdLookup.set("Names.cow", 6);
		mobIdLookup.set("Names.chicken", 7);
		mobIdLookup.set("Names.sheep", 8);
		mobIdLookup.set("Names.horse", 9);
		mobIdLookup.set("Names.zombie_horse", 10);
		mobIdLookup.set("Names.skeleton_horse", 11);
		mobIdLookup.set("Names.cow_flying", 12);
		mobIdLookup.set("Names.slime", 13);
		mobIdLookup.set("Names.slime_pyramid", 14);
		mobIdLookup.set("Names.creeper_on_chicken", 15);
		mobIdLookup.set("Names.skeleton_on_horse", 16);
		mobIdLookup.set("Names.ghast_on_creeper", 17);
		mobIdLookup.set("Names.pig_mutated", 18);
		mobIdLookup.set("Names.giant", 19);
		mobIdLookup.set("Names.chicken_on_baby_zombie", 20);
		
		
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
