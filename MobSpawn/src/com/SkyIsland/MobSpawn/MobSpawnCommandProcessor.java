package com.SkyIsland.MobSpawn;

import java.lang.reflect.Array;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class MobSpawnCommandProcessor implements CommandExecutor {

	private MobSpawn plugin; //we can use this to reference any methods in our plugin
	
	
	//just the constructor
	public MobSpawnCommandProcessor(MobSpawn plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		//add test if console or not
		
				if (cmd.getName().equalsIgnoreCase("spawn_mob")) {
					//spawn mob with ID
					if (Array.getLength(args) != 1) {
						plugin.getLogger().info("invalid length on a spawn_mob request");
						return false;
					}

					Player player = (Player) sender;
					Entity mob;
					Location spawnAt = player.getLocation();
					double distance = 50.0;
					spawnAt.add((Math.random() * distance) - (distance / 2), (Math.random() * distance) - (distance / 2), (Math.random() * distance) - (distance / 2));
					
					//make sure to move from a stupid switch statement
					switch (Integer.parseInt(args[0])) {
					case 0:
						//just a zombie
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE);
						mob.setPassenger(player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE));
						return true;
					case 1:
						//giant zombie
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.GIANT);
						return true;
					case 2:
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.CREEPER);
						return true;
					case 3:
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN);
						mob.setPassenger(player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE));
						return true;
					case 4:
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN);
						mob.setPassenger(player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE));
						((Chicken) mob).setTarget(player);
						return true;
					case 5:
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.BAT);
						//a potion effect specified in MobSpawn is used here!
						plugin.invisQuietForever.apply((LivingEntity) mob);
						mob.setPassenger(player.getWorld().spawnEntity(spawnAt, EntityType.COW));
						return true;
					default:
						plugin.getLogger().info("MobSpawn debug: Looking up index failed!");
						return false;
					}
					
					//Bosses should use mob.setRemoveWhenFarAway(false)
					
					
				}
				if (cmd.getName().equalsIgnoreCase("spawn_constants")) {
					//print out constants
					Player player = (Player) sender;
					player.chat("zombie on zombie - 0 | giant - 1 | creeper - 2\nZombie on Chicken (aggro test) - 3 \n Chicken aggroed with a Zombie - 4 | Flying Cow - 5");
					return true;
				}
				
				return false;
	}
	
}
