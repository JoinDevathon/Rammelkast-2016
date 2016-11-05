package org.devathon.contest2016;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.MachineManager;
import org.devathon.contest2016.machine.PlacedMachine;

public class EventListener implements Listener {
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlaceBlock(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (e.getItemInHand() == null || !e.getItemInHand().hasItemMeta())
			return;
		Machine m = MachineManager.getMachineByName(e.getItemInHand().getItemMeta().getDisplayName());
		if (m == null)
			return;
		if (MachineManager.placeMachine(m, e.getBlock().getLocation())) {
			p.sendMessage(ChatColor.GRAY + "Machine " + m.getName() + ChatColor.GRAY + " placed!");
		} else {
			e.setBuild(false);
			e.setCancelled(true);
			p.sendMessage(ChatColor.GRAY + "Machine could not be placed!");
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onBreakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		PlacedMachine machine = MachineManager.getMachineAtLocation(e.getBlock().getLocation());
		if (machine == null) {
			return;
		}
		MachineManager.removeMachine(machine.getId());
		p.sendMessage(ChatColor.GRAY + "Machine removed!");
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInteractBlock(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null) {
			return;
		}
		PlacedMachine machine = MachineManager.getMachineAtLocation(e.getClickedBlock().getLocation());
		if (machine == null) {
			return;
		}
		e.setCancelled(true);
		e.getPlayer().openInventory(Bukkit.createInventory(null, 9, ChatColor.RED + "Machine fuel manager " + ChatColor.GRAY + "[" + machine.getId() + "]"));
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onInteractBlock(InventoryCloseEvent e) {
		if (!e.getInventory().getName().startsWith(ChatColor.RED + "Machine fuel manager " + ChatColor.GRAY + "[")) {
			return;
		}
		Pattern p = Pattern.compile("\\[.*?\\]");
		Matcher m = p.matcher(e.getInventory().getName());
		if (!m.find()) {
			return;
		}
		int machineId = Integer.parseInt(m.group().substring(1, m.group().length() - 1));
		int amountOfCoal = Utils.calculateCoal(e.getInventory());
		MachineManager.setMachineFuel(machineId, MachineManager.getMachineFuel(machineId) + amountOfCoal);
	}
	
}
