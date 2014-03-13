package com.SkyIsland.MobSpawn;

import java.util.Random;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class spawnEvent implements Listener {
	
	private MobSpawn plugin;
	private boolean trip;
	
	protected spawnEvent(MobSpawn plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void generateSpawn(CreatureSpawnEvent event) {
		if ((event.getSpawnReason() == SpawnReason.NATURAL || event.getSpawnReason() == SpawnReason.CHUNK_GEN) && (event.getEntityType().compareTo(EntityType.SQUID) != 0)) {
			boolean trip = false;
			for (String e: plugin.config.getConfigurationSection("Main").getStringList("worlds")) {
				if (e.compareToIgnoreCase(event.getLocation().getWorld().getName()) == 0) {
					trip = true;
					break;
				}
			}
			
			if (trip == true) {
				event.setCancelled(true);
				String current = getMob(plugin.mobIdLookup);
				plugin.getLogger().info("String obtained: " + current);
				MobConfigProcessor.SpawnMob(current, plugin.mobIdLookup.getString("Types." + current), event.getLocation(), plugin);
			}
		}
	}
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void killHorses(EntityDeathEvent event){
		if (event.getEntity().isInsideVehicle() && event.getEntityType().compareTo(EntityType.PLAYER) != 0 && event.getEntity().getVehicle().getType().compareTo(EntityType.HORSE) == 0) {
			
			for (String e: plugin.config.getConfigurationSection("Main").getStringList("worlds")) {
				if (e.compareToIgnoreCase(event.getEntity().getLocation().getWorld().getName()) == 0) {
					trip = true;
					break;
				}
			}
			
			if (trip == true) {
				event.getEntity().getVehicle().remove();
			}
			
			
		}
	}
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void noFreeLunch(VehicleExitEvent event){
		if (event.getExited().getType().compareTo(EntityType.PLAYER) != 0) {
			for (String e: plugin.config.getConfigurationSection("Main").getStringList("worlds")) {
				if (e.compareToIgnoreCase(event.getVehicle().getLocation().getWorld().getName()) == 0) {
					trip = true;
					break;
				}
			}
			
			if (trip == true) {
				event.getVehicle().remove();
			}
			
			
		}
	}
	/*
	@EventHandler
	protected void riderGone(ChunkUnloadEvent event) {
		for (String e: plugin.config.getConfigurationSection("Main").getStringList("worlds")) {
			if (e.compareToIgnoreCase(event.getWorld().getName()) == 0) {
				trip = true;
				break;
			}
		}
			
		if (trip == true) {
			for (Entity e : event.getChunk().getEntities()) {
				if (e.getType() == EntityType.HORSE) {
					event.setCancelled(true);
					e.remove();
				}
			}

			
		}
	}
	*/
	
	
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
