package com.SkyIsland.MobSpawn;

import java.lang.reflect.Array;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

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
					ItemStack skeletonBow;
					int index;
					
					//!!!!!!!!!!!!!!!!!!!!!!!!!
					//Notice i've moved the argument of the switch statement from args[0]
					//to an index returned by lookup up a string in a yaml file.
					//
					//Even if you make a class to do this spawning (like abstract this switch to a class)
					//make sure you still take whatever STRING is passed to the command
					//and lookup the proper index
					//!!!!!!!!!!!!!!!!!!!!!!!!!
					
					index = plugin.mobIdLookup.getInt("Names." + args[0].toLowerCase());
					
					//if it didn't match, returns 0
					
					
					//make sure to move from a stupid switch statement
					switch (index) {
					/* case 0 is what it returns if the constant wasn't recognized!
					case 0:
						//just a zombie
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE);
						mob.setPassenger(player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE));
						return true;
					*/
					case 19: //these values should not be hardcoded; instead load them from yaml for enforced
							//conform. Unfortunately, a switch makes you...
						//giant zombie
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.GIANT);
						return true;
					case 3:
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.CREEPER);
						return true;
					case 20:
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE);
						((Ageable) mob).setBaby();
						PredefinedPotionEffect.invisForever.potionEffect.apply((LivingEntity) mob);
						mob.setPassenger(player.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN));
						return true;
					case 4:
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN);
						mob.setPassenger(player.getWorld().spawnEntity(spawnAt, EntityType.ZOMBIE));
						((Chicken) mob).setTarget(player);
						return true;
					case 5:
						mob = player.getWorld().spawnEntity(spawnAt, EntityType.BAT);
						//a potion effect specified in MobSpawn is used here!
						PredefinedPotionEffect.invisForever.potionEffect.apply((LivingEntity) mob);
						mob.setPassenger(player.getWorld().spawnEntity(spawnAt, EntityType.COW));
						return true;
					case 6:
						Skeleton testSkele = (Skeleton) player.getWorld().spawn(player.getLocation(), Skeleton.class);
						skeletonBow = new ItemStack(Material.BOW);
						skeletonBow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
						//testSkele.setCustomName("LOLLOLOLOLOLOL\n");
						testSkele.getEquipment().setItemInHand(skeletonBow);
						return true;
					case 7:
						Horse testHorse = (Horse) player.getWorld().spawn(player.getLocation(), Horse.class);
						Skeleton testSkele2 = (Skeleton) player.getWorld().spawn(player.getLocation(), Skeleton.class);
						skeletonBow = new ItemStack(Material.BOW);
						skeletonBow.addEnchantment(Enchantment.ARROW_FIRE,1);
						testSkele2.getEquipment().setItemInHand(skeletonBow);
						testHorse.setVariant(Horse.Variant.SKELETON_HORSE);
						testHorse.setPassenger(testSkele2);
						testHorse.setTamed(true);
						return true;
					case 8:
						Slime testSlime = (Slime) player.getWorld().spawn(player.getLocation(), Slime.class);
						Slime testSlime2 = (Slime) player.getWorld().spawn(player.getLocation(), Slime.class);
						Slime testSlime3 = (Slime) player.getWorld().spawn(player.getLocation(), Slime.class);
						Slime testSlime4 = (Slime) player.getWorld().spawn(player.getLocation(), Slime.class);
						Slime testSlime5 = (Slime) player.getWorld().spawn(player.getLocation(), Slime.class);
						testSlime.setSize(5);
						testSlime2.setSize(4);
						testSlime3.setSize(3);
						testSlime4.setSize(2);
						testSlime5.setSize(1);
						testSlime.setPassenger(testSlime2);
						testSlime2.setPassenger(testSlime3);
						testSlime3.setPassenger(testSlime4);
						testSlime4.setPassenger(testSlime5);
						return true;
					case 9:
						Ghast testGhast = (Ghast) player.getWorld().spawn(player.getLocation(), Ghast.class);
						Creeper testCreeper = (Creeper) player.getWorld().spawn(player.getLocation(), Creeper.class);
						PredefinedPotionEffect.invisForever.potionEffect.apply((LivingEntity) testGhast);
						testCreeper.setPassenger(testGhast);
						testCreeper.getEquipment().setHelmet(new ItemStack(Material.TNT));
						testGhast.setMaximumAir(1000);
						return true;
					case 10:
						for (int i = 0; i < 100; i++) {
							if (Math.round(Math.random()) % 2 == 0) {
								Chicken testChicken = (Chicken) player.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN);						Creeper testCreeper2 = (Creeper) player.getWorld().spawn(player.getLocation(), Creeper.class);
								PredefinedPotionEffect.invisForever.potionEffect.apply((LivingEntity) testCreeper2);
								testChicken.setPassenger(testCreeper2);
							}
							else {
								player.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN);	
							}
						}
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
