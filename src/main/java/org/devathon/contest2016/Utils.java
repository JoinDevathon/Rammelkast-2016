package org.devathon.contest2016;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Utils {

	public static int calculateCoal(Inventory inventory) {
		int coal = 0;
		for (ItemStack i : inventory.getContents()) {
			if (i != null && i.getType() != null) {
				if (i.getType() == Material.COAL) {
					coal += i.getAmount();
					inventory.remove(i);
				}
			}
		}
		return coal;
	}

}
