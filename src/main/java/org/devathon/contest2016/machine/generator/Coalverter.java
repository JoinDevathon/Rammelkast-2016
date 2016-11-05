package org.devathon.contest2016.machine.generator;

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

public class Coalverter implements Machine {

	@Override
	public String getName() {
		return ChatColor.DARK_GRAY + "Coalverter";
	}

	@Override
	public String[] getLore() {
		return new String[] {
			ChatColor.GRAY + "Converts 1 coal into 2 charcoal",
			ChatColor.GRAY + "Uses 1 coal/2 charcoal"
		};
	}

	@Override
	public ItemStack getInventoryItem() {
		ItemStack stack = new ItemStack(Material.FURNACE);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(Arrays.asList(getLore()));
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public Material getBlock() {
		return Material.FURNACE;
	}

	@Override
	public void execute(Location location) {
		Item i = location.getWorld().dropItemNaturally(location.clone().add(0, 1.15, 0), new ItemStack(Material.COAL, 1, (byte) 1));
		i.setVelocity(i.getVelocity().multiply(0.025)); // Prevent items from bouncin' away.
	}

	@Override
	public boolean canContinue(Location location) {
		return true;
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getInventoryItem());
		recipe.shape("CHC", "FDF", "CHC");
		recipe.setIngredient('C', Material.COBBLESTONE);
		recipe.setIngredient('H', Material.HOPPER);
		recipe.setIngredient('F', Material.FURNACE);
		recipe.setIngredient('D', Material.DIAMOND);
		return recipe;
	}

	@Override
	public int getActionsPerFuel() {
		return 2;
	}

}
