package com.SkyIsland.MobSpawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import com.SkyIsland.MobSpawn.additions.ArmorSet;
import com.SkyIsland.MobSpawn.additions.CustomPotionEffect;
import com.SkyIsland.MobSpawn.mobs.CustomMob;
import com.SkyIsland.MobSpawn.mobs.PredefinedMob;
import com.SkyIsland.MobSpawn.mobs.SimpleMob;
import com.SkyIsland.MobSpawn.mobs.StackedMob;

/**
 * Catches spawn events and changes the creatures being spawned
 *
 */
public class MonsterSpawner implements Listener {
		
	//load the config once, storing each mapping between CustomMob to Rates in mobList for fast, random access
	private ArrayList<MobToSpawn> mobList;
	
	private Random random;
	
	private Set<String> worlds;
	
	public MonsterSpawner(YamlConfiguration config) {
		worlds = new HashSet<String>();
		random = new Random();
		
		mobList = new ArrayList<MobToSpawn>(); //like a map, except instead of Entry we have MobToSpawn
		//add all mobs
		loadMobs(config);
		
		//generate mob list for fast, random access
		//mobList = new ArrayList<MobToSpawn>(mobs.entrySet());
		
		//load worlds
		loadWorlds(config);
	}
	
	private void loadWorlds(YamlConfiguration config) {
		List<String> worlds = config.getConfigurationSection("Main").getStringList("Worlds");
		if (worlds == null) {
			System.out.println("worlds == null!!!\nMake sure to specify which worlds are to use MobSpawn in the config file in a section 'Worlds' as a string list.");
			return;
		}
		
		//check if worlds list is empty; if so, we have a problem
		if (worlds.isEmpty()) {
			System.out.println("Unanble to load any worlds into MobSpawn plugin!\nMake sure to specify the worlds to use a list of strings in a section title 'Worlds'");
		}
		
		//a little redundant, but just being safe
		for (String world: worlds){
			System.out.println("Added world " + world);
			this.worlds.add(world);
		}
	}
	
	private void loadMobs(YamlConfiguration config) {
		Map<String, String> mobTypes = new HashMap<String, String>();
		Map<String, Integer> mobRates = new HashMap<String, Integer>();
		
		ConfigurationSection typesSection = config.getConfigurationSection("Types");
		if (typesSection == null) return;
		
		//get names
		for (String mob: typesSection.getKeys(false)){
			mobTypes.put(mob, typesSection.getString(mob));
		}
		
		ConfigurationSection ratesSection = config.getConfigurationSection("Rates");
		if (ratesSection == null) return;
		
		//get rates
		for (String mob: typesSection.getKeys(false)){
			mobRates.put(mob, ratesSection.getInt(mob));
		}
		
		//Go ahead and get the definitions section
		ConfigurationSection definitionsSection = config.getConfigurationSection("Definitions");
		
		//parse types
		for (Entry<String, String> entry: mobTypes.entrySet()){
			
			String name = entry.getKey();
			String type = entry.getValue();
			
			Integer rate = mobRates.get(name);
			if (rate == null) continue;
			
			//simple
			if (type.equalsIgnoreCase("simple")){
				//mobs.put(new SimpleMob(EntityType.valueOf(type)), rate);
				//We move from using a map just so we can get an entry set just so we can get an array of mappings
				//to an ArrayList of MobToSpawn's. MobToSpawn is a class that acts like an Entry; it's just a
				//key/value mapping. We store it straight in mobList and don't have to worry about
				//extracting values to an array for random access.
				SimpleMob mob = new SimpleMob(EntityType.valueOf(name.toUpperCase())); //pull this out for readibility
				mobList.add(new MobToSpawn(mob, rate));
			
			}
			
			//double
			else if (type.toLowerCase().startsWith("double")){
				String[] parts = type.split(" ");
				SimpleMob bottom = new SimpleMob(EntityType.valueOf(parts[1].toUpperCase()));
				SimpleMob top = new SimpleMob(EntityType.valueOf(parts[2].toUpperCase()));
				//mobs.put(new StackedMob(bottom, top), rate);
				mobList.add(new MobToSpawn(new StackedMob(bottom, top), rate));
				//add entry mapping a stacked mob to it's rate.
				//nested is new StackedMob(bottom, top) which creates the StackedMob to add to the entry mapping
			}
			
			//predefined
			else if (type.equalsIgnoreCase("predefined")){
				//mobs.put(PredefinedMob.valueOf(name), rate); replace with just adding an 'entry' to our
				//arrayList mobList;
				mobList.add(new MobToSpawn(PredefinedMob.valueOf(name.toUpperCase()), rate));
			}
			
			//complex
			else if (type.equalsIgnoreCase("complex")){
				ConfigurationSection complexSection = definitionsSection.getConfigurationSection(name);
				CustomMob mob = getComplexMob(complexSection);
				if (mob != null){
					//mobs.put(mob, rate); We are gonna put a MobToSpawn (aka entry mocking class) into
					//mobList, an arrayList holding each mapping
					mobList.add(new MobToSpawn(mob, rate));
				}
			}	
		}
	}

	private CustomMob getComplexMob(ConfigurationSection complexSection) {

		int numberOfPieces = complexSection.getInt("numberOfPieces");
		boolean isBoss = complexSection.getBoolean("isBoss");
		List<SimpleMob> mobs = new LinkedList<SimpleMob>();
		
		//read in the mobs
		for (int i = 0; i < numberOfPieces; i++){
			
			String prefix = "entity" + (i + 1);
			
			// get type entity1: CAVE_SPIDER
			EntityType type = (complexSection.contains(prefix)) ? EntityType.valueOf(complexSection.getString(prefix).toUpperCase()) : EntityType.ZOMBIE;
			
		    
		    // get name entity1Name: none
			String customName = (complexSection.contains(prefix + "Name")) ? complexSection.getString(prefix + "Name") : "";
			
		    // get hp entity1Hp: 70
			int hp = (complexSection.contains(prefix + "Hp")) ? complexSection.getInt(prefix + "Hp") : 10;
			
			// get armor entity1Equips: none none none none none
			ArmorSet armor = (complexSection.contains(prefix + "Equips")) ? parseArmorString(complexSection.getString(prefix + "Equips")) : null;	
			
		    // get potion effect entity1PotionEffect: speedIV
			CustomPotionEffect potionEffect = (complexSection.contains(prefix + "PotionEffect") && !complexSection.getString(prefix + "PotionEffect").equalsIgnoreCase("none")) ? CustomPotionEffect.valueOf(complexSection.getString(prefix + "PotionEffect")) : null;
			
			
			SimpleMob mob = new SimpleMob(type, customName, isBoss, hp, armor,potionEffect);
			
			mobs.add(mob);
		}
		
		if (mobs.size() == 0){
			return null;
		}
		else if (mobs.size() == 1){
			return mobs.get(0);
		}
		else{
			SimpleMob bottom = mobs.remove(0);
			return new StackedMob(bottom, mobs);
		}
	}


	private ArmorSet parseArmorString(String string) {
		String[] parts = string.split(" ");
		ItemStack helmet = null, chestplate = null, leggings = null, boots = null, weapon = null;
		
		if (parts.length > 0 && !parts[0].equalsIgnoreCase("none")) helmet     = new ItemStack(Material.valueOf(parts[0]));
		if (parts.length > 1 && !parts[1].equalsIgnoreCase("none")) chestplate = new ItemStack(Material.valueOf(parts[1]));
		if (parts.length > 2 && !parts[2].equalsIgnoreCase("none")) leggings   = new ItemStack(Material.valueOf(parts[2]));
		if (parts.length > 3 && !parts[3].equalsIgnoreCase("none")) boots      = new ItemStack(Material.valueOf(parts[3]));
		if (parts.length > 4 && !parts[4].equalsIgnoreCase("none")) weapon     = new ItemStack(Material.valueOf(parts[4]));
		
		return new ArmorSet(helmet, chestplate, leggings, boots, weapon);
	}


	public CustomMob getRandomEntity(){
		while(true){
			int rand_index = random.nextInt(mobList.size());
			int rand_spawn = random.nextInt(101);
			
			MobToSpawn entry = mobList.get(rand_index);
			
			if (entry.rate <= rand_spawn){
				return entry.mob;
			}
		}
	}
	
	/**
	 * Catches a CreatureSpawnEvent and changes the creature being spawned
	 * @param event
	 */
	@EventHandler (priority=EventPriority.HIGH)
	protected void generateSpawn(CreatureSpawnEvent event) {
		
		//do nothing if the spawn event was not naturally occurring
		if (event.getSpawnReason() != SpawnReason.NATURAL && event.getSpawnReason() != SpawnReason.CHUNK_GEN){
			return;
		}
		
		if (worlds.contains(event.getLocation().getWorld().getName())){
			event.setCancelled(true);
			CustomMob mob = this.getRandomEntity();
			mob.spawnMob(event.getLocation());
		}
		
	}
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void killHorses(EntityDeathEvent event){
		
		if (event.getEntity().isInsideVehicle() && (!event.getEntityType().equals(EntityType.PLAYER)) && event.getEntity().getVehicle().getType().equals(EntityType.HORSE)) {
			
			//check if world is a custom mob world
			if (worlds.contains(event.getEntity().getWorld().getName())){
				event.getEntity().getVehicle().remove();
				return;
			}
		}
	}
	
	@EventHandler (priority=EventPriority.HIGH)
	protected void removeVehicle(VehicleExitEvent event){
		
		//do nothing if the player exited
		if (event.getExited().getType().equals(EntityType.PLAYER)){
			return;
		}
		
		if (worlds.contains(event.getVehicle().getWorld().getName())){
			event.getVehicle().remove();
			return;
		}
	}
	
}
