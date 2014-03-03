package com.SkyIsland.MobSpawn;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Fireball;

public class spawnEvent implements Listener {
	
	private MobSpawn plugin;
	private Entity helperEntity;
	private Location loc;
	private double distance;
	
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
				MobConfigProcessor.SpawnMob(current, plugin.mobIdLookup.getString("Types." + current), event.getLocation(), plugin);
			}
		}
	}
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void modifyFireBall(ProjectileHitEvent event) {
		if (event.getEntityType().compareTo(EntityType.FIREBALL) == 0) {
			List<Entity> entitylist = event.getEntity().getNearbyEntities(1, 0, 1);
			for (Entity e : entitylist) {
				if (e.getType().compareTo(EntityType.BLAZE) == 0) {
					plugin.getLogger().info("Fireball hit a blaze");
					loc = e.getLocation();
					loc = event.getEntity().getLocation().subtract(loc);
					loc.multiply(10);
					loc.add(e.getLocation());
					helperEntity = loc.getWorld().spawnEntity(loc, EntityType.FIREBALL);
					((Fireball) helperEntity).setDirection(((Fireball) (event.getEntity())).getDirection());
					break;
				}
			}
		}
	}
	
	/*
	@EventHandler (priority=EventPriority.HIGH)
	protected void modifyFireBall(EntityDamageByEntityEvent event) {
			if (event.getDamager() != null) {
				if (event.getDamager().getType().compareTo(EntityType.FIREBALL) == 0 && event.getEntity().getType().compareTo(EntityType.BLAZE) == 0) {
					//we dont want blazes to have their fireballs stopped by other blazes
					//so we try just ignoring the event????
					event.setCancelled(true);
					helperEntity = event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.FIREBALL);
					((Fireball) helperEntity).setDirection(((Fireball) event.getEntity()).getDirection());
					
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
