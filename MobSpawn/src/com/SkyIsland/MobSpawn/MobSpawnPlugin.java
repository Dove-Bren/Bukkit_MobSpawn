package com.SkyIsland.MobSpawn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobSpawnPlugin extends JavaPlugin {
	
	public static double spawnRate;
	
	final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	//the monster spawner
	protected MonsterSpawner spawn;
	
	private final String configName = "config.yml";
	private final File configFile = new File(this.getDataFolder(), configName);
	public YamlConfiguration config;
	
	@Override
	public void onEnable() {
		
		//load up yaml files
		try {
			load();
		} catch (Exception e) {
			getLogger().info("Plugin failed in loading/creating mobLookupTable file (located in MobSpawn/Resources/ in plugin directory) !!");
			getLogger().info("MobSpawn has loaded incorrectly");
			e.printStackTrace();
		}
		
		getLogger().info("MobSpawn initialization complete and successful!");
		getLogger().info("MobSpawn is now turning off regular mob spawning in worlds specified in config.yml");
		
		MonsterSpawner spawn = new MonsterSpawner(this, config);
		getServer().getPluginManager().registerEvents(spawn, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("spawn_mob")) {
			//spawn mob with ID
			if (Array.getLength(args) != 1) {
				getLogger().info("invalid length on a spawn_mob request");
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
			
			index = config.getInt("Names." + args[0].toLowerCase());
			
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
				CustomPotionEffect.invisForever.applyPotionEffects((LivingEntity) mob);
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
				CustomPotionEffect.invisForever.applyPotionEffects((LivingEntity) mob);
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
				CustomPotionEffect.invisForever.applyPotionEffects((LivingEntity) testGhast);
				testCreeper.setPassenger(testGhast);
				testCreeper.getEquipment().setHelmet(new ItemStack(Material.TNT));
				testGhast.setMaximumAir(1000);
				return true;
			case 10:
				for (int i = 0; i < 100; i++) {
					if (Math.round(Math.random()) % 2 == 0) {
						Chicken testChicken = (Chicken) player.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN);						Creeper testCreeper2 = (Creeper) player.getWorld().spawn(player.getLocation(), Creeper.class);
						CustomPotionEffect.invisForever.applyPotionEffects((LivingEntity) testCreeper2);
						testChicken.setPassenger(testCreeper2);
					}
					else {
						player.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN);	
					}
				}
				return true;
			default:
				getLogger().info("MobSpawn debug: Looking up index failed!");
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
	
	/**
	 * Load up yaml files for easy parsing
	 * @throws InvalidConfigurationException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected void load() throws FileNotFoundException, IOException, InvalidConfigurationException {
		File path = new File(getDataFolder() + "/Resources");
		
		//create plugin directory
		if (!path.exists()) {
			if (!path.mkdirs()) {
				//failed; read-write permissions?
				logger.info("Failed to create directories up to the MobIdLookupTable!");
				return;
			}
		}
		
		//load config (mobs and worlds)
		loadConfig();	
	}
	
	/**
	 * Loads the config file. Creates a default config file if one doesn't exist
	 */
	private void loadConfig(){	
		try{
			
			//create file if it doesn't exist
			if (!configFile.exists()){
				if (!configFile.createNewFile()){
					return;
				}
				//create default
				this.createDefaultConfig();
			}
			
			//load configuration
			config.load(configFile);
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a default mob table and world configuration
	 * @throws IOException
	 */
	private void createDefaultConfig() throws IOException{
		
		ArrayList<String> worldList = new ArrayList<String>();
		worldList.add("wilderness");
		config.getConfigurationSection("Main").set("Worlds", worldList);
		
		config = new YamlConfiguration(); 
		config.createSection("Types");
		config.set("Types.zombie", "simple");
		config.set("Types.zombie_on_zombie", "double ZOMBIE ZOMBIE");
		config.set("Types.skeleton", "skeleton");
		config.set("Types.skeleton_on_skeleton", "double SKELETON SKELETON");
		config.set("Types.totem_pole", "complex");
		config.set("Types.ghast_on_creeper", "double CREEPER GHAST true");
		config.set("Types.pig_mutated", "complex");
		
		config.createSection("Rates");
		config.set("Rates.zombie", 80);
		config.set("Rates.zombie_on_zombie", 50);
		config.set("Rates.skeleton", 70);
		config.set("Rates.skeleton_on_skeleton", 40);
		config.set("Rates.totem_pole", 8);
		config.set("Rates.ghast_on_creeper", 15);
		config.set("Rates.pig_mutated", 30);
		
		config.createSection("Definitions");
		
		config.createSection("totem_pole");
		config.set("Definitions.totem_pole.numberOfPieces", 5);
		config.set("Definitions.totem_pole.isBoss", true);
		config.set("Definitions.totem_pole.entity1", "BLAZE");
		config.set("Definitions.totem_pole.entity1Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity1Name", "none");
		config.set("Definitions.totem_pole.entity1Hp", 9);
		config.set("Definitions.totem_pole.entity1PotionEffect", "none");
		config.set("Definitions.totem_pole.entity2", "CHIKEN");
		config.set("Definitions.totem_pole.entity2Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity2Name", "none");
		config.set("Definitions.totem_pole.entity2Hp", 9);
		config.set("Definitions.totem_pole.entity2PotionEffect", "invisForever");
		config.set("Definitions.totem_pole.entity3", "BLAZE");
		config.set("Definitions.totem_pole.entity3Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity3Name", "none");
		config.set("Definitions.totem_pole.entity3Hp", 9);
		config.set("Definitions.totem_pole.entity3PotionEffect", "none");
		config.set("Definitions.totem_pole.entity4", "CHICKEN");
		config.set("Definitions.totem_pole.entity4Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity4Name", "none");
		config.set("Definitions.totem_pole.entity4Hp", 9);
		config.set("Definitions.totem_pole.entity4PotionEffect", "invisForever");
		config.set("Definitions.totem_pole.entity5", "BLAZE");
		config.set("Definitions.totem_pole.entity5Equips", "none none none none none");
		config.set("Definitions.totem_pole.entity5Name", "none");
		config.set("Definitions.totem_pole.entity5Hp", 9);
		config.set("Definitions.totem_pole.entity5PotionEffect", "none");
		
		config.createSection("pig_mutated");
		config.set("Definitions.pig_mutated.numberOfPieces", 2);
		config.set("Definitions.pig_mutated.isBoss", "false");
		config.set("Definitions.pig_mutated.entity1", "PIG");
		config.set("Definitions.pig_mutated.entity1Equips", "none none none none none");
		config.set("Definitions.pig_mutated.entity1Name", "");
		config.set("Definitions.pig_mutated.entity1Hp", 20);
		config.set("Definitions.pig_mutated.entity1PotionEffect", "none");
		config.set("Definitions.pig_mutated.entity2", "PIG");
		config.set("Definitions.pig_mutated.entity2Equips", "none none none none none");
		config.set("Definitions.pig_mutated.entity2Name", "Grumm");
		config.set("Definitions.pig_mutated.entity2Hp", 20);
		config.set("Definitions.pig_mutated.entity2PotionEffect", "none");
		
		config.save(configFile);
	}
	
}
