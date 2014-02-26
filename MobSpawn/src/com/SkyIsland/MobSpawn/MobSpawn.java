package com.SkyIsland.MobSpawn;

import java.lang.reflect.Array;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.EntityType;;

public final class MobSpawn extends JavaPlugin {
	
	 
	@Override
	public void onEnable() {
		//runs when plugin is enabled;
		getLogger().info("Plugin enabled!");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//add test if console or not
		
		if (cmd.getName().equalsIgnoreCase("spawn_mob")) {
			//spawn mob with ID
			if (Array.getLength(args) != 1) {
				getLogger().info("invalid length on a spawn_mob request");
				return false;
			}

			Player player = (Player) sender;
			Location spawnAt = player.getLocation();
			spawnAt.add(Math.random()*100, Math.random()*100, Math.random() * 100);
			
			//make sure to move from a stupid switch statement
			switch (args[0].toLowerCase()) {
			case "zombie":
				//just a zombie
				player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE);
				return true;
			case "giant":
				//giant zombie
				player.getWorld().spawnEntity(spawnAt, EntityType.GIANT);
				return true;
			case "creeper":
				player.getWorld().spawnEntity(spawnAt, EntityType.CREEPER);
				return true;
			default:
				getLogger().info("defaulted to false; no string matches on spawn_mob request");
				return false;
			}
			
			
		}
		if (cmd.getName().equalsIgnoreCase("spawn_constants")) {
			//print out constants
			Player player = (Player) sender;
			player.chat("zombie | giant | creeper");
			return true;
		}
		
		return false;
	}
}
