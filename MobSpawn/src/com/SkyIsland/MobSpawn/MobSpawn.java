package com.SkyIsland.MobSpawn;

//import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobSpawn extends JavaPlugin {
	
	public static double spawnRate;
	protected spawnEvent spawn;
		
	
	//load up config files (a string-to-mob-id config file, for now
	YamlConfiguration mobIdLookup = new YamlConfiguration();
	YamlConfiguration config = new YamlConfiguration();
		
	@Override
	public void onEnable() {
		//runs when plugin is enabled;
		getLogger().info("MobSpawn Plugin enabled!");
		
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
		
		
		getLogger().info("MobSpawn initialization complete and successful!");
		
		
		if (config.getBoolean("Main.internalSpawnEvent")) {
		
			spawnRate = config.getDouble("Main.spawn_rate", 1.0);
			ArrayList<spawnTask> taskList = new ArrayList<spawnTask>();
			spawnTask task;
			int i;
			
			for (i = 0; i <= Math.floor(spawnRate); i++) {
				task = new spawnTask(this);
				taskList.add(task);
				task.runTaskTimer(this, 1, 1);
			}
			
			if (spawnRate - Math.floor(spawnRate) <= .05) {
				task = new spawnTask(this);
				taskList.add(task);
				task.runTaskTimer(this, 1, (long) (1/spawnRate));
			}
			
			
		}
		else {
			spawnEvent spawn = new spawnEvent(this);
			getServer().getPluginManager().registerEvents(spawn, this);
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
		
		mobIdLookup = Config.makeMobIdLookupTable(path);
		config = Config.makeConfig(path);
		
		
		//mobIdLookup = YamlConfiguration.loadConfiguration(this.getResource("MobIdLookupTable.yml"));
		//mobIdLookup.addDefault("Names", new Integer(-1));
		
	}
	
	
	
	
	
}
