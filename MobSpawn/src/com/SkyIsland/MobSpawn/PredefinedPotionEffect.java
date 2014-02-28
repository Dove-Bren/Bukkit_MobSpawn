package com.SkyIsland.MobSpawn;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum PredefinedPotionEffect {
	invisForever (new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, 1, true)),
	invis5Minutes (new PotionEffect(PotionEffectType.INVISIBILITY, 5*60*5, 1 , true)),
	strengthII (new PotionEffect(PotionEffectType.INCREASE_DAMAGE , 999999999, 2 , true)),
	strengthV (new PotionEffect(PotionEffectType.INCREASE_DAMAGE , 999999999, 5 , true));
	
	PotionEffect potionEffect;
	
	PredefinedPotionEffect(PotionEffect effect) {
		this.potionEffect = effect;
	}
	
	
}
