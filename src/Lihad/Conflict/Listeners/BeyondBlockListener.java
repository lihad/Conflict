package Lihad.Conflict.Listeners;


import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
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
		if(event.getBlock().getWorld().getName().equals("survival")){
            if (((!Conflict.Abatton.getGenerals().contains(event.getPlayer().getName()) && Conflict.Abatton.getLocation().distance(event.getBlock().getLocation()) < Conflict.Abatton.getProtectionRadius())
					|| (!Conflict.Oceian.getGenerals().contains(event.getPlayer().getName()) && Conflict.Oceian.getLocation().distance(event.getBlock().getLocation()) < Conflict.Oceian.getProtectionRadius())
					|| (!Conflict.Savania.getGenerals().contains(event.getPlayer().getName()) && Conflict.Savania.getLocation().distance(event.getBlock().getLocation()) < Conflict.Savania.getProtectionRadius())
			)&& !event.getPlayer().isOp()){
				event.setCancelled(true);
			}
			if(!event.getPlayer().isOp() && ((Conflict.TRADE_BLACKSMITH.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_MYSTPORTAL.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_ENCHANTMENTS.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_RICHPORTAL.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_POTIONS.distance(event.getBlock().getLocation()) < 200))
			){
				event.setCancelled(true);
			}
		}
		else if(event.getBlock().getWorld().getName().equals("richworld")){
			if( !Conflict.PlayerCanUseTrade(event.getPlayer().getName(), "richportal") && !event.getPlayer().isOp()) {
				event.setCancelled(true);
				event.getPlayer().kickPlayer("Invalid Block Break by Unauthorized Player in Rich World");
			}
				
		}
	}
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event){
		if(event.getBlock().getWorld().getName().equals("survival")){
            if(( (!Conflict.Abatton.getGenerals().contains(event.getPlayer().getName()) && Conflict.Abatton.getLocation().distance(event.getBlock().getLocation()) < Conflict.Abatton.getProtectionRadius())
					|| (!Conflict.Oceian.getGenerals().contains(event.getPlayer().getName()) && Conflict.Oceian.getLocation().distance(event.getBlock().getLocation()) < Conflict.Oceian.getProtectionRadius())
					|| (!Conflict.Savania.getGenerals().contains(event.getPlayer().getName()) && Conflict.Savania.getLocation().distance(event.getBlock().getLocation()) < Conflict.Savania.getProtectionRadius()))
					&& !event.getPlayer().isOp()){
				event.setCancelled(true);
			}
			if(!event.getPlayer().isOp() && ((Conflict.TRADE_BLACKSMITH.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_MYSTPORTAL.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_ENCHANTMENTS.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_RICHPORTAL.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_POTIONS.distance(event.getBlock().getLocation()) < 200))
			){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		if(event.getBlock().getWorld().getName().equals("survival")){
            if(((!Conflict.Abatton.getGenerals().contains(event.getPlayer().getName()) && Conflict.Abatton.getLocation().distance(event.getBlock().getLocation()) < Conflict.Abatton.getProtectionRadius())
					|| (!Conflict.Oceian.getGenerals().contains(event.getPlayer().getName()) && Conflict.Oceian.getLocation().distance(event.getBlock().getLocation()) < Conflict.Oceian.getProtectionRadius())
					|| (!Conflict.Savania.getGenerals().contains(event.getPlayer().getName()) && Conflict.Savania.getLocation().distance(event.getBlock().getLocation()) < Conflict.Savania.getProtectionRadius()))
					&& !event.getPlayer().isOp()){
				event.setCancelled(true);
			}
			if(!event.getPlayer().isOp() && ((Conflict.TRADE_BLACKSMITH.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_MYSTPORTAL.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_ENCHANTMENTS.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_RICHPORTAL.distance(event.getBlock().getLocation()) < 200)
					|| (Conflict.TRADE_POTIONS.distance(event.getBlock().getLocation()) < 200))
			){
				event.setCancelled(true);
			}
		}
	}	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event){
		if(event.getLocation().getWorld().getName().equals("survival")){
			if((Conflict.TRADE_BLACKSMITH.distance(event.getLocation()) < 200)
					|| (Conflict.TRADE_MYSTPORTAL.distance(event.getLocation()) < 200)
					|| (Conflict.TRADE_ENCHANTMENTS.distance(event.getLocation()) < 200)
					|| (Conflict.TRADE_RICHPORTAL.distance(event.getLocation()) < 200)
					|| (Conflict.TRADE_POTIONS.distance(event.getLocation()) < 200)
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
			if((Conflict.TRADE_BLACKSMITH.distance(event.getPlayer().getLocation()) < 200)
					|| (Conflict.TRADE_MYSTPORTAL.distance(event.getPlayer().getLocation()) < 200)
					|| (Conflict.TRADE_ENCHANTMENTS.distance(event.getPlayer().getLocation()) < 200)
					|| (Conflict.TRADE_RICHPORTAL.distance(event.getPlayer().getLocation()) < 200)
					|| (Conflict.TRADE_POTIONS.distance(event.getPlayer().getLocation()) < 200)
			){
				event.setCancelled(true);
			}
		}
	}
}
