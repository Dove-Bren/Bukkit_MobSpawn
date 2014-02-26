package com.SkyIsland.MobSpawn;

//import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class MobSpawn extends JavaPlugin {
	
	//we will create a couple PotionEffects (like invis) we can use with the mobs
	
		//this effect is invisibility, hopefully forever, with particles off
		protected PotionEffect invisQuietForever = new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, 1, true);
		
	
	//load up config files (a string-to-mob-id config file, for now
	//YamlConfiguration mobIdLookup = new YamlConfiguration();
	//mobIdLookup.load("");
		
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
	
	/**
	 * Load up yaml files for easy parsing
	 * 
	 * 
	 */
	protected void loadYaml() {
		
	}
	
	
	
}
