package com.SkyIsland.MobSpawn;

import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.java.JavaPlugin;

public class spawnEvent implements Listener {
	
	private MobSpawn plugin;
	
	protected spawnEvent(MobSpawn plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void generateSpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() == SpawnReason.NATURAL || event.getSpawnReason() == SpawnReason.CHUNK_GEN && event.getEntityType().compareTo(EntityType.SQUID) == 0) {
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
				MobConfigProcessor.SpawnMob(current, plugin.mobIdLookup.getString("Types." + current), event.getLocation(), plugin);
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
