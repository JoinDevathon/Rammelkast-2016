package org.devathon.contest2016.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.devathon.contest2016.machine.MachineManager;
import org.devathon.contest2016.machine.PlacedMachine;
import org.devathon.contest2016.machine.mine.Miner;
import org.devathon.contest2016.machine.mine.Treecutter;

public class MachineTicker implements Runnable {

	private final Map<Integer, Integer> executionForId = new HashMap<>();
	
	@Override
	public void run() {
		for (PlacedMachine machine : MachineManager.getAllMachines()) {
			Location loc = MachineManager.getMachineLocation(machine.getId());
			if (loc.getBlock().getType() != machine.getMachine().getBlock()) { // u w0t m8?
				MachineManager.removeMachine(machine.getId());
				if (executionForId.containsKey(machine.getId())) {
					executionForId.remove(machine.getId());
				}
				return;
			}
			
			if (!loc.getChunk().isLoaded()) { // Everyone hates lag.
				if (executionForId.containsKey(machine.getId())) {
					executionForId.remove(machine.getId());
				}
				return;
			}
			
			if (MachineManager.getMachineFuel(machine.getId()) <= 0) { // We're out of fuel
				if (executionForId.containsKey(machine.getId())) {
					executionForId.remove(machine.getId());
				}
				return;
			}
			
			if (machine.getMachine().canContinue(loc)) {
				if (!executionForId.containsKey(machine.getId())) {
					executionForId.put(machine.getId(), 1);
				} else {
					executionForId.put(machine.getId(), executionForId.get(machine.getId()) + 1);
				}
				machine.getMachine().execute(loc);
				// Had to hardcode this :S
				if (machine.getMachine() instanceof Miner) {
					if (executionForId.get(machine.getId()) >= 10) {
						MachineManager.setMachineFuel(machine.getId(), MachineManager.getMachineFuel(machine.getId()) - 1);
						executionForId.put(machine.getId(), 1);
					}
				} else if (machine.getMachine() instanceof Treecutter) {
					if (executionForId.get(machine.getId()) >= 5) {
						MachineManager.setMachineFuel(machine.getId(), MachineManager.getMachineFuel(machine.getId()) - 1);
						executionForId.put(machine.getId(), 1);
					}
				}
			}
		}
	}

}
