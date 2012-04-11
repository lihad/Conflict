package Lihad.Conflict.Listeners;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import Lihad.Conflict.Conflict;
import Lihad.Conflict.Util.BeyondUtil;

public class BeyondEntityListener implements Listener {
	
	public static Conflict plugin;
	
	public BeyondEntityListener(Conflict instance) {
		plugin = instance;
	}
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		//
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//
		// "PvP"
		if(event.getEntity().getWorld().getName().equals("survival")){
			if((event.getDamager() instanceof Player || (event.getDamager() instanceof Projectile 
					&& ((Projectile)event.getDamager()).getShooter() instanceof Player)) && event.getEntity() instanceof Player){
				Player player;
				Player hurt = (Player)event.getEntity();
				if(event.getDamager() instanceof Player) player = (Player)event.getDamager();
				else player = (Player)((Projectile)event.getDamager()).getShooter();
				if(player.isOp()){
					return;
				}else if((Conflict.ABATTON_PLAYERS.contains(player.getName()) && Conflict.ABATTON_PLAYERS.contains(hurt.getName()))
						|| (Conflict.OCEIAN_PLAYERS.contains(player.getName()) && Conflict.OCEIAN_PLAYERS.contains(hurt.getName()))
						|| (Conflict.SAVANIA_PLAYERS.contains(player.getName()) && Conflict.SAVANIA_PLAYERS.contains(hurt.getName()))){
					event.setCancelled(true);
				}else if(player.getLocation().distance(player.getWorld().getSpawnLocation()) > 5000.0){
					return;
				}else if((Conflict.ABATTON_PLAYERS.contains(player.getName()) && (Conflict.OCEIAN_LOCATION.distance(hurt.getLocation())<500.0 || Conflict.SAVANIA_LOCATION.distance(hurt.getLocation())<500.0))
						|| (Conflict.OCEIAN_PLAYERS.contains(player.getName()) && (Conflict.ABATTON_LOCATION.distance(hurt.getLocation())<500.0 || Conflict.SAVANIA_LOCATION.distance(hurt.getLocation())<500.0))
						|| (Conflict.SAVANIA_PLAYERS.contains(player.getName()) && (Conflict.ABATTON_LOCATION.distance(hurt.getLocation())<500.0 || Conflict.OCEIAN_LOCATION.distance(hurt.getLocation())<500.0))){
					event.setCancelled(true);
				}else if((Conflict.ABATTON_PLAYERS.contains(player.getName()) && (Conflict.ABATTON_LOCATION.distance(hurt.getLocation())<500.0))
						|| (Conflict.OCEIAN_PLAYERS.contains(player.getName()) && (Conflict.OCEIAN_LOCATION.distance(hurt.getLocation())<500.0))
						|| (Conflict.SAVANIA_PLAYERS.contains(player.getName()) && (Conflict.SAVANIA_LOCATION.distance(hurt.getLocation())<500.0))){
					return;
				}else if(player.getLocation().distance(player.getWorld().getSpawnLocation()) < 5000.0){
					event.setCancelled(true);
				}
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(event.getDamager() instanceof Player){
			Player player = (Player) event.getDamager();
			if((Conflict.ABATTON_PERKS.contains("strike") && Conflict.ABATTON_PLAYERS.contains(player.getName()))
					|| (Conflict.OCEIAN_PERKS.contains("strike") && Conflict.OCEIAN_PLAYERS.contains(player.getName()))
					|| (Conflict.SAVANIA_PERKS.contains("strike") && Conflict.SAVANIA_PLAYERS.contains(player.getName()))){
				event.setDamage(event.getDamage()+1);
			}
		}
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getDamager();
			if((Conflict.ABATTON_PERKS.contains("shield") && Conflict.ABATTON_PLAYERS.contains(player.getName()))
					|| (Conflict.OCEIAN_PERKS.contains("shield") && Conflict.OCEIAN_PLAYERS.contains(player.getName()))
					|| (Conflict.SAVANIA_PERKS.contains("shield") && Conflict.SAVANIA_PLAYERS.contains(player.getName()))){
				event.setDamage(event.getDamage()-1);
			}
		}
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		if(event.getEntity().getKiller() != null){
			Player player = event.getEntity().getKiller();
			int random = new Random().nextInt(100);
			if(random < 2 
					&& event.getEntity() instanceof PigZombie 
					&& ((Conflict.ABATTON_PLAYERS.contains(player.getName()) && Conflict.ABATTON_PERKS.contains("weapondrops"))
							|| (Conflict.OCEIAN_PLAYERS.contains(player.getName()) && Conflict.OCEIAN_PERKS.contains("weapondrops"))
							|| (Conflict.SAVANIA_PLAYERS.contains(player.getName()) && Conflict.SAVANIA_PERKS.contains("weapondrops")))){
				ItemStack stack = new ItemStack(BeyondUtil.weaponTypeRandomizer(), 1);
				while(stack.getEnchantments().isEmpty()){
					stack.addUnsafeEnchantment(BeyondUtil.weaponEnchantRandomizer(), BeyondUtil.weaponLevelRandomizer());
					Random chance = new Random();
					int next = chance.nextInt(100);
					if(next<30)stack.addUnsafeEnchantment(BeyondUtil.weaponEnchantRandomizer(),BeyondUtil.weaponLevelRandomizer());
					if(next<20)stack.addUnsafeEnchantment(BeyondUtil.weaponEnchantRandomizer(), BeyondUtil.weaponLevelRandomizer());
					if(next<5)stack.addUnsafeEnchantment(BeyondUtil.weaponEnchantRandomizer(), BeyondUtil.weaponLevelRandomizer());
				}
				event.getDrops().add(stack);
				player.sendMessage("Hooray! A "+ChatColor.BLUE.toString()+stack.getType().toString()+ChatColor.WHITE.toString()+" dropped! Rarity Index: "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity(stack))+BeyondUtil.rarity(stack));
			}else if(random >= 2 && random < 4 
					&& event.getEntity() instanceof PigZombie 
					&& ((Conflict.ABATTON_PLAYERS.contains(player.getName()) && Conflict.ABATTON_PERKS.contains("armordrops"))
							|| (Conflict.OCEIAN_PLAYERS.contains(player.getName()) && Conflict.OCEIAN_PERKS.contains("armordrops"))
							|| (Conflict.SAVANIA_PLAYERS.contains(player.getName()) && Conflict.SAVANIA_PERKS.contains("armordrops")))){
				ItemStack stack = new ItemStack(BeyondUtil.armorTypeRandomizer(), 1);
				stack.addUnsafeEnchantment(BeyondUtil.armorEnchantRandomizer(), BeyondUtil.armorLevelRandomizer());
				Random chance = new Random();
				int next = chance.nextInt(100);
				if(next<30)stack.addUnsafeEnchantment(BeyondUtil.armorEnchantRandomizer(), BeyondUtil.armorLevelRandomizer());							
				if(next<20)stack.addUnsafeEnchantment(BeyondUtil.armorEnchantRandomizer(), BeyondUtil.armorLevelRandomizer());
				if(next<5)stack.addUnsafeEnchantment(BeyondUtil.armorEnchantRandomizer(), BeyondUtil.armorLevelRandomizer());
				event.getDrops().add(stack);
				player.sendMessage("Hooray! A "+ChatColor.BLUE.toString()+stack.getType().toString()+ChatColor.WHITE.toString()+" dropped! Rarity Index: "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity(stack))+BeyondUtil.rarity(stack));
			}else if(random < 2 
					&& event.getEntity() instanceof Zombie && !(event.getEntity() instanceof PigZombie)
					&& ((Conflict.ABATTON_PLAYERS.contains(player.getName()) && Conflict.ABATTON_PERKS.contains("tooldrops"))
							|| (Conflict.OCEIAN_PLAYERS.contains(player.getName()) && Conflict.OCEIAN_PERKS.contains("tooldrops"))
							|| (Conflict.SAVANIA_PLAYERS.contains(player.getName()) && Conflict.SAVANIA_PERKS.contains("tooldrops")))){
				ItemStack stack = new ItemStack(BeyondUtil.toolTypeRandomizer(), 1);
				stack.addUnsafeEnchantment(BeyondUtil.toolEnchantRandomizer(), BeyondUtil.toolLevelRandomizer());
				Random chance = new Random();
				int next = chance.nextInt(100);
				if(next<40)stack.addUnsafeEnchantment(BeyondUtil.toolEnchantRandomizer(), BeyondUtil.toolLevelRandomizer());
				if(next<25)stack.addUnsafeEnchantment(BeyondUtil.toolEnchantRandomizer(), BeyondUtil.toolLevelRandomizer());
				if(next<10)stack.addUnsafeEnchantment(BeyondUtil.toolEnchantRandomizer(), BeyondUtil.toolLevelRandomizer());
				event.getDrops().add(stack);
				player.sendMessage("Hooray! A "+ChatColor.BLUE.toString()+stack.getType().toString()+ChatColor.WHITE.toString()+" dropped! Rarity Index: "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity(stack))+BeyondUtil.rarity(stack));
			}else if(random >= 2 && random < 4 
					&& event.getEntity() instanceof Zombie && !(event.getEntity() instanceof PigZombie)
					&& ((Conflict.ABATTON_PLAYERS.contains(player.getName()) && Conflict.ABATTON_PERKS.contains("potiondrops"))
							|| (Conflict.OCEIAN_PLAYERS.contains(player.getName()) && Conflict.OCEIAN_PERKS.contains("potiondrops"))
							|| (Conflict.SAVANIA_PLAYERS.contains(player.getName()) && Conflict.SAVANIA_PERKS.contains("potiondrops")))){
				Potion potion = new Potion(BeyondUtil.potionTypeRandomizer(), BeyondUtil.potionTierRandomizer(), BeyondUtil.potionSplashRandomizer());
				ItemStack stack = new ItemStack(Material.POTION, 1);
				potion.apply(stack);
				event.getDrops().add(stack);
			}else if(random < 2 
					&& event.getEntity() instanceof Skeleton 
					&& ((Conflict.ABATTON_PLAYERS.contains(player.getName()) && Conflict.ABATTON_PERKS.contains("bowdrops"))
							|| (Conflict.OCEIAN_PLAYERS.contains(player.getName()) && Conflict.OCEIAN_PERKS.contains("bowdrops"))
							|| (Conflict.SAVANIA_PLAYERS.contains(player.getName()) && Conflict.SAVANIA_PERKS.contains("bowdrops")))){
				ItemStack stack = new ItemStack(Material.BOW, 1);
				stack.addUnsafeEnchantment(BeyondUtil.bowEnchantRandomizer(), BeyondUtil.bowLevelRandomizer());
				Random chance = new Random();
				int next = chance.nextInt(100);
				if(next<40)stack.addUnsafeEnchantment(BeyondUtil.bowEnchantRandomizer(), BeyondUtil.bowLevelRandomizer());
				if(next<25)stack.addUnsafeEnchantment(BeyondUtil.bowEnchantRandomizer(), BeyondUtil.bowLevelRandomizer());
				if(next<10)stack.addUnsafeEnchantment(BeyondUtil.bowEnchantRandomizer(), BeyondUtil.bowLevelRandomizer());
				event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
			}
		}
	}
}