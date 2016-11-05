package org.devathon.contest2016.machine.weapons;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.devathon.contest2016.machine.Machine;

public class Cannon implements Machine {

	@Override
	public String getName() {
		return ChatColor.DARK_RED + "Cannon";
	}

	@Override
	public String[] getLore() {
		return new String[] {
			ChatColor.GRAY + "Shoots fireballs!",
			ChatColor.GRAY + "Uses 1 coal/fireball",
			ChatColor.RED + "Warning! Always fires south!"
		};
	}

	@Override
	public ItemStack getInventoryItem() {
		ItemStack stack = new ItemStack(Material.DROPPER);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(Arrays.asList(getLore()));
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public Material getBlock() {
		return Material.DROPPER;
	}

	@Override
	public void execute(Location location) {
		Fireball fb = (Fireball) location.getWorld().spawnEntity(location.clone().add(0, 1.75, 0), EntityType.FIREBALL);
		fb.setDirection(location.getDirection());
	}

	@Override
	public boolean canContinue(Location location) {
		return true;
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getInventoryItem());
		recipe.shape("CBC", "CFC", "CLC");
		recipe.setIngredient('C', Material.COBBLESTONE);
		recipe.setIngredient('F', Material.FURNACE);
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('L', Material.LAVA_BUCKET);
		return recipe;
	}

	@Override
	public int getActionsPerFuel() {
		return 1;
	}

}
