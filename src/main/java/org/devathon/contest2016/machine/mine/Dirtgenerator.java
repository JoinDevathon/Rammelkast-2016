package org.devathon.contest2016.machine.mine;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.devathon.contest2016.machine.Machine;

public class Dirtgenerator implements Machine {

	@Override
	public String getName() {
		return ChatColor.GOLD + "Dirt generator";
	}

	@Override
	public String[] getLore() {
		return new String[] {
			ChatColor.GRAY + "Generates dirt",
			ChatColor.GRAY + "Uses 1 coal/1 dirt"
		};
	}

	@Override
	public ItemStack getInventoryItem() {
		ItemStack stack = new ItemStack(Material.DISPENSER);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(Arrays.asList(getLore()));
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public Material getBlock() {
		return Material.DISPENSER;
	}

	@Override
	public void execute(Location location) {
		Item i = location.getWorld().dropItemNaturally(location.clone().add(0, 1.15, 0), new ItemStack(Material.DIRT));
		i.setVelocity(i.getVelocity().multiply(0.025)); // Prevent items from bouncin' away.
	}

	@Override
	public boolean canContinue(Location location) {
		return true;
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getInventoryItem());
		recipe.shape("CHC", "CFC", "CDC");
		recipe.setIngredient('C', Material.COBBLESTONE);
		recipe.setIngredient('H', Material.HOPPER);
		recipe.setIngredient('F', Material.FURNACE);
		recipe.setIngredient('D', Material.DIRT);
		return recipe;
	}

	@Override
	public int getActionsPerFuel() {
		return 1;
	}

}
