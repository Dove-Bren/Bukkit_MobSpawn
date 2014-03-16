package com.SkyIsland.MobSpawn;

import java.util.Collection;
import java.util.TreeSet;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum CustomPotionEffect {
	invisForever (new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, 1, true)), //Invisibility designed to be there forever.
	invis5Minutes (new PotionEffect(PotionEffectType.INVISIBILITY, 5*60*5, 1 , true)), //Invisibility, but only for 5 minutes
	strengthII (new PotionEffect(PotionEffectType.INCREASE_DAMAGE , 999999999, 2 , true)), //This is a lot of damage....
	strengthV (new PotionEffect(PotionEffectType.INCREASE_DAMAGE , 999999999, 5 , true)), //crazy damage. What are you doing??
	speedIV (new PotionEffect(PotionEffectType.SPEED,5*50*5, 4, true)), //midget zombie but on any creature
	resistanceII (new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*50*5, 1, true)), //why not just give it more health? This only lasts for 5 minutes. Nice for a special effect
	noMovement (new PotionEffect(PotionEffectType.SLOW, 999999999, 7, true)), //creates a sentinel-effect. Great for towers
	healthAtCreation (new PotionEffect(PotionEffectType.HEALTH_BOOST, 5 * 50 * 2, 4, true)), //temp health boost for 2 minutes upon creation
	weaknessII (new PotionEffect(PotionEffectType.WEAKNESS, 999999999, 2, true)),
	weaknessV (new PotionEffect(PotionEffectType.WEAKNESS, 999999999, 5, true)),
	poisonI (new PotionEffect(PotionEffectType.POISON, 999999999, 1, true)),
	poisonII (new PotionEffect(PotionEffectType.POISON, 999999999, 2, true)),
	poisonIII (new PotionEffect(PotionEffectType.POISON, 999999999, 3, true)),
	regenI (new PotionEffect(PotionEffectType.REGENERATION, 999999999, 1, true)),
	regenII (new PotionEffect(PotionEffectType.REGENERATION, 999999999, 2, true)),
	regenIII (new PotionEffect(PotionEffectType.REGENERATION, 999999999, 3, true));
	
	//weakness
	//poison
	//regeneration
	
	private Collection<PotionEffect> potionEffects;
	
	CustomPotionEffect(PotionEffect... effects) {
		this.potionEffects = new TreeSet<PotionEffect>();
		
		for (PotionEffect effect: effects){
			potionEffects.add(effect);
		}
	}
	
	public Collection<PotionEffect> getPotionEffects(){
		return potionEffects;
	}
	
	public void applyPotionEffects(LivingEntity entity){
		entity.addPotionEffects(potionEffects);
	}
	
	
}
