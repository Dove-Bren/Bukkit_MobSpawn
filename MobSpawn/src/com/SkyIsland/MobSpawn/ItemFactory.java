package com.SkyIsland.MobSpawn;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemFactory {
		
	/**
	 * Creates a generic stone sword
	 * @return The item
	 */
	public static ItemStack MeleeWeapon() {
		return new ItemStack(Material.STONE_SWORD);
	}
	
	/**
	 * Creates a sword of a certain material (by difficulty)
	 * @param level Higher levels of 0-3 relate to different material types
	 * @return The item
	 */
	public static ItemStack MeleeWeapon (int level) {
		switch (level) {
		case 0:
			return new ItemStack(Material.WOOD_SWORD);
		case 1:
			return new ItemStack(Material.STONE_SWORD);
		case 2:
			return new ItemStack(Material.IRON_SWORD);
		case 3:
			return new ItemStack(Material.DIAMOND_SWORD);
		default:
			return new ItemStack(Material.STONE_SWORD);
		}
	}
	
	/**
	 * Creates a sword of a certain material with randomly generated enchantments based upon level
	 * @param level Higher levels of 0-3 relate to different material types
	 * @param enchantmentLVL Higher levels (0-4) mean higher probabilities of higher enchantments
	 * @return The item
	 */
	public static ItemStack MeleeWeapon (int level, int enchantmentLVL) {
		ItemStack newItemStack;
		int randomChance;
		//Create Item
		switch (level) {
		case 0:
			newItemStack = new ItemStack(Material.WOOD_SWORD);
			break;
		case 1:
			newItemStack = new ItemStack(Material.STONE_SWORD);
			break;
		case 2:
			newItemStack = new ItemStack(Material.IRON_SWORD);
			break;
		case 3:
			newItemStack = new ItemStack(Material.DIAMOND_SWORD);
		default:
			newItemStack = new ItemStack(Material.STONE_SWORD);
		}
		//Create Enchantments
		switch (enchantmentLVL) {
		case 0:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.KNOCKBACK, 1);
			break;
		case 1:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.DAMAGE_ALL, 2);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.KNOCKBACK, 2);
			break;
		case 2:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.DAMAGE_ALL, 3);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.KNOCKBACK, 2);
			break;
		case 3:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.DAMAGE_ALL, 4);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.KNOCKBACK, 2);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.FIRE_ASPECT, 1);
			break;
		case 4:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.KNOCKBACK, 2);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.FIRE_ASPECT, 1);
			break;
		}
		return newItemStack;
	}
	
	/**
	 * Creates a bow
	 * @return The item
	 */
	public static ItemStack RangedWeapon() {
		return new ItemStack(Material.STONE_SWORD);
	}
	
	/**
	 * Generates a Ranged weapon with an randomized enchantment
	 * @param enchantmentLVL Higher levels (0-3) mean higher probabilities of higher enchantments
	 * @return The item
	 */
	public static ItemStack RangedWeapon(int enchantmentLVL) {
		ItemStack newItemStack = new ItemStack(Material.BOW);
		int randomChance;
		switch (enchantmentLVL) {
		case 0:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
			break;
		case 1:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
			break;
		case 2:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
			break;
		case 3:
			randomChance = (int) Math.random();
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
			if (randomChance % 2 == 0)
				newItemStack.addEnchantment(Enchantment.ARROW_FIRE,	1);
			break;
		}
		return newItemStack;
	}
}
