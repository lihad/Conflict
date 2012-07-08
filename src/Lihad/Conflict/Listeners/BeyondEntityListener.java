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
	
	public static Conflict plugin;
	
	public BeyondEntityListener(Conflict instance) {
		plugin = instance;
	}
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        Entity hurt = event.getEntity();
    
        if (hurt instanceof Villager) {
            Player attacker = (event.getDamager() instanceof Player) ? ((Player)event.getDamager()) : null;
            for (City city : Conflict.cities) {
                if (city.isInRadius(hurt.getLocation())) {
                    if (attacker != null && !attacker.isOp()) {
                        String message = "" + Conflict.PLAYERCOLOR + attacker.getName() + Conflict.TEXTCOLOR + " tried to murder an innocent villager in " + Conflict.CITYCOLOR + city.getName();
                        org.bukkit.Bukkit.getServer().broadcastMessage(message);
                        attacker.damage(event.getDamage(), hurt);
                    }
                    event.setCancelled(true);
                }
            }
            return;
        }
		if(event.getDamager() instanceof Player){
			Player player = (Player) event.getDamager();
			if((Conflict.Abatton.getPerks().contains("strike") && Conflict.Abatton.hasPlayer(player.getName()))
					|| (Conflict.Oceian.getPerks().contains("strike") && Conflict.Oceian.hasPlayer(player.getName()))
					|| (Conflict.Savania.getPerks().contains("strike") && Conflict.Savania.hasPlayer(player.getName()))){
				event.setDamage(event.getDamage()+1);
			}
		}
		if(hurt instanceof Player){
			Player player = (Player) hurt;
			if((Conflict.Abatton.getPerks().contains("shield") && Conflict.Abatton.hasPlayer(player.getName()))
					|| (Conflict.Oceian.getPerks().contains("shield") && Conflict.Oceian.hasPlayer(player.getName()))
					|| (Conflict.Savania.getPerks().contains("shield") && Conflict.Savania.hasPlayer(player.getName()))){
				event.setDamage(event.getDamage()-1);
			}
		}
	}
	/**
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event){
		if(event.getLocation().getWorld().getName().equals("survival")){
			if((Conflict.TRADE_BLACKSMITH.distance(event.getLocation()) < 200)
					|| (Conflict.TRADE_MYSTPORTAL.distance(event.getLocation()) < 200)
					|| (Conflict.TRADE_ENCHANTMENTS.distance(event.getLocation()) < 200)
					|| (Conflict.TRADE_RICHPORTAL.distance(event.getLocation()) < 200)
					|| (Conflict.TRADE_POTIONS.distance(event.getLocation()) < 200)
			){
				List<Entity> entities;
				if(event.getEntity() != null)entities = event.getEntity().getNearbyEntities(5, 2, 5);
				else entities = event.getLocation().getWorld().spawnCreature(event.getLocation(), EntityType.CHICKEN).getNearbyEntities(5, 2, 5);
				for(int i = 0;i<entities.size();i++){
					if(entities.get(i) instanceof Player){
						Player player = (Player)entities.get(i);
						if(player.getInventory().getArmorContents() != null && player.getInventory().getArmorContents().length > 0){
							int index = Conflict.random.nextInt(player.getInventory().getArmorContents().length);
							if(player.getInventory().getArmorContents()[index].getType() != Material.AIR){
								player.getWorld().dropItemNaturally(player.getLocation(), player.getInventory().getArmorContents()[index]);
								player.sendMessage(ChatColor.RED+"Your "+player.getInventory().getArmorContents()[index].getType().toString()+" got ripped off!!!");
								ItemStack[] stack =  player.getInventory().getArmorContents();
								stack[index] = new ItemStack(0);
								player.getInventory().setArmorContents(stack);
								player.updateInventory();
								player.getWorld().playEffect(player.getLocation(), Effect.GHAST_SHRIEK, null);
								player.getWorld().playEffect(player.getLocation(), Effect.GHAST_SHRIEK, null);
								player.getWorld().playEffect(player.getLocation(), Effect.GHAST_SHRIEK, null);
								player.getWorld().playEffect(player.getLocation(), Effect.BLAZE_SHOOT, null);
								player.getWorld().playEffect(player.getLocation(), Effect.ZOMBIE_CHEW_IRON_DOOR, null);
								player.getWorld().playEffect(player.getLocation(), Effect.EXTINGUISH, null);
							}
						}
					}
				}
				event.setCancelled(true);
			}
		}
	}
	*/
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		if(event.getEntity().getKiller() != null){
			Player player = event.getEntity().getKiller();
			int random = Conflict.random.nextInt(100);
			if(random < 2 
					&& event.getEntity() instanceof PigZombie 
					&& ((Conflict.Abatton.hasPlayer(player.getName()) && Conflict.Abatton.getPerks().contains("weapondrops"))
							|| (Conflict.Oceian.hasPlayer(player.getName()) && Conflict.Oceian.getPerks().contains("weapondrops"))
							|| (Conflict.Savania.hasPlayer(player.getName()) && Conflict.Savania.getPerks().contains("weapondrops")))){
				ItemStack stack = new ItemStack(BeyondUtil.weaponTypeRandomizer(), 1);
                BeyondUtil.addRandomEnchant(stack);
                int next = Conflict.random.nextInt(100);
                if(next<30) BeyondUtil.addRandomEnchant(stack);
                if(next<20) BeyondUtil.addRandomEnchant(stack);
                if(next<5) BeyondUtil.addRandomEnchant(stack);
				event.getDrops().add(stack);
				player.sendMessage("Hooray! A "+ChatColor.BLUE.toString()+stack.getType().toString()+ChatColor.WHITE.toString()+" dropped! Rarity Index: "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity(stack))+BeyondUtil.rarity(stack));
			}else if(random >= 2 && random < 4 
					&& event.getEntity() instanceof PigZombie 
					&& ((Conflict.Abatton.hasPlayer(player.getName()) && Conflict.Abatton.getPerks().contains("armordrops"))
							|| (Conflict.Oceian.hasPlayer(player.getName()) && Conflict.Oceian.getPerks().contains("armordrops"))
							|| (Conflict.Savania.hasPlayer(player.getName()) && Conflict.Savania.getPerks().contains("armordrops")))){
				ItemStack stack = new ItemStack(BeyondUtil.armorTypeRandomizer(), 1);
                BeyondUtil.addRandomEnchant(stack);
                int next = Conflict.random.nextInt(100);
                if(next<30) BeyondUtil.addRandomEnchant(stack);
                if(next<20) BeyondUtil.addRandomEnchant(stack);
                if(next<5) BeyondUtil.addRandomEnchant(stack);
				event.getDrops().add(stack);
				player.sendMessage("Hooray! A "+ChatColor.BLUE.toString()+stack.getType().toString()+ChatColor.WHITE.toString()+" dropped! Rarity Index: "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity(stack))+BeyondUtil.rarity(stack));
			}else if(random < 2 
					&& event.getEntity() instanceof Zombie && !(event.getEntity() instanceof PigZombie)
					&& ((Conflict.Abatton.hasPlayer(player.getName()) && Conflict.Abatton.getPerks().contains("tooldrops"))
							|| (Conflict.Oceian.hasPlayer(player.getName()) && Conflict.Oceian.getPerks().contains("tooldrops"))
							|| (Conflict.Savania.hasPlayer(player.getName()) && Conflict.Savania.getPerks().contains("tooldrops")))){
				ItemStack stack = new ItemStack(BeyondUtil.toolTypeRandomizer(), 1);
                BeyondUtil.addRandomEnchant(stack);
                int next = Conflict.random.nextInt(100);
                if(next<30) BeyondUtil.addRandomEnchant(stack);
                if(next<20) BeyondUtil.addRandomEnchant(stack);
                if(next<5) BeyondUtil.addRandomEnchant(stack);
				event.getDrops().add(stack);
				player.sendMessage("Hooray! A "+ChatColor.BLUE.toString()+stack.getType().toString()+ChatColor.WHITE.toString()+" dropped! Rarity Index: "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity(stack))+BeyondUtil.rarity(stack));
			}else if(random >= 2 && random < 4 
					&& event.getEntity() instanceof Zombie && !(event.getEntity() instanceof PigZombie)
					&& ((Conflict.Abatton.hasPlayer(player.getName()) && Conflict.Abatton.getPerks().contains("potiondrops"))
							|| (Conflict.Oceian.hasPlayer(player.getName()) && Conflict.Oceian.getPerks().contains("potiondrops"))
							|| (Conflict.Savania.hasPlayer(player.getName()) && Conflict.Savania.getPerks().contains("potiondrops")))){
				Potion potion = new Potion(BeyondUtil.potionTypeRandomizer(), BeyondUtil.potionTierRandomizer(), BeyondUtil.potionSplashRandomizer());
				ItemStack stack = new ItemStack(Material.POTION, 1);
				potion.apply(stack);
				event.getDrops().add(stack);
			}else if(random < 2 
					&& event.getEntity() instanceof Skeleton 
					&& ((Conflict.Abatton.hasPlayer(player.getName()) && Conflict.Abatton.getPerks().contains("bowdrops"))
							|| (Conflict.Oceian.hasPlayer(player.getName()) && Conflict.Oceian.getPerks().contains("bowdrops"))
							|| (Conflict.Savania.hasPlayer(player.getName()) && Conflict.Savania.getPerks().contains("bowdrops")))){
				ItemStack stack = new ItemStack(Material.BOW, 1);
                BeyondUtil.addRandomEnchant(stack);
                int next = Conflict.random.nextInt(100);
                if(next<30) BeyondUtil.addRandomEnchant(stack);
                if(next<20) BeyondUtil.addRandomEnchant(stack);
                if(next<5) BeyondUtil.addRandomEnchant(stack);
				event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
			}else if(random < 2
					&& event.getEntity() instanceof Sheep 
					&& ((Conflict.Abatton.hasPlayer(player.getName()) && Conflict.Abatton.getPerks().contains("golddrops"))
							|| (Conflict.Oceian.hasPlayer(player.getName()) && Conflict.Oceian.getPerks().contains("golddrops"))
							|| (Conflict.Savania.hasPlayer(player.getName()) && Conflict.Savania.getPerks().contains("golddrops")))){
				ItemStack stack = new ItemStack(Material.GOLD_INGOT, 1);
				event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
			}
		}
	}
}