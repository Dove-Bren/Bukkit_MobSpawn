package com.SkyIsland.MobSpawn.additions;

import org.bukkit.inventory.ItemStack;

public class ArmorSet {
	
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	private ItemStack sword;
	
	public ArmorSet(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, ItemStack sword){
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		this.sword = sword;
	}

	public ItemStack getHelmet() {
		return helmet;
	}

	public ItemStack getChestplate() {
		return chestplate;
	}

	public ItemStack getLeggings() {
		return leggings;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public ItemStack getSword() {
		return sword;
	}
	
}
