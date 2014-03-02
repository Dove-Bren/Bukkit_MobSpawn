package com.SkyIsland.MobSpawn;

import java.util.StringTokenizer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import com.avaje.ebean.text.StringParser;

public final class complexParser {
	
	
	//type stores the name of the mob as a string
	public static void createFromDefinition(MobSpawn plugin, String mobType, Location loc) {
		ConfigurationSection memSection = plugin.mobIdLookup.getConfigurationSection("Definitions." + mobType);
		String buffer;
		
		int numberOfMobs = memSection.getInt("numberOfPieces", 1), i;
		LivingEntity mob;
		boolean isBoss = memSection.getBoolean("isBoss", false);
		
		for (i = 1; i <= numberOfMobs && memSection.contains("entity" + i); i++) {
			mob = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.valueOf(memSection.getString("entity" + i)));
			
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
				  PredefinedPotionEffect.valueOf(buffer).potionEffect.apply(mob); }
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
						mob.getEquipment().setHelmet(new ItemStack(Material.valueOf(miniBuffer))); }
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
						mob.getEquipment().setChestplate(new ItemStack(Material.valueOf(miniBuffer))); }
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
						mob.getEquipment().setLeggings(new ItemStack(Material.valueOf(miniBuffer))); }
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
						mob.getEquipment().setBoots(new ItemStack(Material.valueOf(miniBuffer))); }
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
						mob.getEquipment().setItemInHand(new ItemStack(Material.valueOf(miniBuffer))); }
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
