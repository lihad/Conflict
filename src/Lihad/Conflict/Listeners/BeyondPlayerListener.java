package Lihad.Conflict.Listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.*;
import Lihad.Conflict.Perk.*;
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
			if(Conflict.isUnassigned(event.getPlayer().getName())){
				event.setTo(event.getFrom());
				event.getPlayer().sendMessage(Conflict.NOTICECOLOR+"You need to join a Capital before continuing...");
				event.getPlayer().sendMessage(Conflict.TEXTCOLOR+"Type "+ChatColor.GRAY+"/join <cityname> confirm");
				event.getPlayer().sendMessage(Conflict.TEXTCOLOR+"You can choose one of: " + Conflict.CITYCOLOR + Conflict.cities);
			}
			for (City city: Conflict.cities)
			{
				if (city.getLocation().distance(event.getTo()) < 500.0
						&& city.getLocation().distance(event.getFrom()) >= 500.0){
					event.getPlayer().sendMessage(Conflict.NOTICECOLOR + "You have entered the City of " + Conflict.CITYCOLOR + city.getName());
				}
				else if(city.getLocation().distance(event.getTo()) > 500.0
						&& city.getLocation().distance(event.getFrom()) <= 500.0){
						event.getPlayer().sendMessage(Conflict.NOTICECOLOR + "You have left the City of " + Conflict.CITYCOLOR + city.getName());
				}
			}
			if(event.getPlayer().getWorld().getSpawnLocation().distance(event.getTo()) > 5000.0
					&& event.getPlayer().getWorld().getSpawnLocation().distance(event.getFrom()) <= 5000.0){
				event.getPlayer().sendMessage(Conflict.NOTICECOLOR + "You have entered the Wilderness");
			}
			else if(event.getPlayer().getWorld().getSpawnLocation().distance(event.getTo()) < 5000.0
					&& event.getPlayer().getWorld().getSpawnLocation().distance(event.getFrom()) >= 5000.0){
				event.getPlayer().sendMessage(Conflict.NOTICECOLOR + "You have entered the Cityscape");
			}
		}
	}
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event){
		if(Conflict.getPlayerCity(event.getPlayer().getName()) == null && !Conflict.handler.inGroup(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), "Drifter")){
			if(!Conflict.isUnassigned(event.getPlayer().getName()))
				Conflict.addUnassigned(event.getPlayer().getName());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public static void onPlayerPortal(PlayerPortalEvent event){
		if(event.getCause().equals(TeleportCause.NETHER_PORTAL) && event.getFrom().getWorld().getName().equals("survival") && Conflict.MystPortal.getNode().getLocation().distance(event.getFrom()) < 10){
			if( Conflict.PlayerCanUseTrade(event.getPlayer().getName(), "mystportal") ) {
				event.getPlayer().sendMessage("Shaaaaazaaam!");
				event.getPlayer().teleport(new Location(plugin.getServer().getWorld("mystworld"), 0.0, 0.0, 0.0));
				event.setTo(new Location(plugin.getServer().getWorld("mystworld"), 0.0, 0.0, 0.0));
				event.setCancelled(true);
			}else{
				event.getPlayer().sendMessage("Epic Fail");
			}
		}
	}
	@EventHandler   
	public static void onPlayerTeleport(PlayerTeleportEvent event){

        Location dest = event.getTo();
        Location src = event.getFrom();
        Player player = event.getPlayer();
        
        if (Conflict.war != null) {
            // During wartime, cancel any tp in or out of nodes.
            if (Conflict.war.getPlayerTeam(player) != null) {
                for (Node n : Conflict.nodes) {
                    if (n.isInRadius(dest) || n.isInRadius(src)) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
    
	@EventHandler
	public static void onPlayerInteractEntity(PlayerInteractEntityEvent event){
		//if(event.getRightClicked() instanceof Villager && event.getPlayer().getInventory().contains(Material.DIAMOND_BLOCK)
		City city = Conflict.getPlayerCity(event.getPlayer().getName());
		if(event.getRightClicked() instanceof Villager && event.getPlayer().getLevel() >= 25
				&& (city != null && city.getPerks().contains("enchantup"))){
			if(event.getPlayer().getItemInHand() != null){
				if(!event.getPlayer().getItemInHand().getEnchantments().isEmpty()){
					Enchantment enchantment = (Enchantment) event.getPlayer().getItemInHand().getEnchantments().keySet().toArray()[(Conflict.random.nextInt(event.getPlayer().getItemInHand().getEnchantments().size()))];
					if(event.getPlayer().getItemInHand().getEnchantmentLevel(enchantment) < BeyondUtil.maxEnchantLevel(enchantment)){
						event.getPlayer().getItemInHand().addUnsafeEnchantment(enchantment, event.getPlayer().getItemInHand().getEnchantmentLevel(enchantment)+1);
						event.getPlayer().sendMessage(Conflict.PERKCOLOR + "WOOT!! "+enchantment.toString()+" is now level "+event.getPlayer().getItemInHand().getEnchantmentLevel(enchantment));
						//ItemStack stack = event.getPlayer().getInventory().getItem(event.getPlayer().getInventory().first(Material.DIAMOND_BLOCK));
						//if(stack.getAmount() <= 1){
						//	stack.setTypeId(0);
						//}else{
						//	stack.setAmount(stack.getAmount()-1);
						//}
						//event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().first(Material.DIAMOND_BLOCK),stack);
                        event.getPlayer().setLevel(event.getPlayer().getLevel() - 25);
						event.getPlayer().updateInventory();
					}
					else{
						event.getPlayer().getItemInHand().addUnsafeEnchantment(enchantment, 1);
						event.getPlayer().sendMessage(Conflict.PERKCOLOR + "Oh no!  That enchant was way too high for me to handle...");
						event.getPlayer().setLevel(event.getPlayer().getLevel() - 10);
					}
				}else{
					event.getPlayer().sendMessage(Conflict.PERKCOLOR + "MMMhmm.  It would seem as if this item has no enchants on it for me to upgrade.");
				}
			}
		}
	}

	@EventHandler
	public static void onPlayerExpChange(PlayerExpChangeEvent event){
		if(event.getAmount() > 0){
			List<Entity> entities = event.getPlayer().getNearbyEntities(20, 20, 20);
			for(int i=0;i<entities.size();i++){
				if(entities.get(i) instanceof Player 
						&& ((Conflict.Abatton.hasPlayer(((Player)entities.get(i)).getName()) && Conflict.Abatton.hasPlayer(event.getPlayer().getName()))
								|| (Conflict.Oceian.hasPlayer(((Player)entities.get(i)).getName()) && Conflict.Oceian.hasPlayer(event.getPlayer().getName()))
								|| (Conflict.Savania.hasPlayer(((Player)entities.get(i)).getName()) && Conflict.Savania.hasPlayer(event.getPlayer().getName())))
				){
					((Player)entities.get(i)).giveExp(event.getAmount());
				}
			}
		}
	}

	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event){
        
        if (event.getClickedBlock() == null) {
            // WTF, Bukkit!
            return;
        }
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        for (Perk p : Conflict.perks) {
            if (p instanceof BlockPerk) {
                if (p.getNode() == null) { 
                    continue; 
                }
                if (!block.getLocation().equals(p.getNode().getLocation())) { continue; }
                if (!Conflict.PlayerCanUseTrade(event.getPlayer().getName(), p.getName())) { 
                    event.getPlayer().sendMessage("You can't use this perk!");
                    continue;
                }
                ((BlockPerk)p).activate(event.getPlayer());
            }
        }
               
        if(block.getLocation().equals(Conflict.Abatton.getLocation().getBlock().getLocation())){
			if(player.getItemInHand().getType() == Material.GOLD_INGOT){
				player.sendMessage("You gave "+ChatColor.YELLOW.toString()+"1 "+ChatColor.WHITE.toString()+"Gold Bar to "+ChatColor.GREEN.toString()+"Abatton");
				if(player.getItemInHand().getAmount() == 1){
					player.getInventory().setItemInHand(null);
					Conflict.Abatton.addMoney(1);
				}else if(player.isSneaking()){
					Conflict.Abatton.addMoney(player.getItemInHand().getAmount());
					player.getInventory().setItemInHand(null);
				}else{
					player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
					player.getInventory().setItemInHand(player.getItemInHand());
					Conflict.Abatton.addMoney(1);
				}
				player.updateInventory();
			}
			player.sendMessage("Abatton has "+ChatColor.GREEN.toString()+Conflict.Abatton.getMoney()+" Gold!");
		}else if(block.getLocation().equals(Conflict.Oceian.getLocation().getBlock().getLocation())){
			if(player.getItemInHand().getType() == Material.GOLD_INGOT){
				player.sendMessage("You gave "+ChatColor.YELLOW.toString()+"1 "+ChatColor.WHITE.toString()+"Gold Bar to "+ChatColor.GREEN.toString()+"Oceian");
				if(player.getItemInHand().getAmount() == 1){
					player.getInventory().setItemInHand(null);
					Conflict.Oceian.addMoney(1);
				}else if(player.isSneaking()){
					Conflict.Oceian.addMoney(player.getItemInHand().getAmount());
					player.getInventory().setItemInHand(null);
				}else{
					player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
					player.getInventory().setItemInHand(player.getItemInHand());
					Conflict.Oceian.addMoney(1);
				}
				player.updateInventory();
			}
			player.sendMessage("Oceian has "+ChatColor.GREEN.toString()+Conflict.Oceian.getMoney()+" Gold!");
		}else if(block.getLocation().equals(Conflict.Savania.getLocation().getBlock().getLocation())){
			if(player.getItemInHand().getType() == Material.GOLD_INGOT){
				player.sendMessage("You gave "+ChatColor.YELLOW.toString()+"1 "+ChatColor.WHITE.toString()+"Gold Bar to "+ChatColor.GREEN.toString()+"Savania");
				if(player.getItemInHand().getAmount() == 1){
					player.getInventory().setItemInHand(null);
					Conflict.Savania.addMoney(1);
				}else if(player.isSneaking()){
					Conflict.Savania.addMoney(player.getItemInHand().getAmount());
					player.getInventory().setItemInHand(null);
				}else{
					player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
					player.getInventory().setItemInHand(player.getItemInHand());
					Conflict.Savania.addMoney(1);
				}
				player.updateInventory();
			}
			player.sendMessage("Savania has "+ChatColor.GREEN.toString()+Conflict.Savania.getMoney()+" Gold!");
		}else if(block.getLocation().equals(Conflict.Abatton.getSpongeLocation().getBlock().getLocation())){
			try{
				if(Conflict.handler.inGroup(player.getWorld().getName(), player.getName(), "Drifter")
						&& !Conflict.handler.inGroup(player.getWorld().getName(), player.getName(), "Peasant")){
					if(((Conflict.Oceian.getPopulation()-Conflict.Abatton.getPopulation()) < -5) || (Conflict.Savania.getPopulation()-Conflict.Abatton.getPopulation()) < -5){
						player.sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");

					}else{
						System.out.println("-------------------UPGRADE-DEBUG-----------");
						System.out.println("Sponge hit by player: "+player);
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pex promote "+player.getName());
						Conflict.Abatton.addPlayer(player.getName());
						plugin.getServer().broadcastMessage(player.getName()+ChatColor.GOLD.toString()+" has joined the Capital of "+ChatColor.WHITE.toString()+"Abatton"+ChatColor.GOLD.toString());
						if(Conflict.Abatton.getSpawn() != null) player.teleport(Conflict.Abatton.getSpawn());
						player.sendMessage(ChatColor.BLUE.toString()+"Congrats!  You've been accepted!!! You can leave this area now");
						player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF,10));
						player.getInventory().addItem(new ItemStack(Material.IRON_SWORD,1));
						player.getInventory().addItem(new ItemStack(Material.WOOD,64));
						player.getInventory().addItem(new ItemStack(Material.TORCH,20));
						player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE,1));
						player.getInventory().addItem(new ItemStack(Material.STONE,64));
						player.getInventory().addItem(new ItemStack(Material.COBBLESTONE,64));
						System.out.println("----------------------------------------------");
						
					}
				}
			}catch(NullPointerException e){}
		}else if(block.getLocation().equals(Conflict.Oceian.getSpongeLocation().getBlock().getLocation())){
			try{
				if(Conflict.handler.inGroup(player.getWorld().getName(), player.getName(), "Drifter")
						&& !Conflict.handler.inGroup(player.getWorld().getName(), player.getName(), "Peasant")){
					if((Conflict.Abatton.getPopulation()-Conflict.Oceian.getPopulation()) < -5 || (Conflict.Savania.getPopulation()-Conflict.Oceian.getPopulation()) < -5){
						player.sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");
					}else{
						System.out.println("-------------------UPGRADE-DEBUG-----------");
						System.out.println("Sponge hit by player: "+player);
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pex promote "+player.getName());
						Conflict.Oceian.addPlayer(player.getName());
						plugin.getServer().broadcastMessage(player.getName()+ChatColor.GOLD.toString()+" has joined the Capital of "+ChatColor.WHITE.toString()+"Oceian"+ChatColor.GOLD.toString());
						if(Conflict.Oceian.getSpawn() != null) player.teleport(Conflict.Oceian.getSpawn());
						player.sendMessage(ChatColor.BLUE.toString()+"Congrats!  You've been accepted!!! You can leave this area now");
						player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF,10));
						player.getInventory().addItem(new ItemStack(Material.IRON_SWORD,1));
						player.getInventory().addItem(new ItemStack(Material.WOOD,64));
						player.getInventory().addItem(new ItemStack(Material.TORCH,20));
						player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE,1));
						player.getInventory().addItem(new ItemStack(Material.STONE,64));
						player.getInventory().addItem(new ItemStack(Material.COBBLESTONE,64));
						System.out.println("----------------------------------------------");
						
					}
				}
			}catch(NullPointerException e){}
		}else if(block.getLocation().equals(Conflict.Savania.getSpongeLocation().getBlock().getLocation())){
			try{
				if(Conflict.handler.inGroup(player.getWorld().getName(), player.getName(), "Drifter")
						&& !Conflict.handler.inGroup(player.getWorld().getName(), player.getName(), "Peasant")){
					if((Conflict.Abatton.getPopulation()-Conflict.Savania.getPopulation()) < -5 || (Conflict.Oceian.getPopulation()-Conflict.Savania.getPopulation()) < -5){
						player.sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");
					}else{
						System.out.println("-------------------UPGRADE-DEBUG-----------");
						System.out.println("Sponge hit by player: "+player);
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "pex promote "+player.getName());
						Conflict.Savania.addPlayer(player.getName());
						plugin.getServer().broadcastMessage(player.getName()+ChatColor.GOLD.toString()+" has joined the Capital of "+ChatColor.WHITE.toString()+"Savania"+ChatColor.GOLD.toString());
						if(Conflict.Savania.getSpawn() != null) player.teleport(Conflict.Savania.getSpawn());
						player.sendMessage(ChatColor.BLUE.toString()+"Congrats!  You've been accepted!!! You can leave this area now");
						player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF,10));
						player.getInventory().addItem(new ItemStack(Material.IRON_SWORD,1));
						player.getInventory().addItem(new ItemStack(Material.WOOD,64));
						player.getInventory().addItem(new ItemStack(Material.TORCH,20));
						player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE,1));
						player.getInventory().addItem(new ItemStack(Material.STONE,64));
						player.getInventory().addItem(new ItemStack(Material.COBBLESTONE,64));
						System.out.println("----------------------------------------------");
						
					}
				}
			}catch(NullPointerException e){}
		}
	}
	@EventHandler
	public static void onPlayerRespawn(PlayerRespawnEvent event){
		if(event.getPlayer().getWorld().getName().equals("survival") && (
            Conflict.Blacksmith.getNode().isInRadius(event.getRespawnLocation()) || 
            Conflict.Potions.getNode().isInRadius(event.getRespawnLocation()) || 
            Conflict.Enchantments.getNode().isInRadius(event.getRespawnLocation()) || 
            Conflict.MystPortal.getNode().isInRadius(event.getRespawnLocation()) || 
            Conflict.RichPortal.getNode().isInRadius(event.getRespawnLocation()) ||
            event.getRespawnLocation().equals(event.getPlayer().getWorld().getSpawnLocation()))) 
        {
			if(Conflict.Abatton.hasPlayer(event.getPlayer().getName()))event.setRespawnLocation(Conflict.Abatton.getSpawn());
			else if(Conflict.Oceian.hasPlayer(event.getPlayer().getName()))event.setRespawnLocation(Conflict.Oceian.getSpawn());
			else if(Conflict.Savania.hasPlayer(event.getPlayer().getName()))event.setRespawnLocation(Conflict.Savania.getSpawn());
		}
	}
    
    @EventHandler
    public static void onInventoryClose(org.bukkit.event.inventory.InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        if (event.getView().getType() == InventoryType.CRAFTING) {
            if (!player.isOp() && !Conflict.handler.has(player, "conflict.debug")) {
                BeyondUtil.nerfOverenchantedPlayerInventory(player);
            }
        }
        else if (event.getView().getType() == InventoryType.CHEST) {
            if (!player.isOp() && !Conflict.handler.has(player, "conflict.debug")) {
                BeyondUtil.nerfOverenchantedInventory(event.getInventory());
            }
        }
    }
    
    @EventHandler
    public static void onPlayerItemPickup(org.bukkit.event.player.PlayerPickupItemEvent event) {
        Player player = (Player)event.getPlayer();
        if (!player.isOp() && !Conflict.handler.has(player, "conflict.debug")) {
            ItemStack i = event.getItem().getItemStack();
            BeyondUtil.nerfOverenchantedItem(i);
        }
    }
}
