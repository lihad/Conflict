package Lihad.Conflict.Listeners;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
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
	/**
	@EventHandler
	public static void onPlayerInteractshtdf(PlayerInteractEvent event){
		if(event.getPlayer().getItemInHand().getType() == Material.BOOK && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
			List<Block> blocks = event.getPlayer().getLineOfSight(null, 5);
			List<Entity> entities = event.getPlayer().getNearbyEntities(10, 10, 10);
			for(int i = 0;i<blocks.size();i++){
				blocks.get(i).getWorld().playEffect(blocks.get(i).getLocation(), Effect.MOBSPAWNER_FLAMES, null);
				for(int j = 0;j<entities.size();j++){
					if(entities.get(j) instanceof LivingEntity && (blocks.get(i).getLocation().equals(((LivingEntity)entities.get(j)).getLocation().getBlock().getLocation())
							|| blocks.get(i).getLocation().equals(((LivingEntity)entities.get(j)).getEyeLocation().getBlock().getLocation()))){
						entities.get(j).setFireTicks(60);
					}
				}
			}
		}
	}
	@EventHandler
	public static void onEntityShootBow(EntityShootBowEvent event){
		if(event.getEntity() instanceof Player){
			event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(3));
		}
	}
	*/
}
