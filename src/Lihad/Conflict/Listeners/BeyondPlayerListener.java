package Lihad.Conflict.Listeners;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import Lihad.Conflict.Conflict;
import Lihad.Conflict.Util.BeyondUtil;

public class BeyondPlayerListener implements Listener {
	public static Conflict plugin;
	public BeyondPlayerListener(Conflict instance) {
		plugin = instance;
	}
	@EventHandler
	public static void onPlayerMove(PlayerMoveEvent event){
		if((event.getFrom().getBlockX() != event.getTo().getBlockX()
				|| event.getFrom().getBlockY() != event.getTo().getBlockY()
				|| event.getFrom().getBlockZ() != event.getTo().getBlockZ())
				&& event.getTo().getWorld().getName().equals("survival")) {
			if(Conflict.UNASSIGNED_PLAYERS.contains(event.getPlayer().getName())){
				event.setTo(event.getFrom());
				event.getPlayer().sendMessage(ChatColor.RED+"You need to join a Capital before continuing...");
				event.getPlayer().sendMessage(ChatColor.RED+"Type "+ChatColor.GRAY+"/abatton"+ChatColor.RED+" or "+ChatColor.GRAY+"/oceian"+ChatColor.RED+" or "+ChatColor.GRAY+"/savania");
			}
			if(Conflict.ABATTON_LOCATION.distance(event.getTo()) < 500.0
					&& Conflict.ABATTON_LOCATION.distance(event.getFrom()) >= 500.0){
				event.getPlayer().sendMessage(ChatColor.GRAY.toString()+"You have entered the Capital of Abatton");
			}
			else if(Conflict.ABATTON_LOCATION.distance(event.getTo()) > 500.0
					&& Conflict.ABATTON_LOCATION.distance(event.getFrom()) <= 500.0){
				event.getPlayer().sendMessage(ChatColor.GRAY.toString()+"You have left the Capital of Abatton");
			}
			if(Conflict.OCEIAN_LOCATION.distance(event.getTo()) < 500.0
					&& Conflict.OCEIAN_LOCATION.distance(event.getFrom()) >= 500.0){
				event.getPlayer().sendMessage(ChatColor.GRAY.toString()+"You have entered the Capital of Oceian");
			}
			else if(Conflict.OCEIAN_LOCATION.distance(event.getTo()) > 500.0
					&& Conflict.OCEIAN_LOCATION.distance(event.getFrom()) <= 500.0){
				event.getPlayer().sendMessage(ChatColor.GRAY.toString()+"You have left the Capital of Oceian");
			}
			if(Conflict.SAVANIA_LOCATION.distance(event.getTo()) < 500.0
					&& Conflict.SAVANIA_LOCATION.distance(event.getFrom()) >= 500.0){
				event.getPlayer().sendMessage(ChatColor.GRAY.toString()+"You have entered the Capital of Savania");
			}
			else if(Conflict.SAVANIA_LOCATION.distance(event.getTo()) > 500.0
					&& Conflict.SAVANIA_LOCATION.distance(event.getFrom()) <= 500.0){
				event.getPlayer().sendMessage(ChatColor.GRAY.toString()+"You have left the Capital of Savania");
			}
			if(event.getPlayer().getWorld().getSpawnLocation().distance(event.getTo()) > 5000.0
					&& event.getPlayer().getWorld().getSpawnLocation().distance(event.getFrom()) <= 5000.0){
				event.getPlayer().sendMessage(ChatColor.GRAY.toString()+"You have entered the Wilderness");
			}
			else if(event.getPlayer().getWorld().getSpawnLocation().distance(event.getTo()) < 5000.0
					&& event.getPlayer().getWorld().getSpawnLocation().distance(event.getFrom()) >= 5000.0){
				event.getPlayer().sendMessage(ChatColor.GRAY.toString()+"You have entered the City Scape");
			}
		}
	}
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event){
		if(!Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()) && !Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName())
				&& !Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName())
				&& !Conflict.handler.inGroup(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), "Drifter")){
			if(!Conflict.UNASSIGNED_PLAYERS.contains(event.getPlayer().getName()))Conflict.UNASSIGNED_PLAYERS.add(event.getPlayer().getName());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerPortal(PlayerPortalEvent event){
		if(event.getCause().equals(TeleportCause.NETHER_PORTAL) && event.getFrom().getWorld().getName().equals("survival") &&  Conflict.TRADE_RICHPORTAL.distance(event.getFrom()) < 10){
			if((Conflict.ABATTON_TRADES.contains("richportal") && Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()))
					|| (Conflict.OCEIAN_TRADES.contains("richportal") && Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()))
					|| (Conflict.SAVANIA_TRADES.contains("richportal") && Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()))
			){
				event.getPlayer().sendMessage("Shaaaaazaaam!");
				event.getPlayer().teleport(new Location(plugin.getServer().getWorld("richworld"), 0.0, 0.0, 0.0));
				event.setTo(new Location(plugin.getServer().getWorld("richworld"), 0.0, 0.0, 0.0));
				event.setCancelled(true);
			}else{
				event.getPlayer().sendMessage("Epic Fail");
			}
		}
		else if(event.getCause().equals(TeleportCause.NETHER_PORTAL) && event.getFrom().getWorld().getName().equals("survival") && Conflict.TRADE_MYSTPORTAL.distance(event.getFrom()) < 10){
			if((Conflict.ABATTON_TRADES.contains("mystportal") && Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()))
					|| (Conflict.OCEIAN_TRADES.contains("mystportal") && Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()))
					|| (Conflict.SAVANIA_TRADES.contains("mystportal") && Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()))
			){
				event.getPlayer().sendMessage("Shaaaaazaaam!");
				event.getPlayer().teleport(new Location(plugin.getServer().getWorld("mystworld"), 0.0, 0.0, 0.0));
				event.setTo(new Location(plugin.getServer().getWorld("mystworld"), 0.0, 0.0, 0.0));
				event.setCancelled(true);
			}else{
				event.getPlayer().sendMessage("Epic Fail");
			}
		}
	}
	public static void onPlayerTeleport(PlayerTeleportEvent event){

		if(event.getTo().getWorld().getName().equals("survival") && (event.getTo().distance(Conflict.TRADE_BLACKSMITH) < 200 || event.getTo().distance(Conflict.TRADE_ENCHANTMENTS) < 200
			|| event.getTo().distance(Conflict.TRADE_MYSTPORTAL) < 200 || event.getTo().distance(Conflict.TRADE_POTIONS) < 200
			|| event.getTo().distance(Conflict.TRADE_RICHPORTAL) < 200) && !event.getPlayer().isOp()){
			event.setTo(event.getFrom());
		}
		else if(event.getTo().getWorld().getName().equals("richworld") && event.getCause().equals(TeleportCause.ENDER_PEARL)){
			event.setTo(event.getFrom());
		}
	}
	@EventHandler
	public static void onPlayerExpChange(PlayerExpChangeEvent event){
		if(event.getAmount() > 0){
			List<Entity> entities = event.getPlayer().getNearbyEntities(20, 20, 20);
			for(int i=0;i<entities.size();i++){
				if(entities.get(i) instanceof Player 
						&& ((Conflict.ABATTON_PLAYERS.contains(((Player)entities.get(i)).getName()) && Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()))
								|| (Conflict.OCEIAN_PLAYERS.contains(((Player)entities.get(i)).getName()) && Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()))
								|| (Conflict.SAVANIA_PLAYERS.contains(((Player)entities.get(i)).getName()) && Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName())))
				){
					((Player)entities.get(i)).giveExp(event.getAmount());
				}
			}
		}
	}
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event){
		if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.TRADE_BLACKSMITH.getBlock().getLocation())
				&&((Conflict.ABATTON_TRADES.contains("blacksmith") && Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()))
						|| (Conflict.OCEIAN_TRADES.contains("blacksmith") && Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()))
						|| (Conflict.SAVANIA_TRADES.contains("blacksmith") && Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName())))){
			if(event.getPlayer().getItemInHand().getDurability() != 0){
				if(Conflict.TRADE_BLACKSMITH_PLAYER_USES.containsKey(event.getPlayer().getName())){
					if(Conflict.TRADE_BLACKSMITH_PLAYER_USES.get(event.getPlayer().getName())<5){
						Conflict.TRADE_BLACKSMITH_PLAYER_USES.put(event.getPlayer().getName(), Conflict.TRADE_BLACKSMITH_PLAYER_USES.get(event.getPlayer().getName())+1);
						event.getPlayer().getItemInHand().setDurability((short) 0);
					}else{
						event.getPlayer().sendMessage("You have accessed blacksmith too many times this hour");
						event.setCancelled(true);
					}
				}else Conflict.TRADE_BLACKSMITH_PLAYER_USES.put(event.getPlayer().getName(), 1);
			}
			else event.getPlayer().sendMessage("This item is either unable to be repaired or is at max durability");
			event.getPlayer().updateInventory();
		}else if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.TRADE_ENCHANTMENTS.getBlock().getLocation())
				&&((Conflict.ABATTON_TRADES.contains("enchantments") && Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()))
						|| (Conflict.OCEIAN_TRADES.contains("enchantments") && Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()))
						|| (Conflict.SAVANIA_TRADES.contains("enchantments") && Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName())))){
			ItemStack stack = event.getPlayer().getItemInHand();
			if(stack.getType() == Material.DIAMOND_SWORD){
				if(Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.containsKey(event.getPlayer().getName())){
					if(Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(event.getPlayer().getName())<5){
						Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(event.getPlayer().getName(), Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(event.getPlayer().getName())+1);
						event.getPlayer().getItemInHand().addUnsafeEnchantment(BeyondUtil.weaponEnchantRandomizer(), BeyondUtil.weaponLevelRandomizer());
					}else{
						event.getPlayer().sendMessage("You have accessed enchantments too many times this hour");
						event.setCancelled(true);
					}
				}else Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(event.getPlayer().getName(), 1);
			}
			else if(stack.getType() == Material.DIAMOND_HELMET || stack.getType() == Material.DIAMOND_CHESTPLATE 
					|| stack.getType() == Material.DIAMOND_LEGGINGS || stack.getType() == Material.DIAMOND_BOOTS){
				if(Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.containsKey(event.getPlayer().getName())){
					if(Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(event.getPlayer().getName())<5){
						Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(event.getPlayer().getName(), Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(event.getPlayer().getName())+1);
						event.getPlayer().getItemInHand().addUnsafeEnchantment(BeyondUtil.armorEnchantRandomizer(),BeyondUtil.armorLevelRandomizer());
					}else{
						event.getPlayer().sendMessage("You have accessed enchantments too many times this hour");
						event.setCancelled(true);
					}
				}else Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(event.getPlayer().getName(), 1);
			}
			else if(stack.getType() == Material.DIAMOND_AXE || stack.getType() == Material.DIAMOND_PICKAXE 
					|| stack.getType() == Material.DIAMOND_SPADE || stack.getType() == Material.DIAMOND_HOE){
				if(Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.containsKey(event.getPlayer().getName())){
					if(Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(event.getPlayer().getName())<5){
						Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(event.getPlayer().getName(), Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(event.getPlayer().getName())+1);
						event.getPlayer().getItemInHand().addUnsafeEnchantment(BeyondUtil.toolEnchantRandomizer(),BeyondUtil.toolLevelRandomizer());
					}else{
						event.getPlayer().sendMessage("You have accessed enchantments too many times this hour");
						event.setCancelled(true);
					}
				}else Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(event.getPlayer().getName(), 1);
			}
			else if(stack.getType() == Material.BOW){
				if(Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.containsKey(event.getPlayer().getName())){
					if(Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(event.getPlayer().getName())<5){
						Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(event.getPlayer().getName(), Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(event.getPlayer().getName())+1);
						event.getPlayer().getItemInHand().addUnsafeEnchantment(BeyondUtil.bowEnchantRandomizer(),BeyondUtil.bowLevelRandomizer());
					}else{
						event.getPlayer().sendMessage("You have accessed enchantments too many times this hour");
						event.setCancelled(true);
					}
				}else Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(event.getPlayer().getName(), 1);
			}
			else event.getPlayer().sendMessage("Bad Item.  Try something else");
			event.getPlayer().updateInventory();
		}else if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.TRADE_POTIONS.getBlock().getLocation())
				&&((Conflict.ABATTON_TRADES.contains("potions") && Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()))
						|| (Conflict.OCEIAN_TRADES.contains("potions") && Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()))
						|| (Conflict.SAVANIA_TRADES.contains("potions") && Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName())))){
			if(event.getPlayer().getItemInHand().getType() == Material.GLASS_BOTTLE){
				if(Conflict.TRADE_POTIONS_PLAYER_USES.containsKey(event.getPlayer().getName())){
					if(Conflict.TRADE_POTIONS_PLAYER_USES.get(event.getPlayer().getName())<30){
						Conflict.TRADE_POTIONS_PLAYER_USES.put(event.getPlayer().getName(), Conflict.TRADE_POTIONS_PLAYER_USES.get(event.getPlayer().getName())+1);
						Potion potion = new Potion(BeyondUtil.potionTypeRandomizer(), BeyondUtil.potionTierRandomizer(), BeyondUtil.potionSplashRandomizer());
						ItemStack stack = new ItemStack(Material.POTION, 1);
						potion.apply(stack);
						event.getPlayer().setItemInHand(stack);
						event.getPlayer().updateInventory();
					}else{
						event.getPlayer().sendMessage("You have accessed potions too many times this hour");
						event.setCancelled(true);
					}
				}else Conflict.TRADE_POTIONS_PLAYER_USES.put(event.getPlayer().getName(), 1);
			}
		}else if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.ABATTON_LOCATION.getBlock().getLocation())){
			if(event.getPlayer().getItemInHand().getType() == Material.GOLD_INGOT){
				event.getPlayer().sendMessage("You gave "+ChatColor.YELLOW.toString()+"1 "+ChatColor.WHITE.toString()+"Gold Bar to "+ChatColor.GREEN.toString()+"Abatton");
				if(event.getPlayer().getItemInHand().getAmount() == 1)event.getPlayer().getInventory().setItemInHand(null);
				else{
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount()-1);
					event.getPlayer().getInventory().setItemInHand(event.getPlayer().getItemInHand());
				}
				Conflict.ABATTON_WORTH++;
				event.getPlayer().updateInventory();
			}
			event.getPlayer().sendMessage("Abatton has "+ChatColor.GREEN.toString()+Conflict.ABATTON_WORTH+" Gold!");
		}else if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.OCEIAN_LOCATION.getBlock().getLocation())){
			if(event.getPlayer().getItemInHand().getType() == Material.GOLD_INGOT){
				event.getPlayer().sendMessage("You gave "+ChatColor.YELLOW.toString()+"1 "+ChatColor.WHITE.toString()+"Gold Bar to "+ChatColor.GREEN.toString()+"Oceian");
				if(event.getPlayer().getItemInHand().getAmount() == 1)event.getPlayer().getInventory().setItemInHand(null);
				else{
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount()-1);
					event.getPlayer().getInventory().setItemInHand(event.getPlayer().getItemInHand());
				}
				Conflict.OCEIAN_WORTH++;
				event.getPlayer().updateInventory();
			}
			event.getPlayer().sendMessage("Oceian has "+ChatColor.GREEN.toString()+Conflict.OCEIAN_WORTH+" Gold!");
		}else if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.SAVANIA_LOCATION.getBlock().getLocation())){
			if(event.getPlayer().getItemInHand().getType() == Material.GOLD_INGOT){
				event.getPlayer().sendMessage("You gave "+ChatColor.YELLOW.toString()+"1 "+ChatColor.WHITE.toString()+"Gold Bar to "+ChatColor.GREEN.toString()+"Savania");
				if(event.getPlayer().getItemInHand().getAmount() == 1)event.getPlayer().getInventory().setItemInHand(null);
				else{
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount()-1);
					event.getPlayer().getInventory().setItemInHand(event.getPlayer().getItemInHand());
				}
				Conflict.SAVANIA_WORTH++;
				event.getPlayer().updateInventory();
			}
			event.getPlayer().sendMessage("Savania has "+ChatColor.GREEN.toString()+Conflict.SAVANIA_WORTH+" Gold!");
		}else if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.ABATTON_LOCATION_DRIFTER.getBlock().getLocation())){
			try{
				if(Conflict.handler.inGroup(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), "Drifter")
						&& !Conflict.handler.inGroup(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), "Peasant")){
					if(((Conflict.OCEIAN_PLAYERS.size()-Conflict.ABATTON_PLAYERS.size()) < -5) || (Conflict.SAVANIA_PLAYERS.size()-Conflict.ABATTON_PLAYERS.size()) < -5){
						event.getPlayer().sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");

					}else{
						System.out.println("-------------------UPGRADE-DEBUG-----------");
						System.out.println("Sponge hit by player: "+event.getPlayer());
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pex promote "+event.getPlayer().getName());
						Conflict.ABATTON_PLAYERS.add(event.getPlayer().getName());
						plugin.getServer().broadcastMessage(event.getPlayer().getName()+ChatColor.GOLD.toString()+" has joined the Capital of "+ChatColor.WHITE.toString()+"Abatton"+ChatColor.GOLD.toString());
						if(Conflict.ABATTON_LOCATION_SPAWN != null) event.getPlayer().teleport(Conflict.ABATTON_LOCATION_SPAWN);
						event.getPlayer().sendMessage(ChatColor.BLUE.toString()+"Congrats!  You've been accepted!!! You can leave this area now");
						event.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF,10));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_SWORD,1));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.WOOD,64));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.TORCH,20));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_PICKAXE,1));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.STONE,64));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.COBBLESTONE,64));
						System.out.println("----------------------------------------------");
						
					}
				}
			}catch(NullPointerException e){}
		}else if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.OCEIAN_LOCATION_DRIFTER.getBlock().getLocation())){
			try{
				if(Conflict.handler.inGroup(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), "Drifter")
						&& !Conflict.handler.inGroup(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), "Peasant")){
					if((Conflict.ABATTON_PLAYERS.size()-Conflict.OCEIAN_PLAYERS.size()) < -5 || (Conflict.SAVANIA_PLAYERS.size()-Conflict.OCEIAN_PLAYERS.size()) < -5){
						event.getPlayer().sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");

					}else{
						System.out.println("-------------------UPGRADE-DEBUG-----------");
						System.out.println("Sponge hit by player: "+event.getPlayer());
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pex promote "+event.getPlayer().getName());
						Conflict.OCEIAN_PLAYERS.add(event.getPlayer().getName());
						plugin.getServer().broadcastMessage(event.getPlayer().getName()+ChatColor.GOLD.toString()+" has joined the Capital of "+ChatColor.WHITE.toString()+"Oceian"+ChatColor.GOLD.toString());
						if(Conflict.OCEIAN_LOCATION_SPAWN != null) event.getPlayer().teleport(Conflict.OCEIAN_LOCATION_SPAWN);
						event.getPlayer().sendMessage(ChatColor.BLUE.toString()+"Congrats!  You've been accepted!!! You can leave this area now");
						event.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF,10));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_SWORD,1));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.WOOD,64));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.TORCH,20));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_PICKAXE,1));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.STONE,64));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.COBBLESTONE,64));
						System.out.println("----------------------------------------------");
						
					}
				}
			}catch(NullPointerException e){}
		}else if(event.getClickedBlock() != null && event.getClickedBlock().getLocation().equals(Conflict.SAVANIA_LOCATION_DRIFTER.getBlock().getLocation())){
			try{
				if(Conflict.handler.inGroup(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), "Drifter")
						&& !Conflict.handler.inGroup(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), "Peasant")){
					if((Conflict.ABATTON_PLAYERS.size()-Conflict.SAVANIA_PLAYERS.size()) < -5 || (Conflict.OCEIAN_PLAYERS.size()-Conflict.SAVANIA_PLAYERS.size()) < -5){
						event.getPlayer().sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");

					}else{
						System.out.println("-------------------UPGRADE-DEBUG-----------");
						System.out.println("Sponge hit by player: "+event.getPlayer());
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pex promote "+event.getPlayer().getName());
						Conflict.SAVANIA_PLAYERS.add(event.getPlayer().getName());
						plugin.getServer().broadcastMessage(event.getPlayer().getName()+ChatColor.GOLD.toString()+" has joined the Capital of "+ChatColor.WHITE.toString()+"Savania"+ChatColor.GOLD.toString());
						if(Conflict.SAVANIA_LOCATION_SPAWN != null) event.getPlayer().teleport(Conflict.SAVANIA_LOCATION_SPAWN);
						event.getPlayer().sendMessage(ChatColor.BLUE.toString()+"Congrats!  You've been accepted!!! You can leave this area now");
						event.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF,10));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_SWORD,1));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.WOOD,64));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.TORCH,20));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_PICKAXE,1));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.STONE,64));
						event.getPlayer().getInventory().addItem(new ItemStack(Material.COBBLESTONE,64));
						System.out.println("----------------------------------------------");
						
					}
				}
			}catch(NullPointerException e){}
		}
	}
	@EventHandler
	public static void onPlayerRespawn(PlayerRespawnEvent event){
		if(event.getPlayer().getWorld().getName().equals("survival") && (event.getRespawnLocation().distance(Conflict.TRADE_BLACKSMITH) < 200 || event.getRespawnLocation().distance(Conflict.TRADE_ENCHANTMENTS) < 200
				|| event.getRespawnLocation().distance(Conflict.TRADE_MYSTPORTAL) < 200 || event.getRespawnLocation().distance(Conflict.TRADE_POTIONS) < 200
				|| event.getRespawnLocation().distance(Conflict.TRADE_RICHPORTAL) < 200 || event.getRespawnLocation().equals(event.getPlayer().getWorld().getSpawnLocation()))){
			if(Conflict.ABATTON_PLAYERS.contains(event.getPlayer().getName()))event.setRespawnLocation(Conflict.ABATTON_LOCATION_SPAWN);
			else if(Conflict.OCEIAN_PLAYERS.contains(event.getPlayer().getName()))event.setRespawnLocation(Conflict.OCEIAN_LOCATION_SPAWN);
			else if(Conflict.SAVANIA_PLAYERS.contains(event.getPlayer().getName()))event.setRespawnLocation(Conflict.SAVANIA_LOCATION_SPAWN);
		}
	}
}
