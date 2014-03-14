package com.SkyIsland.MobSpawn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import com.SkyIsland.MobSpawn.mobs.CustomMob;
import com.SkyIsland.MobSpawn.mobs.PredefinedMob;
import com.SkyIsland.MobSpawn.mobs.SimpleMob;
import com.SkyIsland.MobSpawn.mobs.StackedMob;

/**
 * Catches spawn events and changes the creatures being spawned
 *
 */
public class MonsterSpawner implements Listener {
	
	private MobSpawn plugin;
	
	//load the config once, generate entities, and store them here
	private Map<CustomMob, Integer> mobs;
	
	//after all mobs are in the set, turn it into an array, for fast random access
	private Entry<CustomMob,Integer>[] mobList;
	
	private Random random;
	
	private Set<String> worlds;
	
	@SuppressWarnings("unchecked")
	public MonsterSpawner(MobSpawn plugin, YamlConfiguration config) {
		worlds = new HashSet<String>();
		mobs = new HashMap<CustomMob, Integer>();
		random = new Random();
		this.plugin = plugin;
		
		//add all mobs
		loadMobs(config);
		
		//generate random list
		mobList = (Entry<CustomMob, Integer>[]) mobs.entrySet().toArray();
		
	}
	
	
	private void loadMobs(YamlConfiguration config) {
		Map<String, String> mobTypes = new HashMap<String, String>();
		Map<String, Integer> mobRates = new HashMap<String, Integer>();
		
		ConfigurationSection typesSection = config.getConfigurationSection("Types");
		if (typesSection == null) return;
		
		//get names
		for (String mob: typesSection.getKeys(false)){
			mobTypes.put(mob, typesSection.getString(mob));
		}
		
		ConfigurationSection ratesSection = config.getConfigurationSection("Rates");
		if (ratesSection == null) return;
		
		//get rates
		for (String mob: typesSection.getKeys(false)){
			mobRates.put(mob, ratesSection.getInt(mob));
		}
		
		//parse types
		for (Entry<String, String> entry: mobTypes.entrySet()){
			
			String name = entry.getKey();
			String type = entry.getValue();
			
			Integer rate = mobRates.get(name);
			if (rate == null) continue;
			
			//simple
			if (type.equalsIgnoreCase("simple")){
				mobs.put(new SimpleMob(EntityType.valueOf(type)), rate);
			}
			
			//double
			else if (type.toLowerCase().startsWith("double")){
				String[] parts = type.split(" ");
				SimpleMob bottom = new SimpleMob(EntityType.valueOf(parts[1]));
				SimpleMob top = new SimpleMob(EntityType.valueOf(parts[2]));
				mobs.put(new StackedMob(bottom, top), rate);
			}
			
			//predefined
			else if (type.equalsIgnoreCase("predefined")){
				mobs.put(PredefinedMob.valueOf(name), rate);
			}
			
			//complex
			else if (type.equalsIgnoreCase("complex")){
				
				
				
				
			}
			
		}
	}

	public CustomMob getRandomEntity(){
		while(true){
			int rand_index = random.nextInt(mobList.length);
			int rand_spawn = random.nextInt(101);
			
			Entry<CustomMob,Integer> entry = mobList[rand_index];
			
			if (entry.getValue() <= rand_spawn){
				return entry.getKey();
			}
		}
	}
	
	/**
	 * Catches a CreatureSpawnEvent and changes the creature being spawned
	 * @param event
	 */
	@EventHandler (priority=EventPriority.HIGH)
	protected void generateSpawn(CreatureSpawnEvent event) {
		
		//do nothing if the spawn event was not naturally occurring
		if (event.getSpawnReason() != SpawnReason.NATURAL && event.getSpawnReason() != SpawnReason.CHUNK_GEN){
			return;
		}
		
		for (String e: plugin.config.getConfigurationSection("Main").getStringList("worlds")) {
			if (e.compareToIgnoreCase(event.getLocation().getWorld().getName()) == 0) {
				event.setCancelled(true);
				String current = getMob(plugin.mobTable);
				MobConfigProcessor.SpawnMob(current, plugin.mobTable.getString("Types." + current), event.getLocation(), plugin);
				return;
			}
		}
		
	}
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void killHorses(EntityDeathEvent event){
		
		if (event.getEntity().isInsideVehicle() && event.getEntityType().compareTo(EntityType.PLAYER) != 0 && event.getEntity().getVehicle().getType().compareTo(EntityType.HORSE) == 0) {
			
			//check if world is a custom mob world
			for (String e: plugin.config.getConfigurationSection("Main").getStringList("worlds")) {
				if (e.compareToIgnoreCase(event.getEntity().getLocation().getWorld().getName()) == 0) {
					event.getEntity().getVehicle().remove();
					return;
				}
			}		
		}
	}
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void removeVehicle(VehicleExitEvent event){
		
		//do nothing if the player exited
		if (event.getExited().getType().equals(EntityType.PLAYER)){
			return;
		}
		
		//check if world is a custom mob world
		for (String e: plugin.config.getConfigurationSection("Main").getStringList("worlds")) {
			if (e.compareToIgnoreCase(event.getVehicle().getLocation().getWorld().getName()) == 0) {
				event.getVehicle().remove();
				return;
			}
		}
	}
	
	/**
	 * 
	 * @param mobTable the yaml config file to fetch mob from
	 * @return the keyword for the mob
	 */
	public static String getMob(YamlConfiguration mobTable) {
		Object keys[] = null;
		Set<String> tempSet = mobTable.getConfigurationSection("Rates").getKeys(true);
		Random rGen = new Random();
		
		keys = tempSet.toArray();
		
		int length, pos, chance, actualChance;
		
		length = keys.length;
		
		while (true) {
			pos = (int) Math.floor(Math.random() * length); //hopefully 0 through list length-1
			chance = (int) rGen.nextInt(100);
			actualChance = mobTable.getInt("Rates." + keys[pos].toString(), 100); //should load up the reported chance, defaulting to 100
			
			if (chance <= actualChance) {
				return keys[pos].toString();
				
			}
		}
		
	}
}
