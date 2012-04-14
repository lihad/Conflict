package Lihad.Conflict.Listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;

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
			)
					
					&& !event.getPlayer().isOp()){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event){
		if(event.getBlock().getWorld().getName().equals("survival")){
			if(((!Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()) && Conflict.ABATTON_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_LOCATION.distance(event.getBlock().getLocation()) < 500))
					&& !event.getPlayer().isOp()){
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		if(event.getBlock().getWorld().getName().equals("survival")){
			if(((!Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()) && Conflict.ABATTON_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()) && Conflict.OCEIAN_LOCATION.distance(event.getBlock().getLocation()) < 500)
					|| (!Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()) && Conflict.SAVANIA_LOCATION.distance(event.getBlock().getLocation()) < 500))
					&& !event.getPlayer().isOp()){
				event.setCancelled(true);
			}
		}
	}
}
