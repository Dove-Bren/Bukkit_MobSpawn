package com.SkyIsland.MobSpawn;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class spawnTaskTest {

	@Test
	public void testSpawnTask() {
		File file;
		int i;
		Scanner input = new Scanner(System.in);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String current;
		
		file = new File("MobIdLookupTable.yml");
		
		YamlConfiguration configFile = new YamlConfiguration();
		try {
			configFile.load(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (i = 0; i < 1000; i++) {
			current = spawnTask.getMob(configFile);
			if (map.get(current) == null) {
				map.put(current, new Integer(1));
			}
			else {
				map.put(current, new Integer(map.get(current).intValue() + 1));
			}
		}
		
		
		
		for (String key : map.keySet()) {
			System.out.println(key + ": " + map.get(key).doubleValue() / 10 + "%");
		}
		
	}

}
