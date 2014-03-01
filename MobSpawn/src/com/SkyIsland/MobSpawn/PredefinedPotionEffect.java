package com.SkyIsland.MobSpawn;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum PredefinedPotionEffect {
	invisForever (new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, 1, true)), //invisibility designed to be there forever.
	invis5Minutes (new PotionEffect(PotionEffectType.INVISIBILITY, 5*60*5, 1 , true)), //Invisibility, but only for 5 minutes
	strengthII (new PotionEffect(PotionEffectType.INCREASE_DAMAGE , 999999999, 2 , true)), //This is a lot of damage....
	strengthV (new PotionEffect(PotionEffectType.INCREASE_DAMAGE , 999999999, 5 , true)), //crazy damage. What are you doing??
	speedIV (new PotionEffect(PotionEffectType.SPEED,5*50*5, 4, true)), //midget zombie but on any creature
	resistanceII (new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*50*5, 1, true)), //why not just give it more health? This only lasts for 5 minutes. Nice for a special effect
	noMovement (new PotionEffect(PotionEffectType.SLOW, 999999999, 7, true)), //creates a sentinel-effect. Great for towers
	healthAtCreation (new PotionEffect(PotionEffectType.HEALTH_BOOST, 5 * 50 * 2, 4, true)); //temp health boost for 2 minutes upon creation
	
	PotionEffect potionEffect;
	
	PredefinedPotionEffect(PotionEffect effect) {
		this.potionEffect = effect;
	}
	
	
}
