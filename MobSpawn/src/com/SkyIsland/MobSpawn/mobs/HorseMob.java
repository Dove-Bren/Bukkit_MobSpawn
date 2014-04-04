package com.SkyIsland.MobSpawn.mobs;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

/**
 * Holds a horse, a specialized version of a SimpleMob.
 * <p>
 * Because horses need their type specified -- and must be tamed in order for mobs spawned on them to stay --
 * they got their own class.
 * </p>
 * <p>
 * This class will have everything a SimpleMob has, but also allow for the specification of horse variety, special
 * variables like tame-ness, and even allow for inventories to be specified (when applicable)
 * </p>
 * @author Skyler
 *
 */
public class HorseMob extends SimpleMob {
	
	//holds whether or not the represented horse is tame
	private boolean tame = true;
	//holds the variant of the represented horse
	private Horse.Variant variant = Horse.Variant.HORSE;
	//holds the horse's inventory as a list of items. This is added to the actual inventory upon creation.
	private ArrayList<ItemStack> inventory = null;
	
	/**
	 * default constructor that assumes tame is true (default) and variant is HORSE (default). Also leaves inventory as null.
	 */
	public HorseMob() {
		super(EntityType.HORSE);
	}
	/**
	 * HorseMob constructor that will set whether or not the horse is tame.
	 * @param tame
	 */
	public HorseMob(boolean tame) {
		super(EntityType.HORSE);
		this.tame = tame;
	}
	public HorseMob(Horse.Variant variant) {
		super(EntityType.HORSE);
		this.variant = variant;
	}
	public HorseMob(boolean tame, Horse.Variant variant) {
		super(EntityType.HORSE);
		this.tame = tame;
		this.variant = variant;
	}
	/**
	 * Horse constructor that takes in the inventory as a HorseInventory. Great for this object!
	 * @param inventory
	 */
	public HorseMob(HorseInventory inventory) {
		super(EntityType.HORSE);
		this.inventory = new ArrayList<ItemStack>();
		for (ItemStack item : inventory) {
			this.inventory.add(item);
		}
	}
	/**
	 * creates a horse prototype with the given inventory
	 * @param inventory
	 */
	public HorseMob(Collection<ItemStack> inventory) {
		super(EntityType.HORSE);
		this.inventory = new ArrayList<ItemStack>(inventory);
	}
	/**
	 * Creates a horse with the passed items. Must have at least one item passed
	 * @param item1 - the first of the items to be stored in the horse's inventory
	 * @param items - a list of items to be stored in the horse's inventory
	 */
	public HorseMob(ItemStack item1, ItemStack...items){
		super(EntityType.HORSE);
		this.inventory = new ArrayList<ItemStack>();
		this.inventory.add(item1);
		for (ItemStack item : items) {
			this.inventory.add(item);
		}
	}
	
	public HorseMob(boolean tame, HorseInventory inventory) {
		this(inventory);
		this.tame = tame;
	}
	public HorseMob(boolean tame, Collection<ItemStack> inventory) {
		this(inventory);
		this.tame = tame;
	}
	public HorseMob(boolean tame, ItemStack item1, ItemStack...item) {
		this(item1, item);
		this.tame = tame;
	}
	
	public HorseMob(Horse.Variant variant, HorseInventory inventory) {
		this(inventory);
		this.variant = variant;
	}
	public HorseMob(Horse.Variant variant, Collection<ItemStack> inventory) {
		this(inventory);
		this.variant = variant;
	}
	public HorseMob(Horse.Variant variant, ItemStack item1, ItemStack...items) {
		this(item1, items);
		this.variant = variant;
	}
	
	public HorseMob(boolean tame, Horse.Variant variant, HorseInventory inventory) {
		this(inventory);
		this.variant = variant;
	}
	public HorseMob(boolean tame, Horse.Variant variant, Collection<ItemStack> inventory) {
		this(inventory);
		this.variant = variant;
	}
	public HorseMob(boolean tame, Horse.Variant variant, ItemStack item1, ItemStack...items) {
		this(item1, items);
		this.variant = variant;
	}
	
	//finally done with constructors
	
	//specify own getters/setters
	public boolean isTame() {
		return tame;
	}
	public void setTame(boolean tame) {
		this.tame = tame;
	}
	public Horse.Variant getVariant() {
		return variant;
	}
	public void setVariant(Horse.Variant variant) {
		this.variant = variant;
	}
	public ArrayList<ItemStack> getInventory() {
		return inventory;
	}
	public void setInventory(ArrayList<ItemStack> inventory) {
		this.inventory = inventory;
	}
	
	
	//now override spawnMob, because it's different for us horse-folk
	@Override
	public LivingEntity spawnMob(Location location) {
		if (this.type != EntityType.HORSE) {
			//some tweaking has happened, which is not what we want
			System.out.println("Corrupt horse trying to spawn! This was initialized as a horse, but (through the magic of setters) has changed!\n\n");
			new Throwable().printStackTrace();
			return null;
			
		}
		
		Horse returnEntity = (Horse) super.spawnMob(location);
		
		//it will be of type horse, because that's what we specified
		returnEntity.setTamed(tame);
		returnEntity.setVariant(variant);
		
		//if our inventory is still set to null, the creater wants default inventory.
		//if not, we need to go through out list and add each item to the horse's inventory
		if (inventory != null) {
			//inventory has been specified
			for (ItemStack item : inventory) {
				returnEntity.getInventory().addItem(item);
			}
		}
		
		
		return returnEntity;
	}
	
}
