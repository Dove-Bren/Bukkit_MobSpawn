package com.SkyIsland.MobSpawn;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

/**
 * Catches spawn events and changes the creatures being spawned
 *
 */
public class MonsterSpawner implements Listener {
	
	private MobSpawn plugin;
	
	//load the config once, generate entities, and store them here
	private Set<CustomMob> mobs;
	
	//after all mobs are in the set, turn it into an array, for fast random access
	private CustomMob[] mobList;
	
	private Random random;
	
	public MonsterSpawner(MobSpawn plugin, YamlConfiguration config) {
		mobs = new HashSet<CustomMob>();
		random = new Random();
		this.plugin = plugin;
		
		//add all mobs
		loadMobs();
		
		//generate random list
		mobList = (CustomMob[]) mobs.toArray();
		
	}
	
	
	private void loadMobs() {
		// TODO Auto-generated method stub
		
	}

	public Entity getRandomEntity(){
		while(true){
			int rand_index = random.nextInt(mobList.length);
			int rand_spawn = random.nextInt(101);
			
			if (mobList[rand_index].getSpawnChance() <= rand_spawn){
				return mobList[rand_index].getEntity();
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
				String current = getMob(plugin.mobIdLookup);
				MobConfigProcessor.SpawnMob(current, plugin.mobIdLookup.getString("Types." + current), event.getLocation(), plugin);
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
