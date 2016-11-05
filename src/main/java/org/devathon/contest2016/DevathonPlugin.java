package org.devathon.contest2016;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.MachineManager;

/**
 * Made for Devathon 2016 | Theme is Machines...
 * @author Marco
 *
 */
public class DevathonPlugin extends JavaPlugin {

	private static DevathonPlugin instance;
	
	private FileConfiguration fileConfiguration;
	
    @Override
    public void onEnable() {
    	instance = this;
    	
    	// Create plugin folder is doesn't exist
    	if (!this.getDataFolder().exists()) {
    		this.getDataFolder().mkdir();
    	}
    	
    	// Create config if doesn't exist
    	if (!new File(this.getDataFolder(), "config.yml").exists()) {
    		this.saveResource("config.yml", true);
    	}
    	
    	// Load config
    	this.fileConfiguration = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml"));
    	
    	// Register events
    	Bukkit.getPluginManager().registerEvents(new EventListener(), this);
    	
    	/*
    	 * Register recipes
    	 */
    	for (Machine m : MachineManager.MACHINES) {
    		getServer().addRecipe(m.getRecipe());
    	}
    }

    @Override
    public void onDisable() {
    	// Save config
    	try {
			this.fileConfiguration.save(new File(this.getDataFolder(), "config.yml"));
		} catch (IOException e) {
			getLogger().severe("Could not save config, some machines might not be registered!");
		}
    }

	public static DevathonPlugin getInstance() {
		return instance;
	}

	public FileConfiguration getFileConfiguration() {
		return fileConfiguration;
	}
}

