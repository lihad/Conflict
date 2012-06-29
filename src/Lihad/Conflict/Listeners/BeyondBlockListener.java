package Lihad.Conflict.Listeners;


import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import Lihad.Conflict.Conflict;

public class BeyondBlockListener implements Listener {
	public static Conflict plugin;
	public BeyondBlockListener(Conflict instance) {
		plugin = instance;
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
		if(event.getBlock().getWorld().getName().equals("survival")){
            if (((!Conflict.Abatton.getMayors().contains(player.getName()) && Conflict.Abatton.getLocation().distance(event.getBlock().getLocation()) < Conflict.Abatton.getProtectionRadius())
					|| (!Conflict.Oceian.getMayors().contains(player.getName()) && Conflict.Oceian.getLocation().distance(event.getBlock().getLocation()) < Conflict.Oceian.getProtectionRadius())
					|| (!Conflict.Savania.getMayors().contains(player.getName()) && Conflict.Savania.getLocation().distance(event.getBlock().getLocation()) < Conflict.Savania.getProtectionRadius())
			)&& !player.isOp() && !Conflict.handler.has(player, "conflict.debug")){
				event.setCancelled(true);
			}
			if(!player.isOp() && !Conflict.handler.has(player, "conflict.debug") && (
                   (Conflict.Blacksmith     .getNode().isInRadius(event.getBlock().getLocation()))
                || (Conflict.MystPortal     .getNode().isInRadius(event.getBlock().getLocation()))
                || (Conflict.Enchantments   .getNode().isInRadius(event.getBlock().getLocation()))
                || (Conflict.RichPortal     .getNode().isInRadius(event.getBlock().getLocation()))
                || (Conflict.Potions        .getNode().isInRadius(event.getBlock().getLocation())))
			){
				event.setCancelled(true);
			}
		}
		else if(event.getBlock().getWorld().getName().equals("richworld")){
			if( !Conflict.PlayerCanUseTrade(player.getName(), "richportal") && !player.isOp()) {
				event.setCancelled(true);
				player.kickPlayer("Invalid Block Break by Unauthorized Player in Rich World");
			}
				
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
		if(event.getBlock().getWorld().getName().equals("survival")){
            if(((!Conflict.Abatton.getMayors().contains(player.getName()) && Conflict.Abatton.getLocation().distance(event.getBlock().getLocation()) < Conflict.Abatton.getProtectionRadius())
					|| (!Conflict.Oceian.getMayors().contains(player.getName()) && Conflict.Oceian.getLocation().distance(event.getBlock().getLocation()) < Conflict.Oceian.getProtectionRadius())
					|| (!Conflict.Savania.getMayors().contains(player.getName()) && Conflict.Savania.getLocation().distance(event.getBlock().getLocation()) < Conflict.Savania.getProtectionRadius()))
					&& !player.isOp() && !Conflict.handler.has(player, "conflict.debug")){
				event.setCancelled(true);
			}
			if(!player.isOp() && !Conflict.handler.has(player, "conflict.debug") && (
                   (Conflict.Blacksmith     .getNode().isInRadius(event.getBlock().getLocation()))
                || (Conflict.MystPortal     .getNode().isInRadius(event.getBlock().getLocation()))
                || (Conflict.Enchantments   .getNode().isInRadius(event.getBlock().getLocation()))
                || (Conflict.RichPortal     .getNode().isInRadius(event.getBlock().getLocation()))
                || (Conflict.Potions        .getNode().isInRadius(event.getBlock().getLocation())))
			){
				event.setCancelled(true);
			}
		}
	}	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event){
		if(event.getLocation().getWorld().getName().equals("survival")){
			if(
                   (Conflict.Blacksmith     .getNode().isInRadius(event.getLocation()))
                || (Conflict.MystPortal     .getNode().isInRadius(event.getLocation()))
                || (Conflict.Enchantments   .getNode().isInRadius(event.getLocation()))
                || (Conflict.RichPortal     .getNode().isInRadius(event.getLocation()))
                || (Conflict.Potions        .getNode().isInRadius(event.getLocation()))
			){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getPlayer().getWorld().getName().equals("survival") && event.getPlayer().getItemInHand() != null 
				&& (event.getPlayer().getItemInHand().getType() == Material.LAVA_BUCKET 
				|| event.getPlayer().getItemInHand().getType() == Material.WATER_BUCKET)){
			if(
                   (Conflict.Blacksmith     .getNode().isInRadius(event.getPlayer().getLocation()))
                || (Conflict.MystPortal     .getNode().isInRadius(event.getPlayer().getLocation()))
                || (Conflict.Enchantments   .getNode().isInRadius(event.getPlayer().getLocation()))
                || (Conflict.RichPortal     .getNode().isInRadius(event.getPlayer().getLocation()))
                || (Conflict.Potions        .getNode().isInRadius(event.getPlayer().getLocation()))
			){
				event.setCancelled(true);
			}
		}
	}
}
