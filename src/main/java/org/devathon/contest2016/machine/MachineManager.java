package org.devathon.contest2016.machine;

import java.util.ArrayList;
import java.util.List;

import org.devathon.contest2016.machine.mine.Miner;

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
	}
	
}
