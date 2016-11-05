package org.devathon.contest2016.machine.mine;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.devathon.contest2016.machine.Machine;

public class Miner implements Machine {

	@Override
	public String getName() {
		return ChatColor.RED + "Miner";
	}

	@Override
	public String[] getLore() {
		return new String[] {
			ChatColor.GRAY + "Mines all blocks underneath, until bedrock",
			ChatColor.GRAY + "Uses 1 coal/10 blocks"
		};
	}

	@Override
	public ItemStack getInventoryItem() {
		ItemStack stack = new ItemStack(Material.ANVIL);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(Arrays.asList(getLore()));
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public Material getBlock() {
		return Material.ANVIL;
	}

	@Override
	public void execute(Location location) {
		Location minerLocation = location.clone();
		Location underneath = minerLocation.clone().add(0, -2, 0);
		if (underneath.getBlock().getType() == Material.WATER || underneath.getBlock().getType() == Material.LAVA || underneath.getBlock().getType() == Material.BEDROCK) {
			return;
		}
		
		while (underneath.getBlock().getType() == Material.AIR) {
			underneath.add(0, -1, 0);
		}
		
		Material mined = underneath.getBlock().getType();
		byte data = underneath.getBlock().getData();
		underneath.getBlock().setType(Material.AIR);
		minerLocation.getWorld().dropItemNaturally(minerLocation.clone().add(0, 1.165, 0), new ItemStack(mined, 1, data)); // TODO custom drops for ores etc
	}

	@Override
	public boolean canContinue(Location location) {
		Location minerLocation = location.clone();
		Location underneath = minerLocation.clone().add(0, -2, 0);
		while (underneath.getBlock().getType() == Material.AIR) {
			underneath.add(0, -1, 0);
		}
		return !(underneath.getBlock().getType() == Material.WATER || underneath.getBlock().getType() == Material.LAVA || underneath.getBlock().getType() == Material.BEDROCK) && !(location.getBlock().getType() == Material.WATER || location.getBlock().getType() == Material.LAVA || location.getBlock().getType() == Material.BEDROCK);
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getInventoryItem());
		recipe.shape("BBB", "IRI", "IPI");
		recipe.setIngredient('B', Material.IRON_BLOCK);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('P', Material.DIAMOND_PICKAXE);
		return recipe;
	}

	@Override
	public int getActionsPerFuel() {
		return 10;
	}

}
