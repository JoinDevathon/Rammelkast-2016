package org.devathon.contest2016.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.mine.Dirtgenerator;
import org.devathon.contest2016.machine.mine.Miner;
import org.devathon.contest2016.machine.mine.Treecutter;

public class MachineManager {

	public static final List<Machine> MACHINES = new ArrayList<Machine>();
	
	static {
		/*
		 * Miner - mines blocks underneath untill bedrock
		 * Uses 1 coal/10 blocks
		 * 
		 * Recipe:
		 * B = iron block
		 * I = iron ingot
		 * R = redstone
		 * P = diamond pickaxe
		 * |B B B|
		 * |I R I|
		 * |I P I|
		 */
		MACHINES.add(new Miner());
		/*
		 * Dirt generator - generates dirt
		 * Uses 1 coal/dirt
		 * 
		 * Recipe:
		 * H = hopper
		 * C = cobble
		 * D = dirt
		 * F = furnace
		 * |C H C|
		 * |C F C|
		 * |C D C|
		 */
		MACHINES.add(new Dirtgenerator());
		/*
		 * Treecutter - cuts tress above (max. 7 blocks)
		 * Uses 1 coal/5 logs
		 * 
		 * Recipe:
		 * W = wood
		 * C = cobble
		 * A = diamond axe
		 * P = piston
		 * F = furnace
		 * |W A W|
		 * |C P C|
		 * |C F C|
		 */
		MACHINES.add(new Treecutter());
	}
	
	/**
	 * Register a custom machine
	 * @param machine
	 */
	public static void registerMachine(Machine machine) {
		MACHINES.add(machine);
	}
	
	public static Machine getMachineByClassname(String className) {
		for (Machine m : MACHINES) {
			if (m.getClass().getSimpleName().equals(className)) {
				return m;
			}
		}
		return null;
	}
	
	public static Machine getMachineByName(String name) {
		for (Machine m : MACHINES) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}

	public static boolean placeMachine(Machine m, Location location) {
		try {
			World world = location.getWorld();
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();
			FileConfiguration config = DevathonPlugin.getInstance().getFileConfiguration();
			if (config.getConfigurationSection("machines") == null) {
				config.createSection("machines");
			}
			ConfigurationSection machines = config.getConfigurationSection("machines");
			Map<String, Object> machineList = machines.getValues(false);
			Set<String> machineIds = machineList.keySet();
			int lastId = ((machineIds == null || machineIds.isEmpty()) ? -1 : Integer.parseInt(machineIds.toArray(new String[machineIds.size()])[machineIds.size() - 1])) + 1;
			ConfigurationSection subSection = machines.createSection(String.valueOf(lastId));
			subSection.set("world", world.getName());
			subSection.set("x", x);
			subSection.set("y", y);
			subSection.set("z", z);
			subSection.set("fuel", 0);
			subSection.set("type", m.getClass().getSimpleName());
			DevathonPlugin.getInstance().saveFileConfig();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static PlacedMachine[] getAllMachines() {
		FileConfiguration config = DevathonPlugin.getInstance().getFileConfiguration();
		if (config.getConfigurationSection("machines") == null) {
			config.createSection("machines");
		}
		ConfigurationSection machines = config.getConfigurationSection("machines");
		Map<String, Object> machineList = machines.getValues(false);
		List<PlacedMachine> placedMachines = new ArrayList<>();
		for (String s : machineList.keySet()) {
			ConfigurationSection subSection = machines.getConfigurationSection(s);
			placedMachines.add(new PlacedMachine(getMachineByClassname(subSection.getString("type")), Integer.parseInt(s)));
		}
		return placedMachines.toArray(new PlacedMachine[placedMachines.size()]);
	}
	
	public static PlacedMachine getMachineAtLocation(Location location) {
		World world = location.getWorld();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		FileConfiguration config = DevathonPlugin.getInstance().getFileConfiguration();
		if (config.getConfigurationSection("machines") == null) {
			config.createSection("machines");
		}
		ConfigurationSection machines = config.getConfigurationSection("machines");
		Map<String, Object> machineList = machines.getValues(false);
		for (Object machineData : machineList.values()) {
			MemorySection sec = (MemorySection)machineData;
			if (sec.getString("world").equals(world.getName()) && sec.getInt("x") == x && sec.getInt("y") == y && sec.getInt("z") == z) {
				int id = -1;
				for (String s : machineList.keySet()) {
					if (machineList.get(s).equals(machineData)) {
						id = Integer.parseInt(s);
					}
				}
				return new PlacedMachine(getMachineByClassname(sec.getString("type")), id);
			}
		}
		return null;
	}
	
	public static void setMachineFuel(int id, int fuel) {
		FileConfiguration config = DevathonPlugin.getInstance().getFileConfiguration();
		if (config.getConfigurationSection("machines") == null) {
			config.createSection("machines");
		}
		ConfigurationSection machines = config.getConfigurationSection("machines");
		ConfigurationSection subSection = machines.getConfigurationSection(id + "");
		subSection.set("fuel", fuel);
		DevathonPlugin.getInstance().saveFileConfig();
	}
	
	public static int getMachineFuel(int id) {
		FileConfiguration config = DevathonPlugin.getInstance().getFileConfiguration();
		if (config.getConfigurationSection("machines") == null) {
			config.createSection("machines");
		}
		ConfigurationSection machines = config.getConfigurationSection("machines");
		ConfigurationSection subSection = machines.getConfigurationSection(id + "");
		return subSection.getInt("fuel");
	}
	
	public static Location getMachineLocation(int id) {
		FileConfiguration config = DevathonPlugin.getInstance().getFileConfiguration();
		if (config.getConfigurationSection("machines") == null) {
			config.createSection("machines");
		}
		ConfigurationSection machines = config.getConfigurationSection("machines");
		ConfigurationSection subSection = machines.getConfigurationSection(id + "");
		return new Location(Bukkit.getWorld(subSection.getString("world")), subSection.getInt("x"),  subSection.getInt("y"),  subSection.getInt("z"));
	}
	
	public static void removeMachine(int id) {
		FileConfiguration config = DevathonPlugin.getInstance().getFileConfiguration();
		if (config.getConfigurationSection("machines") == null) {
			config.createSection("machines");
		}
		ConfigurationSection machines = config.getConfigurationSection("machines");
		machines.set(id + "", null);
		DevathonPlugin.getInstance().saveFileConfig();
	}
	
}
