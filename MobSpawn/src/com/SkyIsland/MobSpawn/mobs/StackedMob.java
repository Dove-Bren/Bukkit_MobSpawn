package com.SkyIsland.MobSpawn.mobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class StackedMob implements CustomMob{

	private SimpleMob mob;
	private List<SimpleMob> stackedMobs;
	
	public StackedMob(SimpleMob mob, SimpleMob... otherMobs) {
		this.mob = mob;
		stackedMobs = new ArrayList<SimpleMob>();
		
		for (SimpleMob otherMob: otherMobs){
			stackedMobs.add(otherMob);
		}
	}
	
	public StackedMob(SimpleMob mob, Collection<SimpleMob> otherMobs) {
		this.mob = mob;
		stackedMobs = new ArrayList<SimpleMob>();
		
		for (SimpleMob otherMob: otherMobs){
			stackedMobs.add(otherMob);
		}
	}
	
	public LivingEntity spawnMob(Location location){
		LivingEntity bottom = mob.spawnMob(location);
		
		//start at the bottom
		LivingEntity current = bottom;
		
		//stack the mobs
		for (SimpleMob stackedMob: stackedMobs){
			LivingEntity otherMob = stackedMob.spawnMob(location);
			current.setPassenger(otherMob);
			current = otherMob;
		}
		
		//return the bottom most mob
		return bottom;
	}
	
	public boolean canSpawn(Location location) {
		int h = 0;
		//because we're given one location, and each mob doesn't store their own height, we have to do some magic
		//h will store how many mobs we've checked. We can assume each mob stacked increases the height of the 
		//stack by 1 block
		
		for (SimpleMob myMob : stackedMobs) {
			if (!myMob.canSpawn(location.add(0, h, 0))) {
				return false;
			}
			h++;
		}
		
		//got through checking all of the mobs in mob list. 
		return true;
	}

}
