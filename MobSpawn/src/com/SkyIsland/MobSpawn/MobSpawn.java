package com.SkyIsland.MobSpawn;

//import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobSpawn extends JavaPlugin {
	
	//we will create a couple PotionEffects (like invis) we can use with the mobs
	
		//this effect is invisibility, hopefully forever, with particles off
		
	
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
		try {
			loadYaml();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			getLogger().info("Plugin failed in loading/creating mobLookupTable file (located in MobSpawn/Resources/ in plugin directory) !!");
			getLogger().info("MobSpawn has loaded incorrectly");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
	
	/**
	 * Load up yaml files for easy parsing
	 * @throws InvalidConfigurationException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 * 
	 */
	protected void loadYaml() throws FileNotFoundException, IOException, InvalidConfigurationException {
		File path = new File(getDataFolder() + "/Resources");
		Config.makeMobIdLookupTable(path);
		
		//mobIdLookup = YamlConfiguration.loadConfiguration(this.getResource("MobIdLookupTable.yml"));
		//mobIdLookup.addDefault("Names", new Integer(-1));
		
	}
	
	
	
	
	
}
