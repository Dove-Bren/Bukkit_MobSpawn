package com.SkyIsland.MobSpawn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobSpawn extends JavaPlugin {
	
	public static double spawnRate;
	
	final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	//the monster spawner
	protected MonsterSpawner spawn;
	
	//filenames
	private final String configName = "config.yml";
	private final String mobTableName = "MobIdLookupTable.yml";
	
	//files
	private final File configFile = new File(this.getDataFolder(), configName);
	private final File mobTableFile = new File(this.getDataFolder(), mobTableName);
	
	//configurations
	public YamlConfiguration config;
	public YamlConfiguration mobTable;
	
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
		
		MonsterSpawner spawn = new MonsterSpawner(this, mobTable);
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
		
		//load config files
		loadConfig();
		loadMobTable();
		
		//load worlds
		
		//load mobs
		
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
	 * Loads the mob table. Creates a default mob table if one doesn't exist
	 */
	private void loadMobTable(){
		try{
		
			//create file if it doesn't exist
			if (!mobTableFile.exists()){
				if (!mobTableFile.createNewFile()){
					return;
				}
				
				//create default
				createDefaultMobTable();
			}
			
			//load configuration
			mobTable.load(mobTableFile);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a default configuration
	 * @throws IOException
	 */
	private void createDefaultConfig() throws IOException{
		config.createSection("Main");
		ArrayList<String> worldList = new ArrayList<String>();
		worldList.add("wilderness");
		config.getConfigurationSection("Main").set("worlds", worldList);
		config.save(configFile);
	}
	
	/**
	 * Creates a default mob table
	 * @throws IOException
	 */
	private void createDefaultMobTable() throws IOException{
		mobTable = new YamlConfiguration(); 
		mobTable.createSection("Types");
		mobTable.set("Types.zombie", "simple");
		mobTable.set("Types.zombie_on_zombie", "double ZOMBIE ZOMBIE");
		mobTable.set("Types.skeleton", "skeleton");
		mobTable.set("Types.skeleton_on_skeleton", "double SKELETON SKELETON");
		mobTable.set("Types.totem_pole", "complex");
		mobTable.set("Types.ghast_on_creeper", "double CREEPER GHAST true");
		mobTable.set("Types.pig_mutated", "complex");
		
		mobTable.createSection("Rates");
		mobTable.set("Rates.zombie", 80);
		mobTable.set("Rates.zombie_on_zombie", 50);
		mobTable.set("Rates.skeleton", 70);
		mobTable.set("Rates.skeleton_on_skeleton", 40);
		mobTable.set("Rates.totem_pole", 8);
		mobTable.set("Rates.ghast_on_creeper", 15);
		mobTable.set("Rates.pig_mutated", 30);
		
		mobTable.createSection("Definitions");
		
		mobTable.createSection("totem_pole");
		mobTable.set("Definitions.totem_pole.numberOfPieces", 5);
		mobTable.set("Definitions.totem_pole.isBoss", true);
		mobTable.set("Definitions.totem_pole.entity1", "BLAZE");
		mobTable.set("Definitions.totem_pole.entity1Equips", "none none none none none");
		mobTable.set("Definitions.totem_pole.entity1Name", "none");
		mobTable.set("Definitions.totem_pole.entity1Hp", 9);
		mobTable.set("Definitions.totem_pole.entity1PotionEffect", "none");
		mobTable.set("Definitions.totem_pole.entity2", "CHIKEN");
		mobTable.set("Definitions.totem_pole.entity2Equips", "none none none none none");
		mobTable.set("Definitions.totem_pole.entity2Name", "none");
		mobTable.set("Definitions.totem_pole.entity2Hp", 9);
		mobTable.set("Definitions.totem_pole.entity2PotionEffect", "invisForever");
		mobTable.set("Definitions.totem_pole.entity3", "BLAZE");
		mobTable.set("Definitions.totem_pole.entity3Equips", "none none none none none");
		mobTable.set("Definitions.totem_pole.entity3Name", "none");
		mobTable.set("Definitions.totem_pole.entity3Hp", 9);
		mobTable.set("Definitions.totem_pole.entity3PotionEffect", "none");
		mobTable.set("Definitions.totem_pole.entity4", "CHICKEN");
		mobTable.set("Definitions.totem_pole.entity4Equips", "none none none none none");
		mobTable.set("Definitions.totem_pole.entity4Name", "none");
		mobTable.set("Definitions.totem_pole.entity4Hp", 9);
		mobTable.set("Definitions.totem_pole.entity4PotionEffect", "invisForever");
		mobTable.set("Definitions.totem_pole.entity5", "BLAZE");
		mobTable.set("Definitions.totem_pole.entity5Equips", "none none none none none");
		mobTable.set("Definitions.totem_pole.entity5Name", "none");
		mobTable.set("Definitions.totem_pole.entity5Hp", 9);
		mobTable.set("Definitions.totem_pole.entity5PotionEffect", "none");
		
		mobTable.createSection("pig_mutated");
		mobTable.set("Definitions.pig_mutated.numberOfPieces", 2);
		mobTable.set("Definitions.pig_mutated.isBoss", "false");
		mobTable.set("Definitions.pig_mutated.entity1", "PIG");
		mobTable.set("Definitions.pig_mutated.entity1Equips", "none none none none none");
		mobTable.set("Definitions.pig_mutated.entity1Name", "");
		mobTable.set("Definitions.pig_mutated.entity1Hp", 20);
		mobTable.set("Definitions.pig_mutated.entity1PotionEffect", "none");
		mobTable.set("Definitions.pig_mutated.entity2", "PIG");
		mobTable.set("Definitions.pig_mutated.entity2Equips", "none none none none none");
		mobTable.set("Definitions.pig_mutated.entity2Name", "Grumm");
		mobTable.set("Definitions.pig_mutated.entity2Hp", 20);
		mobTable.set("Definitions.pig_mutated.entity2PotionEffect", "none");
		
		mobTable.save(mobTableFile);
	}
	
}
