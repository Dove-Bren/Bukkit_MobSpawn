package com.SkyIsland.MobSpawn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobSpawnPlugin extends JavaPlugin {
	
	public static double spawnRate;
	
	final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	//the monster spawner
	protected MonsterSpawner spawn;
	
	private final String configName = "config.yml";
	private final File configFile = new File(this.getDataFolder(), configName);
	public YamlConfiguration config;
	
	@Override
	public void onEnable() {
		
		//need to specify the commands are to be run using the command processor
		MobSpawnCommandProcessor cmdProcess = new MobSpawnCommandProcessor(this); //create our command processor
		getCommand("spawn_mob").setExecutor(cmdProcess); //tie spawn_mob command to command processor
		getCommand("spawn_constants").setExecutor(cmdProcess); // " "
		
		//load up yaml files
		try {
			load();
		} catch (Exception e) {
			getLogger().info("Plugin failed in loading/creating mobLookupTable file (located in MobSpawn/Resources/ in plugin directory) !!");
			getLogger().info("MobSpawn has loaded incorrectly");
			e.printStackTrace();
		}
		
		getLogger().info("MobSpawn initialization complete and successful!");
		getLogger().info("MobSpawn is now turning off regular mob spawning in worlds specified in config.yml");
		
		MonsterSpawner spawn = new MonsterSpawner(this, config);
		getServer().getPluginManager().registerEvents(spawn, this);
	}
	
	/**
	 * Load up yaml files for easy parsing
	 * @throws InvalidConfigurationException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected void load() throws FileNotFoundException, IOException, InvalidConfigurationException {
		File path = new File(getDataFolder() + "/Resources");
		
		//create plugin directory
		if (!path.exists()) {
			if (!path.mkdirs()) {
				//failed; read-write permissions?
				logger.info("Failed to create directories up to the MobIdLookupTable!");
				return;
			}
		}
		
		//load config (mobs and worlds)
		loadConfig();	
	}
	
	/**
	 * Loads the config file. Creates a default config file if one doesn't exist
	 */
	private void loadConfig(){	
		try{
			
			//create file if it doesn't exist
			if (!configFile.exists()){
				if (!configFile.createNewFile()){
					return;
				}
				//create default
				this.createDefaultConfig();
			}
			
			//load configuration
			config.load(configFile);
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a default mob table and world configuration
	 * @throws IOException
	 */
	private void createDefaultConfig() throws IOException{
		
		ArrayList<String> worldList = new ArrayList<String>();
		worldList.add("wilderness");
		config.getConfigurationSection("Main").set("Worlds", worldList);
		
		config = new YamlConfiguration(); 
		config.createSection("Types");
		config.set("Types.zombie", "simple");
		config.set("Types.zombie_on_zombie", "double ZOMBIE ZOMBIE");
		config.set("Types.skeleton", "skeleton");
		config.set("Types.skeleton_on_skeleton", "double SKELETON SKELETON");
		config.set("Types.totem_pole", "complex");
		config.set("Types.ghast_on_creeper", "double CREEPER GHAST true");
		config.set("Types.pig_mutated", "complex");
		
		config.createSection("Rates");
		config.set("Rates.zombie", 80);
		config.set("Rates.zombie_on_zombie", 50);
		config.set("Rates.skeleton", 70);
		config.set("Rates.skeleton_on_skeleton", 40);
		config.set("Rates.totem_pole", 8);
		config.set("Rates.ghast_on_creeper", 15);
		config.set("Rates.pig_mutated", 30);
		
		config.createSection("Definitions");
		
		config.createSection("totem_pole");
		config.set("Definitions.totem_pole.numberOfPieces", 5);
		config.set("Definitions.totem_pole.isBoss", true);
		config.set("Definitions.totem_pole.entity1", "BLAZE");
		config.set("Definitions.totem_pole.entity1Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity1Name", "none");
		config.set("Definitions.totem_pole.entity1Hp", 9);
		config.set("Definitions.totem_pole.entity1PotionEffect", "none");
		config.set("Definitions.totem_pole.entity2", "CHIKEN");
		config.set("Definitions.totem_pole.entity2Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity2Name", "none");
		config.set("Definitions.totem_pole.entity2Hp", 9);
		config.set("Definitions.totem_pole.entity2PotionEffect", "invisForever");
		config.set("Definitions.totem_pole.entity3", "BLAZE");
		config.set("Definitions.totem_pole.entity3Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity3Name", "none");
		config.set("Definitions.totem_pole.entity3Hp", 9);
		config.set("Definitions.totem_pole.entity3PotionEffect", "none");
		config.set("Definitions.totem_pole.entity4", "CHICKEN");
		config.set("Definitions.totem_pole.entity4Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity4Name", "none");
		config.set("Definitions.totem_pole.entity4Hp", 9);
		config.set("Definitions.totem_pole.entity4PotionEffect", "invisForever");
		config.set("Definitions.totem_pole.entity5", "BLAZE");
		config.set("Definitions.totem_pole.entity5Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity5Name", "none");
		config.set("Definitions.totem_pole.entity5Hp", 9);
		config.set("Definitions.totem_pole.entity5PotionEffect", "none");
		
		config.createSection("pig_mutated");
		config.set("Definitions.pig_mutated.numberOfPieces", 2);
		config.set("Definitions.pig_mutated.isBoss", "false");
		config.set("Definitions.pig_mutated.entity1", "PIG");
		config.set("Definitions.pig_mutated.entity1Equips", "none none none none none");
		config.set("Definitions.pig_mutated.entity1Name", "");
		config.set("Definitions.pig_mutated.entity1Hp", 20);
		config.set("Definitions.pig_mutated.entity1PotionEffect", "none");
		config.set("Definitions.pig_mutated.entity2", "PIG");
		config.set("Definitions.pig_mutated.entity2Equips", "none none none none none");
		config.set("Definitions.pig_mutated.entity2Name", "Grumm");
		config.set("Definitions.pig_mutated.entity2Hp", 20);
		config.set("Definitions.pig_mutated.entity2PotionEffect", "none");
		
		config.save(configFile);
	}
	
}
