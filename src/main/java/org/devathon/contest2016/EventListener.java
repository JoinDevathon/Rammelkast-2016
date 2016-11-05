package org.devathon.contest2016;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener {
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlaceBlock(BlockPlaceEvent e) {
		
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onBreakBlock(BlockBreakEvent e) {
		
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInteractBlock(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK) {
			return;
		}
	}
	
}
