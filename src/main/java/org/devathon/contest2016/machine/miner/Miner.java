package org.devathon.contest2016.machine.miner;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
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
		
		while (underneath.getBlock().getType() == Material.AIR) {
			underneath.add(0, -1, 0);
		}
		
		if (underneath.getBlock().getType() == Material.WATER || underneath.getBlock().getType() == Material.LAVA || underneath.getBlock().getType() == Material.STATIONARY_WATER || underneath.getBlock().getType() == Material.STATIONARY_LAVA || underneath.getBlock().getType() == Material.BEDROCK) {
			return;
		}
		
		Material mined = underneath.getBlock().getType();
		byte data = underneath.getBlock().getData();
		underneath.getWorld().playEffect(underneath, Effect.STEP_SOUND, underneath.getBlock().getTypeId());
		underneath.getBlock().setType(Material.AIR);
		if (hasChest(minerLocation)) {
			Chest chest = (Chest) minerLocation.getBlock().getState();
			HashMap<Integer, ItemStack> slotsOpen = new HashMap<Integer, ItemStack>();
            slotsOpen.putAll(chest.getBlockInventory().addItem(new ItemStack(mined, 1, data)));
            if (!slotsOpen.isEmpty()) {
            	minerLocation.getWorld().dropItemNaturally(minerLocation.clone().add(0, 1.165, 0), new ItemStack(mined, 1, data)); // TODO custom drops for ores etc
            }
			return;
		}
		minerLocation.getWorld().dropItemNaturally(minerLocation.clone().add(0, 1.165, 0), new ItemStack(mined, 1, data)); // TODO custom drops for ores etc
	}

	private boolean hasChest(Location minerLocation) {
		return minerLocation.add(0, 1, 0).getBlock().getType() == Material.CHEST;
	}

	@Override
	public boolean canContinue(Location location) {
		Location minerLocation = location.clone();
		Location underneath = minerLocation.clone().add(0, -2, 0);
		
		while (underneath.getBlock().getType() == Material.AIR) {
			underneath.add(0, -1, 0);
		}
		
		if (underneath.getBlock().getType() == Material.WATER || underneath.getBlock().getType() == Material.LAVA || underneath.getBlock().getType() == Material.STATIONARY_WATER || underneath.getBlock().getType() == Material.STATIONARY_LAVA || underneath.getBlock().getType() == Material.BEDROCK) {
			return false;
		}
		
		return true;
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
