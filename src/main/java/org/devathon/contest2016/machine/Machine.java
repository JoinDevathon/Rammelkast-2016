package org.devathon.contest2016.machine;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface Machine {

	public String getName();
	
	public String[] getLore();
	
	public ItemStack getInventoryItem();
	
	public Material getBlock();
	
	public void execute(Location location);
	
	public boolean canContinue(Location location);
	
	public Recipe getRecipe();
	
}
