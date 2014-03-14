package com.SkyIsland.MobSpawn;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import com.SkyIsland.MobSpawn.mobs.PredefinedMob;

public final class MobConfigProcessor {
	
	final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Entity helper;
	
	/**
	 * This method determines how a new entity(ies) will be generated
	 * @param Name The name of the entity
	 * @param Command Various attributes associated with the specified mob type
	 * @param Loc The location to generate the mob
	 */
	public static void SpawnMob (String Name, String Command, Location Loc, MobSpawn plugin) {
		StringTokenizer stringToken = new StringTokenizer(Command, " ");
		String tokenString = stringToken.nextToken();
		//Check for type of mob: Simple, Predefined, Complex
		if (tokenString.trim().compareTo("simple") == 0) {
			helper =  SimpleMobType.CustomEntity(EntityType.valueOf(Name.toUpperCase()), Loc);
			if (helper.getType().compareTo(EntityType.SKELETON) == 0) {
				((LivingEntity) helper).getEquipment().setItemInHand(InventoryCreator.RangedWeapon((int) (2 + Math.random() * 3)));
			}
		}
		if (tokenString.trim().compareTo("predefined") == 0) {
			PredefinedMob.spawnLocalized(PredefinedMob.valueOf(Name), Loc);	
		}
		if (tokenString.trim().compareTo("double") == 0) {
			//Determine how complex types will be generated
			tokenString = stringToken.nextToken();
			tokenString.trim();
			tokenString.toUpperCase();
			String Vehicle = tokenString;
			if (!stringToken.hasMoreTokens()) {
				logger.info("Incorrect yml feed!");
				return;
			}
			tokenString = stringToken.nextToken();
			tokenString.trim();
			tokenString.toUpperCase();
			String Passenger = tokenString;
			if (!stringToken.hasMoreTokens())
				ComplexMobType.CustomEntity(EntityType.valueOf(Vehicle), EntityType.valueOf(Passenger), Loc, InventoryCreator.RangedWeapon((int) (2 + Math.random() * 3)));
			else {
				tokenString = stringToken.nextToken();
				ComplexMobType.CustomEntity(EntityType.valueOf(Vehicle), EntityType.valueOf(Passenger), Loc, tokenString);
			}
		}
		if (tokenString.trim().compareToIgnoreCase("complex") == 0) {
			createFromDefinition(plugin, Name, Loc);
		}
	}
	
	
	//type stores the name of the mob as a string
	public static void createFromDefinition(MobSpawn plugin, String mobType, Location loc) {
		ConfigurationSection memSection = plugin.mobTable.getConfigurationSection("Definitions." + mobType);
		String buffer;
		
		int numberOfMobs = memSection.getInt("numberOfPieces", 1), i;
		LivingEntity mob = null, mob2 = null;
		boolean isBoss = memSection.getBoolean("isBoss", false);
		
		for (i = 1; i <= numberOfMobs && memSection.contains("entity" + i); i++) {
			
			mob2 = mob;
			mob =  (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.valueOf(memSection.getString("entity" + i)));
			
			if (i > 1) {
				mob2.setPassenger(mob);
			}
			
				//if it's a boss, we don't want it to despawn
			mob.setRemoveWhenFarAway(!isBoss);
			
			//set it's name, if it's not 'none'
			buffer = memSection.getString("entity" + i + "Name", "none");
			if (!buffer.trim().equalsIgnoreCase("none")) {
				mob.setCustomName(buffer);
			}
			
			//get how much hp it has. if for whatever reason we can't get it, we assume 6
			mob.setMaxHealth(memSection.getDouble("entity" + i + "Hp", 6));
			
			//load up what potion effect is has, as long as it's not none.
			buffer = memSection.getString("entity" + i + "PotionEffect", "none");
			if (buffer.trim().compareToIgnoreCase("none") != 0) {
				try { 
				  PredefinedPotionEffect.valueOf(buffer).potionEffect.apply(mob);
				}
				catch (IllegalArgumentException e) {
					plugin.getLogger().info("Invalid type of potion effect on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Potion effect must be one of the effects supplied in PredefinedPotionEffect.java!");
					mob.remove();
					return;
				}
				catch (NullPointerException e) {
	
					plugin.getLogger().info("Invalid type of potion effect on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Parser is reading a null!");
					mob.remove();
					return;
				}
			}
			
			//all we need to do now is the equipment
			buffer = memSection.getString("entity" + i + "Equips", "none none none none none");
			if (!buffer.trim().equalsIgnoreCase("none none none none none")) {
				//heads chest legs boots inHand
				StringTokenizer parser = new StringTokenizer(buffer, " ");
				String miniBuffer;
				
				//first part of the string is what equip goes on the head
				miniBuffer = parser.nextToken();
				if (!miniBuffer.trim().equalsIgnoreCase("none")) {
					try { 
						mob.getEquipment().setHelmet(new ItemStack(Material.valueOf(miniBuffer)));
					}
					catch (IllegalArgumentException e) {
						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Material must be a valid value of the Material Enumerated type");
						mob.remove();
						return;
					}
					catch (NullPointerException e) {

						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Parser is reading a null!");
						mob.remove();
						return;
					}
				}
				
				//next is the chestplate
				//first part of the string is what equip goes on the head
				miniBuffer = parser.nextToken();
				if (!miniBuffer.trim().equalsIgnoreCase("none")) {
					try { 
						mob.getEquipment().setChestplate(new ItemStack(Material.valueOf(miniBuffer)));
					}
					catch (IllegalArgumentException e) {
						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Material must be a valid value of the Material Enumerated type");
						mob.remove();
						return;
					}
					catch (NullPointerException e) {

						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Parser is reading a null!");
						mob.remove();
						return;
					}
				}
				
				//next part is the leggings
				//first part of the string is what equip goes on the head
				miniBuffer = parser.nextToken();
				if (!miniBuffer.trim().equalsIgnoreCase("none")) {
					try { 
						mob.getEquipment().setLeggings(new ItemStack(Material.valueOf(miniBuffer)));
					}
					catch (IllegalArgumentException e) {
						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Material must be a valid value of the Material Enumerated type");
						mob.remove();
						return;
					}
					catch (NullPointerException e) {

						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Parser is reading a null!");
						mob.remove();
						return;
					}
				}
				
				//fourth part is boots
				//first part of the string is what equip goes on the head
				miniBuffer = parser.nextToken();
				if (!miniBuffer.trim().equalsIgnoreCase("none")) {
					try { 
						mob.getEquipment().setBoots(new ItemStack(Material.valueOf(miniBuffer)));
					}
					catch (IllegalArgumentException e) {
						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Material must be a valid value of the Material Enumerated type");
						mob.remove();
						return;
					}
					catch (NullPointerException e) {

						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Parser is reading a null!");
						mob.remove();
						return;
					}
				}	
				
				//last is what's in hand
				//first part of the string is what equip goes on the head
				miniBuffer = parser.nextToken();
				if (!miniBuffer.trim().equalsIgnoreCase("none")) {
					try { 
						mob.getEquipment().setItemInHand(new ItemStack(Material.valueOf(miniBuffer)));
					}
					catch (IllegalArgumentException e) {
						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Material must be a valid value of the Material Enumerated type");
						mob.remove();
						return;
					}
					catch (NullPointerException e) {

						plugin.getLogger().info("Invalid type of material on mob type [" + mobType + "]" + System.getProperty("line.separator") + "Parser is reading a null!");
						mob.remove();
						return;
					}
				}
			}			
		}
	}
}

