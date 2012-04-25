package Lihad.Conflict.Listeners;


import org.bukkit.Material;
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
			if(((!Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()) && Conflict.ABATTON_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.ABATTON_GENERALS.contains(event.getPlayer().getName()) && Conflict.ABATTON_LOCATION.distance(event.getBlock().getLocation()) < Conflict.ABATTON_PROTECTION)
					|| (!Conflict.OCEIAN_GENERALS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_LOCATION.distance(event.getBlock().getLocation()) < Conflict.OCEIAN_PROTECTION)
					|| (!Conflict.SAVANIA_GENERALS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_LOCATION.distance(event.getBlock().getLocation()) < Conflict.SAVANIA_PROTECTION)
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
			if((!Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()) && Conflict.ABATTON_TRADES.contains("richportal")
					|| !Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_TRADES.contains("richportal")
					|| !Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_TRADES.contains("richportal"))
					&& !event.getPlayer().isOp())
			{
				event.setCancelled(true);
				event.getPlayer().kickPlayer("Invalid Block Break by Unauthorized Player in Rich World");
			}
				
		}
	}
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event){
		if(event.getBlock().getWorld().getName().equals("survival")){
			if(((!Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()) && Conflict.ABATTON_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.ABATTON_GENERALS.contains(event.getPlayer().getName()) && Conflict.ABATTON_LOCATION.distance(event.getBlock().getLocation()) < Conflict.ABATTON_PROTECTION)
					|| (!Conflict.OCEIAN_GENERALS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_LOCATION.distance(event.getBlock().getLocation()) < Conflict.OCEIAN_PROTECTION)
					|| (!Conflict.SAVANIA_GENERALS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_LOCATION.distance(event.getBlock().getLocation()) < Conflict.SAVANIA_PROTECTION))
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
			if(((!Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()) && Conflict.ABATTON_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.ABATTON_GENERALS.contains(event.getPlayer().getName()) && Conflict.ABATTON_LOCATION.distance(event.getBlock().getLocation()) < Conflict.ABATTON_PROTECTION)
					|| (!Conflict.OCEIAN_GENERALS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_LOCATION.distance(event.getBlock().getLocation()) < Conflict.OCEIAN_PROTECTION)
					|| (!Conflict.SAVANIA_GENERALS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_LOCATION.distance(event.getBlock().getLocation()) < Conflict.SAVANIA_PROTECTION))
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
