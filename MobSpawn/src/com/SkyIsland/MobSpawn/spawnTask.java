package com.SkyIsland.MobSpawn;

import java.util.List;
import java.util.Set;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.YamlConfiguration;

public class spawnTask extends BukkitRunnable {
	
	MobSpawn plugin;
	double spawnRate;
	
	public spawnTask(MobSpawn plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * 
	 * @param mobTable the yaml config file to fetch mob from
	 * @return the keyword for the mob
	 */
	private String getMob(YamlConfiguration mobTable) {
		List<String> keys = mobTable.getStringList("Rates");
		int length, pos, chance, actualChance;
		
		length = keys.size();
		
		while (true) {
			pos = (int) Math.floor(Math.random() * length); //hopefully 0 through list length-1
			chance = (int) Math.random() * 100;
			actualChance = mobTable.getInt("Rates." + keys.get(pos), 100); //should load up the reported chance, defaulting to 100
			
			if (chance <= actualChance) {
				return keys.get(pos);
			}
		}
		
	}
	
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
