package org.devathon.contest2016.machine;

public class PlacedMachine {

	private final Machine machine;
	private final int id;
	
	public PlacedMachine(Machine machine, int id) {
		this.machine = machine;
		this.id = id;
	}

	public Machine getMachine() {
		return machine;
	}

	public int getId() {
		return id;
	}
	
}
