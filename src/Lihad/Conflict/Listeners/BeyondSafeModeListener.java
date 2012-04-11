package Lihad.Conflict.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import Lihad.Conflict.Conflict;

public class BeyondSafeModeListener implements Listener {
	public static Conflict plugin;

	public BeyondSafeModeListener(Conflict instance) {
		plugin = instance;
	}
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event){
		if(Conflict.PLAYER_SET_SELECT.containsKey(event.getPlayer().getName()) && event.getClickedBlock() != null){
			if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("Abatton"))Conflict.ABATTON_LOCATION = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("Oceian"))Conflict.OCEIAN_LOCATION = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("Savania"))Conflict.SAVANIA_LOCATION = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("blacksmith"))Conflict.TRADE_BLACKSMITH = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("potions"))Conflict.TRADE_POTIONS = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("enchantments"))Conflict.TRADE_ENCHANTMENTS = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("richportal"))Conflict.TRADE_RICHPORTAL = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("mystportal"))Conflict.TRADE_MYSTPORTAL = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("drifterabatton"))Conflict.ABATTON_LOCATION_DRIFTER = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("drifteroceian"))Conflict.OCEIAN_LOCATION_DRIFTER = event.getClickedBlock().getLocation();
			else if(Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()).equalsIgnoreCase("driftersavania"))Conflict.SAVANIA_LOCATION_DRIFTER = event.getClickedBlock().getLocation();
			else{
				event.getPlayer().sendMessage("Failed set");
				Conflict.PLAYER_SET_SELECT.remove(event.getPlayer().getName());
				return;
			}
			event.getPlayer().sendMessage("Set "+Conflict.PLAYER_SET_SELECT.get(event.getPlayer().getName()));
			Conflict.PLAYER_SET_SELECT.remove(event.getPlayer().getName());
		}
	}
}
