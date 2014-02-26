package com.SkyIsland.MobSpawn;

import org.bukkit.plugin.java.JavaPlugin;

public final class MobSpawn extends JavaPlugin {
	
	 
	@Override
	public void onEnable() {
		//runs when plugin is enabled;
		getLogger().info("Plugin enabled!");
		
		//need to specify the commands are to be run using the command processor
		MobSpawnCommandProcessor cmdProcess = new MobSpawnCommandProcessor(this); //create our command processor
		getCommand("spawn_mob").setExecutor(cmdProcess); //tie spawn_mob command to command processor
		getCommand("spawn_constants").setExecutor(cmdProcess); // " "
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
}
