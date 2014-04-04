package com.SkyIsland.MobSpawn.mobs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;

import com.SkyIsland.MobSpawn.additions.ArmorSet;
import com.SkyIsland.MobSpawn.additions.CustomPotionEffect;
import com.SkyIsland.MobSpawn.additions.ItemFactory;

public class PredefinedMob implements CustomMob{
	
	public enum PredefinedMobType {
		WITHERSKELETON,
		SKELETONONHORSE,
		WITHERSKELETONONHORSE,
		ZOMBIEONHORSE,
		SKELETONHORSESQUAD,
		WITHERSKELETONSQUAD,
		ZOMBIEHORSESQUAD,
		SLIMEPYRAMID,
		SKELETONONHORSEONBAT,
		EMPTY;
	}
	
	//use an arraylist to store any and all mobs that will be spawned for this predefined mob type
	private ArrayList<CustomMob> entityList = new ArrayList<CustomMob>();
	
	//constantNAme will hold what the name of the constant used to initialize the PredefinedMob is.
	//this is so we have SOME way to figure out if a predefined mob is a zombieonhorse, or a squad, or what
	//namely for WitherSkeletons
	private String constantName = null;
	
	public PredefinedMob(PredefinedMobType type) {
		try {
			this.getClass().getMethod(type.name(), (Class<?>[]) null).invoke(this, (Object[]) null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This value of predefined mob is NOT FOR CASUAL USE.
	 * This version is to allow the internal modification of a PredefinedMob to get around certain parameter restrictions.
	 * <p />This *Does Not* introduce a way to modify the PRedefinedMob externally; only a way to sort of 'allocate'
	 * an empty PredefinedMob
	 */
	public void EMPTY() {
		constantName = "EMPTY";
	}
	
	public void WITHERSKELETON() {
		//redundant
		//constantName = "WITHERSKELETON";
		
		PredefinedMob mobPackage = WITHERSKELETON("none", false, 20, new ArmorSet(null, null, null, null, ItemFactory.RangedWeapon(3)), CustomPotionEffect.empty);
		this.constantName = mobPackage.constantName;
		this.entityList.add(mobPackage);
//		for (CustomMob mob : mobPackage.entityList) {
//			//we have to go through and add all the entities in it's list to our own :(
//			this.entityList.add(mob);
//		}
		mobPackage = null; //get rid of reference to useless package
	
	}
	/**
	 * This function is <u>much different</u> than WITHERSKELETON:
	 * <p>
	 * It returns a PredefinedMob corresponding to the requested WitherSkeleton
	 * </p>
	 * It cannot be called using default enumeration instantiation.
	 * @param name
	 * @param isBoss
	 * @param health
	 * @param armor
	 * @param potionEffect
	 * @return
	 */
	public PredefinedMob WITHERSKELETON(String name, boolean isBoss, int health, ArmorSet armor, CustomPotionEffect potionEffect) {
		//We want to make a new Predefined mob package with a defined wither skeleton.
		//this involves making a new "Empty" predefined mob and setting it up like we want
		//and then returning that new mob.
		PredefinedMob mobPackage = new PredefinedMob(PredefinedMobType.EMPTY);
		mobPackage.constantName = "WITHERSKELETON";
		
		SimpleMob skeleton;
		skeleton = new SimpleMob(EntityType.SKELETON, name, isBoss, health, armor, potionEffect);
		//EntityType entity, String name, boolean isBoss, int health, ArmorSet armor, CustomPotionEffect potionEffect
		
		mobPackage.entityList.add(skeleton);
		
		return mobPackage;
		
	}
	
	/**
	 * Sets up this CustomMob to construct a skeleton riding a horse upon spawnMob method call.
	 * <p />Because horses are of free-will and do not follow their master's directions, each skeleton is spawned
	 * with a bow rather than a melee weapon.
	 */
	public void SKELETONONHORSE() {
		constantName = "SKELETONONHORSE";
		
		SimpleMob skeletonRider;
		ArmorSet skeletonArmor = new ArmorSet(null, null, null, null, ItemFactory.RangedWeapon(1));
		skeletonRider = new SimpleMob(EntityType.SKELETON, "none", false, 20, skeletonArmor);
		
		//entityList.add(skeletonRider);

		HorseMob horse;
		Horse.Variant variant = Horse.Variant.SKELETON_HORSE;
		horse = new HorseMob(true, variant);
		
		StackedMob duo = new StackedMob(horse, skeletonRider);
		entityList.add(duo);
	}

	/**
	 * Creates a wither skeleton riding a horse in the appropriate fashion
	 */
	public void WITHERSKELETONONHORSE() {
		//constantName = "WITHERSKELETONONHORSE";
		PredefinedMob mobPackage;
		mobPackage = WITHERSKELETONONHORSE(WITHERSKELETON("none", false, 20, new ArmorSet(null, null, null, null, ItemFactory.RangedWeapon(3)), CustomPotionEffect.empty));
		
		//since calling WITHERSKELETONONHORSE doesn't initialize any of our variables (but returns a package with them)
		//we simply copy them
		this.constantName = mobPackage.constantName;
		this.entityList.add(mobPackage);
		
		
	}
	

	/**
	 * This method acts similar to WITHERSKELETON(args), and differently from WITHERSKELETONONHORSE:
	 * <p>
	 * The function RETURNS a predefined mob with the required specs.
	 * </p>
	 * @param skeletonRider
	 * @return
	 */
	public PredefinedMob WITHERSKELETONONHORSE(PredefinedMob skeletonRider) {
		PredefinedMob mobPackage = new PredefinedMob(PredefinedMobType.EMPTY);
		
		mobPackage.constantName = "WITHERSKELETONONHORSE";
		
		
		HorseMob horse;
		Horse.Variant variant = Horse.Variant.SKELETON_HORSE;
		horse = new HorseMob(true, variant);
		
		mobPackage.entityList.add(horse);
		mobPackage.entityList.add(skeletonRider);
		
		return mobPackage;
		
	}
	
	
	/**
	 * Creates a zombie riding a horse. Zombie is spawned with no equipment, and does nothing.
	 */
	public void  ZOMBIEONHORSE() {
		constantName = "ZOMBIEONHORSE";
		
		SimpleMob zombie;
		ArmorSet zombieArmor = new ArmorSet(null, null, null, null, ItemFactory.MeleeWeapon(2));
		zombie = new SimpleMob(EntityType.ZOMBIE, "none", false, 20, zombieArmor);
		
		HorseMob horse;
		horse = new HorseMob(true, Horse.Variant.UNDEAD_HORSE);
		
		StackedMob duo = new StackedMob(horse, zombie);
		this.entityList.add(duo);
		
	}
	
	/**
	 * Spawns a whole squad of skeletons, each with weapons of moderate enchantment.
	 * <p />
	 * A more powerful skeleton (wither skeleton) is also created as a leader, who will possess greater equipment.
	 */
	public void SKELETONHORSESQUAD() {
		constantName = "SKELETONHORSESQUADE";
		
		int squadCount = (int) ((Math.random() % 4) + 2); //get squadcount to be 2-5
		
		for (; squadCount > 0; squadCount--) {
			//same as repeat(squadCount)
			this.entityList.add(new PredefinedMob(PredefinedMobType.SKELETONONHORSE));
		}
		
		//now we create the chief, bringing out squad total to 3-6
		PredefinedMob chief;
		chief = new PredefinedMob(PredefinedMobType.WITHERSKELETONONHORSE);
		this.entityList.add(chief);
		
	}
	
	/**
	 * Creates a wither skeleton squad, with a very powerful leader.
	 */
	public void  WITHERSKELETONSQUAD() {
		constantName = "WITHERSKELETONSQUAD";
		
		int squadCount = (int) ((Math.random() % 4) + 2); //get squadcount to be 1-4
		
		for (; squadCount > 0; squadCount--) {
			//same as repeat(squadCount)
			this.entityList.add(new PredefinedMob(PredefinedMobType.WITHERSKELETONONHORSE));
		}
		
		//now we create the chief, bringing out squad total to 2-5
		PredefinedMob chief;
		ArmorSet chiefArmor = new ArmorSet(new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), null, null, ItemFactory.RangedWeapon(3));
		chief = WITHERSKELETON("none", true, 20, chiefArmor, CustomPotionEffect.empty);
		chief = WITHERSKELETONONHORSE(chief);
		this.entityList.add(chief);	
		
		
	}
	
	/**
	 * Creates a squade of zombie horsemen armed with swords!
	 */
	public void  ZOMBIEHORSESQUAD() {
		constantName = "ZOMBIESQUAD";
		
		int squadCount = (int) ((Math.random() % 4) + 2); //get squadcount to be 2-5
		
		for (; squadCount > 0; squadCount--) {
			//same as repeat(squadCount)
			this.entityList.add(new PredefinedMob(PredefinedMobType.ZOMBIEONHORSE));
		}
		
		//now we create the chief, bringing out squad total to 3-6
		PredefinedMob chief;
		chief = new PredefinedMob(PredefinedMobType.ZOMBIEONHORSE);
		this.entityList.add(chief);
		
		
	}
	
	/**
	 * Creates a slime pyramid of height 4 or 5
	 */
	public void  SLIMEPYRAMID() {
		constantName = "SLIMEPYRAMID";
		
		for (int i = (int) ((Math.random() % 2) +4); i > 0; i++) {
			//4 - 5 times
			this.entityList.add(new SimpleMob(EntityType.SLIME));
		}
		
	}
	
	/**
	 * Creates a Skeleton on a horse, which is on an invisible bat.
	 */
	public void  SKELETONONHORSEONBAT() {
		constantName = "SKELETONONHORSEONBAT";
		
		SimpleMob skeleton = new SimpleMob(EntityType.SKELETON, "none", false, 20, new ArmorSet(null, null, null, null, ItemFactory.RangedWeapon(1)));
		
		HorseMob horse = new HorseMob(true, Horse.Variant.SKELETON_HORSE);
		
		SimpleMob bat = new SimpleMob(EntityType.BAT, "none", false, 6, new ArmorSet(null, null, null, null, null), CustomPotionEffect.invisForever);
		
		StackedMob duo = new StackedMob(bat, horse, skeleton);
		
		this.entityList.add(duo);
		
	}
	
	
	/**
	 * Spawns the mobtype held in this class at the given location.
	 * @param location Where to spawn the mob at
	 * @todo Make the locations of each group-member randomized a bit instead of spawning them all in the same location
	 */
	@Override
	public LivingEntity spawnMob(Location location) {
		
		//We are given a list of mobs to spawn. They can be simple mobs, stacked mobs, who knows. We just spawn'em
		if (entityList.isEmpty()) {
			System.out.println("Tried to create PredefinedMob without any setup: PredefinedMob.java:spawnMob() - " + this.constantName);
			return null;
		}
		
		LivingEntity returnEntity = null;
		
		//if we have more than one CustomMob to create, it's a group. Instead of finding
		//a spawnable location for each, we'll just spawn them all on the same spot: location
		for (CustomMob mob : entityList) {
			

			if (mob instanceof PredefinedMob && ((PredefinedMob) mob).constantName.equals("WITHERSKELETONONHORSE")) {
				//kind of gross, I know. Introducing the WITHER SKELETON exception made it so we can't just pack
				//a WITHERSKELETON and horse into a stacked mob: a wither skeleton is a predefined mob, not a simple mob!
				returnEntity = ((PredefinedMob) mob).entityList.get(0).spawnMob(location); //should be horse
				returnEntity.setPassenger(((PredefinedMob) mob).entityList.get(1).spawnMob(location));
				// above should set skeleton on horse and still return horse.
				
				continue;
			}
			
			if (mob instanceof PredefinedMob && ((PredefinedMob) mob).constantName.equals("SLIMEPYRAMID")) {
				//we have to do slime pryamid separately, because each slime has a 'size' to be set that's not
				//accessible though SimpleMob
				LivingEntity slime2; //use to hold slime for increased reabability in the below loop
				int size = 1; //used to increase size of slimes
				for (CustomMob slime : ((PredefinedMob) mob).entityList) {
					//we know they're all just slimes
					slime2 = returnEntity;
					returnEntity = slime.spawnMob(location);
					((Slime) returnEntity).setSize(size);
					
					//return entity will be getting bigger and bigger, and holds the base of each sub-pyramid
					returnEntity.setPassenger(slime2);
					
					size++;
				}
				continue;
			}
			
			
			
			returnEntity = mob.spawnMob(location);

			/*so here's the deal: rather than make a Skeleton subclass of SimpleMob (like horses), I decided
			 * to make a predefined skeleton. The downside is I need to make a special if statement here. It
			 * is nasty, and I hate doing it, but I would like to keep the classes simple
			 * rather than make other nasty extensions like HorseMob
			 */
			if (mob instanceof PredefinedMob && ((PredefinedMob) mob).constantName.equals("WITHERSKELETON")) {
				((Skeleton) returnEntity).setSkeletonType(SkeletonType.WITHER);
			}
		}
		
		//returnEntity is given the last-spawned entity each time. We'll return whichever spawned last.
		
		
		return returnEntity;
	}
	
	/**
	 * Returns whether or not every single mob that's part of this PredefinedMobType can spawn at the location
	 * @param location the Location to check at....
	 */
	@Override
	public boolean canSpawn(Location location) {
		
		if (entityList.isEmpty()) {
			//no entities were ever specified...
			return false;
			//we return false just to kind of signal an error
		}
		
		for (CustomMob mob : entityList) {
			if (!mob.canSpawn(location)) {
				//if any of the mobs designated can't spawn, return false
				return false;
			}
		}
		
		//if no mobs ended up not being able to spawn, it must be okay for them to spawn here.
		return true;
	}
	
//	
//	/**
//	 * This constructor creates a Skeleton Horse with a Skeleton Rider armed with an item
//	 * @param loc The location for which the mobs will spawn at
//	 * @param SkeletonWeapon The item that will be in the Skeleton Rider's hand
//	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
//	 */
//	@SuppressWarnings("unused")
//	private LivingEntity skeletonOnHorse (Location location) {
//		ItemStack SkeletonWeapon = ItemFactory.RangedWeapon(1);
//		Horse HorseVehicle;
//		Skeleton SkeletonRider;
//		HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
//		HorseVehicle.setTamed(Boolean.TRUE);
//		SkeletonRider = (Skeleton) location.getWorld().spawnEntity(HorseVehicle.getLocation(), EntityType.SKELETON);
//		SkeletonRider.getEquipment().setItemInHand(SkeletonWeapon);
//		HorseVehicle.setPassenger(SkeletonRider);
//		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//		HorseVehicle.setRemoveWhenFarAway(true);
//		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
//		return HorseVehicle;
//	}
//	
//	/**
//	 * This constructor creates a Skeleton Horse with a Wither Skeleton Rider armed with an item
//	 * @param loc The location for which the mobs will spawn at
//	 * @param SkeletonWeapon The item that will be in the Wither Skeleton Rider's hand
//	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
//	 */
//	@SuppressWarnings("unused")
//	private LivingEntity witherSkeletonOnHorse (Location location) {
//		ItemStack SkeletonWeapon = ItemFactory.RangedWeapon(3);
//		Horse HorseVehicle;
//		Skeleton WitherSkeletonRider;
//		HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
//		HorseVehicle.setTamed(Boolean.TRUE);
//		WitherSkeletonRider = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
//		WitherSkeletonRider.setSkeletonType(Skeleton.SkeletonType.WITHER);
//		WitherSkeletonRider.getEquipment().setItemInHand(SkeletonWeapon);
//		HorseVehicle.setPassenger(WitherSkeletonRider);
//		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//		HorseVehicle.setRemoveWhenFarAway(true);
//		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
//		return HorseVehicle;
//	}
//	
//	/**
//	 * This constructor creates an Undead Horse with a Zombie Rider armed with an item
//	 * @param loc The location for which the mobs will spawn at
//	 * @param ZombieWeapon The item that will be in the Zombie Rider's hand
//	 * @bugs Warning: Default Horse entities will kick off their passengers (To fix, turn "tamed" on)
//	 */
//	@SuppressWarnings("unused")
//	private LivingEntity zombieOnHorse (Location location) {
//		ItemStack ZombieWeapon = new ItemStack(Material.IRON_SWORD);
//		Horse HorseVehicle;
//		Zombie ZombieRider;
//		HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
//		HorseVehicle.setTamed(Boolean.TRUE);
//		ZombieRider = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
//		ZombieRider.getEquipment().setItemInHand(ZombieWeapon);
//		HorseVehicle.setPassenger(ZombieRider);
//		HorseVehicle.setVariant(Horse.Variant.UNDEAD_HORSE);
//		HorseVehicle.setRemoveWhenFarAway(true);
//		//HorseVehicle.getInventory().setSaddle(new ItemStack(Material.SADDLE));
//		return HorseVehicle;
//	}
//	
//	/**
//	 * This constructor creates a squad of Skeleton Horses with Riders armed with items
//	 * In addition, a superior leader will also be spawned
//	 * @param loc The location for the squad
//	 */
//	@SuppressWarnings("unused")
//	private LivingEntity skeletonHorseSquad(Location location) {
//		Horse HorseVehicle;
//		Skeleton SkeletonRider;
//		int i;
//		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
//			HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
//			HorseVehicle.setTamed(Boolean.TRUE);
//			SkeletonRider = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
//			SkeletonRider.getEquipment().setItemInHand(ItemFactory.RangedWeapon(1));
//			HorseVehicle.setPassenger(SkeletonRider);
//			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//			HorseVehicle.setRemoveWhenFarAway(true);
//		}
//		HorseVehicle = (Horse) location.getWorld().spawnEntity(location,  EntityType.HORSE);
//		HorseVehicle.setTamed(Boolean.TRUE);
//		SkeletonRider = (Skeleton)	location.getWorld().spawnEntity(location,EntityType.SKELETON);
//		SkeletonRider.getEquipment().setItemInHand(ItemFactory.RangedWeapon(5));
//		HorseVehicle.setPassenger(SkeletonRider);
//		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
//		HorseVehicle.setRemoveWhenFarAway(true);
//		
//		return HorseVehicle;
//	}
//	
//	/**
//	 * This constructor creates a squad of Skeleton Horses with Wither Riders armed with items
//	 * In addition, a superior leader will also be spawned
//	 * @param loc The location for the squad
//	 */
//	@SuppressWarnings("unused")
//	private LivingEntity witherSkeletonSquad(Location location) {
//		Horse HorseVehicle;
//		Skeleton WitherSkeletonRider;
//		int i;
//		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
//			HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
//			HorseVehicle.setTamed(Boolean.TRUE);
//			WitherSkeletonRider = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
//			WitherSkeletonRider.getEquipment().setItemInHand(ItemFactory.RangedWeapon(1));
//			WitherSkeletonRider.setSkeletonType(Skeleton.SkeletonType.WITHER);
//			HorseVehicle.setPassenger(WitherSkeletonRider);
//			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//			HorseVehicle.setRemoveWhenFarAway(true);
//		}
//		HorseVehicle = (Horse) location.getWorld().spawnEntity(location,  EntityType.HORSE);
//		HorseVehicle.setTamed(Boolean.TRUE);
//		WitherSkeletonRider = (Skeleton)	location.getWorld().spawnEntity(location,EntityType.SKELETON);
//		WitherSkeletonRider.getEquipment().setItemInHand(ItemFactory.RangedWeapon(5));
//		HorseVehicle.setPassenger(WitherSkeletonRider);
//		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
//		HorseVehicle.setRemoveWhenFarAway(true);
//		
//		return HorseVehicle;
//	}
//	
//	/**
//	 * This constructor creates a squad of Skeleton Horses with Wither Riders armed with items
//	 * In addition, a superior leader will also be spawned
//	 * @param loc The location for the squad
//	 */
//	@SuppressWarnings("unused")
//	private LivingEntity zombieHorseSquad(Location location) {
//		Horse HorseVehicle;
//		Zombie ZombieRider;
//		int i;
//		for (i = (int) (Math.random() % 4) + 2; i > 0; i--) {
//			HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
//			HorseVehicle.setTamed(Boolean.TRUE);
//			ZombieRider = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
//			ZombieRider.getEquipment().setItemInHand(ItemFactory.RangedWeapon(1));
//			HorseVehicle.setPassenger(ZombieRider);
//			HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//			HorseVehicle.setRemoveWhenFarAway(true);
//		}
//		HorseVehicle = (Horse) location.getWorld().spawnEntity(location,  EntityType.HORSE);
//		HorseVehicle.setTamed(Boolean.TRUE);
//		ZombieRider = (Zombie)	location.getWorld().spawnEntity(location,EntityType.ZOMBIE);
//		ZombieRider.getEquipment().setItemInHand(ItemFactory.RangedWeapon(5));
//		HorseVehicle.setPassenger(ZombieRider);
//		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//		HorseVehicle.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
//		HorseVehicle.setRemoveWhenFarAway(true);
//		
//		return HorseVehicle;
//	}
//	
//	/**
//	 * Spawns a slime pyramid, with height 5. 
//	 * @param loc the location of the pyramid
//	 */
//	@SuppressWarnings("unused")
//	private LivingEntity slimePyramid(Location location) {
//		Slime currentSlime, oldSlime, firstSlime;
//		int i;
//		
//		firstSlime = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
//		currentSlime = firstSlime;
//		currentSlime.setSize(5);
//		
//		for (i = 4; i > 0; i--) {
//			oldSlime = currentSlime;
//			currentSlime = (Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
//			currentSlime.setSize(i);
//			oldSlime.setPassenger(currentSlime);
//		}
//		
//		return firstSlime;
//	}
//	
//	/**
//	 * Spawns a skeleton on a horse on a bat
//	 * @param loc the location of the monstah
//	 */
//	@SuppressWarnings("unused")
//	private LivingEntity skeletonOnHorseOnBat(Location location) {
//		Horse HorseVehicle;
//		Skeleton SkeletonRider;
//		Bat bat;
//		HorseVehicle = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
//		HorseVehicle.setTamed(Boolean.TRUE);
//		SkeletonRider = (Skeleton) location.getWorld().spawnEntity(HorseVehicle.getLocation(), EntityType.SKELETON);
//		SkeletonRider.getEquipment().setItemInHand(new ItemStack(Material.BOW));
//		HorseVehicle.setPassenger(SkeletonRider);
//		HorseVehicle.setVariant(Horse.Variant.SKELETON_HORSE);
//		HorseVehicle.setRemoveWhenFarAway(true);
//		bat = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
//		bat.setPassenger(HorseVehicle);
//		bat.addPotionEffects(CustomPotionEffect.invisForever.getPotionEffects());
//		
//		return bat;
//	}
//
//	@Override
//	public LivingEntity spawnMob(Location location) {
//		try {
//			return (LivingEntity) getClass().getMethod(name(), Location.class).invoke(this, location);
//		} catch (Exception  e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
