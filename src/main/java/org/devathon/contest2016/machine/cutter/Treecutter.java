package org.devathon.contest2016.machine.cutter;

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

public class Treecutter implements Machine {

	@Override
	public String getName() {
		return ChatColor.GREEN + "Treecutter";
	}

	@Override
	public String[] getLore() {
		return new String[] {
			ChatColor.GRAY + "Mines logs above (max. 7 logs)",
			ChatColor.GRAY + "Uses 1 coal/5 logs"
		};
	}

	@Override
	public ItemStack getInventoryItem() {
		ItemStack stack = new ItemStack(Material.PISTON_BASE);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(Arrays.asList(getLore()));
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public Material getBlock() {
		return Material.PISTON_BASE;
	}

	@Override
	public void execute(Location location) {
		Location minerLocation = location.clone();
		Location above = minerLocation.clone().add(0, 2, 0);
		
		while (above.getBlock().getType() == Material.AIR && above.getY() - minerLocation.getY() < 8) {
			above.add(0, 1, 0);
		}
		
		Material mined = above.getBlock().getType();
		Random r = new Random();
		byte data = above.getBlock().getData();
		above.getBlock().setType(Material.AIR);
		Item i = minerLocation.getWorld().dropItemNaturally(minerLocation.clone().add(r.nextDouble(), 0.8, r.nextDouble()), new ItemStack(mined, 1, data));
		i.setVelocity(i.getVelocity().multiply(0.025)); // Prevent items from bouncin' away.
	}

	@Override
	public boolean canContinue(Location location) {
		Location base = location.clone().add(0, 1, 0);
		int i = 0;
		int amt = 0;
		while (i < 7) {
			if (base.add(0, 1, 0).getBlock().getType() == Material.LOG) {
				amt++;
			}
			i++;
		}
		return amt > 0;
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getInventoryItem());
		recipe.shape("WDW", "CPC", "CFC");
		recipe.setIngredient('W', Material.WOOD);
		recipe.setIngredient('D', Material.DIAMOND_AXE);
		recipe.setIngredient('F', Material.FURNACE);
		recipe.setIngredient('C', Material.COBBLESTONE);
		recipe.setIngredient('P', Material.PISTON_BASE);
		return recipe;
	}

	@Override
	public int getActionsPerFuel() {
		return 5;
	}

}
