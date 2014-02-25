package com.SkyIsland.MobSpawn;

import java.lang.reflect.Array;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
				return false;
			}
			
			//make sure to move from a stupid switch statement
			switch (Integer.parseInt(args[0])) {
			case 1:
				//just a zombie
				Player player = (Player) sender;
				player.getWorld().spawnEntity(arg0, arg1)
			}
			
			return true;
		}
		
		return false;
	}
}
