package Lihad.Conflict.Listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import Lihad.Conflict.*;
import Lihad.Conflict.Util.BeyondUtil;

public class BeyondEntityListener implements Listener {
	
	public BeyondEntityListener() { }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        Entity hurt = event.getEntity();
    
        if (hurt instanceof Villager) {
            Player attacker = (event.getDamager() instanceof Player) ? ((Player)event.getDamager()) : null;
            for (City city : Conflict.cities) {
                if (city.isInRadius(hurt.getLocation())) {
                    if (attacker == null) {
                        // attacked by non-player
                        event.setCancelled(true);
                    }
                    else if (!attacker.isOp()) {
                        // It's a player.  Only block non-ops
                        String message = "" + Conflict.PLAYERCOLOR + attacker.getName() + Conflict.TEXTCOLOR + " tried to murder an innocent villager in " + Conflict.CITYCOLOR + city.getName();
                        org.bukkit.Bukkit.getServer().broadcastMessage(message);
                        attacker.damage(event.getDamage(), hurt);
                        event.setCancelled(true);
                    }
                }
            }
            return;
        }
	}
}