package org.devathon.contest2016.machine;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface Machine {

	/**
	 * @return the machine's name
	 */
	public String getName();
	
	/**
	 * @return the machine's lore
	 */
	public String[] getLore();
	
	/**
	 * @return the machine's inventory item
	 */
	public ItemStack getInventoryItem();
	
	/**
	 * @return the machine's block
	 */
	public Material getBlock();
	
	/**
	 * What to do on execute?
	 */
	public void execute(Location location);
	
	/**
	 * Should the MachineTicker execute this machine's action?
	 */
	public boolean canContinue(Location location);
	
	/**
	 * @return the machine's recipe
	 */
	public Recipe getRecipe();
	
	/**
	 * @return the amount of times execute() can be called before using 1 fuel (coal)
	 */
	public int getActionsPerFuel();
	
}
