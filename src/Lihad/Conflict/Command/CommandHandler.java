package Lihad.Conflict.Command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.Conflict;
import Lihad.Conflict.City;
import Lihad.Conflict.Util.BeyondUtil;
import Lihad.Conflict.Information.BeyondInfo;

public class CommandHandler implements CommandExecutor {
	public static Conflict plugin;
	public ItemStack post;
	public CommandHandler(Conflict instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] arg) {
        // if (!(sender instanceof Player) {
            // // Console can't send Conflict commands.  Sorry.
            // return false;
        // }
        Player player = (Player)sender;
        
		if(cmd.getName().equalsIgnoreCase("point") && arg.length == 2 && arg[0].equalsIgnoreCase("set") && sender instanceof Player && (player).isOp()){
			if(!Conflict.PLAYER_SET_SELECT.isEmpty() && Conflict.PLAYER_SET_SELECT.containsKey((player).getName())){
				(player).sendMessage(ChatColor.LIGHT_PURPLE.toString()+"Selection turned off");
				Conflict.PLAYER_SET_SELECT.remove((player).getName());
			}else if(arg[1].equalsIgnoreCase("Abatton") || arg[1].equalsIgnoreCase("Oceian") || arg[1].equalsIgnoreCase("Savania")
					|| arg[1].equalsIgnoreCase("blacksmith") || arg[1].equalsIgnoreCase("potions") || arg[1].equalsIgnoreCase("enchantments")
					|| arg[1].equalsIgnoreCase("richportal") || arg[1].equalsIgnoreCase("mystportal")
					|| arg[1].equalsIgnoreCase("drifterabatton") || arg[1].equalsIgnoreCase("drifteroceian") || arg[1].equalsIgnoreCase("driftersavania")){
				(player).sendMessage(ChatColor.LIGHT_PURPLE.toString()+"Please select a position");
				Conflict.PLAYER_SET_SELECT.put((player).getName(), arg[1]);
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("cwho")){
			if(arg.length == 1){
				if(arg[0].equalsIgnoreCase("abatton")){
					List<Player> players = Arrays.asList(plugin.getServer().getOnlinePlayers());
					String message = "";
					for(int i = 0;i<players.size();i++){
						if(Conflict.Abatton.hasPlayer(players.get(i).getName()))	message = message.concat(players.get(i).getName()+" ");
					}
					(player).sendMessage(message);
				}else if(arg[0].equalsIgnoreCase("oceian")){
					List<Player> players = Arrays.asList(plugin.getServer().getOnlinePlayers());
					String message = "";
					for(int i = 0;i<players.size();i++){
						if(Conflict.Oceian.hasPlayer(players.get(i).getName()))	message = message.concat(players.get(i).getName()+" ");
					}
					(player).sendMessage(message);
				}else if(arg[0].equalsIgnoreCase("savania")){
					List<Player> players = Arrays.asList(plugin.getServer().getOnlinePlayers());
					String message = "";
					for(int i = 0;i<players.size();i++){
						if(Conflict.Savania.hasPlayer(players.get(i).getName()))	message = message.concat(players.get(i).getName()+" ");
					}
					(player).sendMessage(message);
				}else if(plugin.getServer().getPlayer(arg[0]) != null){
					if(Conflict.Abatton.hasPlayer(plugin.getServer().getPlayer(arg[0]).getName())) (player).sendMessage(arg[0]+" - Abatton");
					else if(Conflict.Oceian.hasPlayer(plugin.getServer().getPlayer(arg[0]).getName())) (player).sendMessage(arg[0]+" - Oceian");
					else if(Conflict.Savania.hasPlayer(plugin.getServer().getPlayer(arg[0]).getName())) (player).sendMessage(arg[0]+" - Savania");
					else (player).sendMessage(arg[0]+" - <None>");

				}else (player).sendMessage("Player is not online");

			}else (player).sendMessage("try '/cwho <playername>|<capname>");
			return true;

		}else if(cmd.getName().equalsIgnoreCase("rarity") && arg.length == 0){
			if(BeyondUtil.rarity((player).getItemInHand()) >= 60)(player).sendMessage("The Rarity Index of your "+ChatColor.BLUE.toString()+(player).getItemInHand().getType().name()+" is "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity((player).getItemInHand()))+BeyondUtil.rarity((player).getItemInHand()));
			else (player).sendMessage("This item has no Rarity Index");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("myst") && arg.length == 3){
			if((player).getWorld().getName().equals("mystworld")){	
				if(Integer.parseInt(arg[0]) < 100000 && Integer.parseInt(arg[0]) > -100000 
						&& Integer.parseInt(arg[1]) < 255 && Integer.parseInt(arg[1]) > 0
						&& Integer.parseInt(arg[2]) < 100000 && Integer.parseInt(arg[2]) > -100000){
					(player).teleport(new Location(plugin.getServer().getWorld("survival"),Integer.parseInt(arg[0]),Integer.parseInt(arg[1]),Integer.parseInt(arg[2])));
				}
			}else (player).sendMessage("You are not in the correct world to use this command");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("protectcity") && arg.length == 1){
			if(Integer.parseInt(arg[0]) <= 500 && Integer.parseInt(arg[0]) > -1){
				if(Conflict.Abatton.getGenerals().contains((player).getName())){
					Conflict.Abatton.setProtectionRadius(Integer.parseInt(arg[0]));
				}else if(Conflict.Oceian.getGenerals().contains((player).getName())){
					Conflict.Oceian.setProtectionRadius(Integer.parseInt(arg[0]));
				}else if(Conflict.Savania.getGenerals().contains((player).getName())){
					Conflict.Savania.setProtectionRadius(Integer.parseInt(arg[0]));
				}
			}else{
				(player).sendMessage("Invalid number. 0-500");
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("cca") && ((sender instanceof Player && (player).isOp()) || sender instanceof ConsoleCommandSender)){
			if(arg.length == 1 && arg[0].equalsIgnoreCase("count")){
				sender.sendMessage("Counts - [AB - "+Conflict.Abatton.getPopulation()+" OC - "+Conflict.Oceian.getPopulation()+" SA - "+Conflict.Savania.getPopulation()+"]");
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("trade")){
				sender.sendMessage("Abatton - "+Conflict.Abatton.getTrades());
				sender.sendMessage("Oceian - "+Conflict.Oceian.getTrades());
				sender.sendMessage("Savania - "+Conflict.Savania.getTrades());
			}else if(arg.length == 3 && arg[0].equalsIgnoreCase("cmove")){
				if(plugin.getServer().getPlayer(arg[2]) != null){
					if(arg[1].equalsIgnoreCase("abatton") && !Conflict.Abatton.hasPlayer(plugin.getServer().getPlayer(arg[2]).getName())){
						Conflict.Abatton.addPlayer(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.Oceian.removePlayer(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.Savania.removePlayer(plugin.getServer().getPlayer(arg[2]).getName());
						sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a Member of Abatton");
					}
					else if(arg[1].equalsIgnoreCase("oceian") && !Conflict.Oceian.hasPlayer(plugin.getServer().getPlayer(arg[2]).getName())){
						Conflict.Abatton.removePlayer(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.Oceian.addPlayer(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.Savania.removePlayer(plugin.getServer().getPlayer(arg[2]).getName());
						sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a Member of Oceian");
					}
					else if(arg[1].equalsIgnoreCase("savania") && !Conflict.Savania.hasPlayer(plugin.getServer().getPlayer(arg[2]).getName())){
						Conflict.Abatton.removePlayer(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.Oceian.removePlayer(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.Savania.addPlayer(plugin.getServer().getPlayer(arg[2]).getName());
						sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a Member of Savania");
					}else{
						sender.sendMessage("Invalid City.  Try, abatton, oceian or savania");
					}
				}else{
					sender.sendMessage("Player not online or invalid. Safety Enabled. Upgrade denied.");
				}
			}else if(arg.length == 3 && arg[0].equalsIgnoreCase("gassign")){
				if(plugin.getServer().getPlayer(arg[2]) != null){
					if(arg[1].equalsIgnoreCase("abatton")){
						Conflict.Abatton.getGenerals().add(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2])).setPrefix(ChatColor.WHITE.toString()+"["+ChatColor.LIGHT_PURPLE.toString()+"AB-General"+ChatColor.WHITE.toString()+"]", null);
						sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a General of Abatton");
					}
					else if(arg[1].equalsIgnoreCase("oceian")){
						Conflict.Oceian.getGenerals().add(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2])).setPrefix(ChatColor.WHITE.toString()+"["+ChatColor.LIGHT_PURPLE.toString()+"OC-General"+ChatColor.WHITE.toString()+"]", null);
						sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a General of Oceian");
					}
					else if(arg[1].equalsIgnoreCase("savania")){
						Conflict.Savania.getGenerals().add(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2])).setPrefix(ChatColor.WHITE.toString()+"["+ChatColor.LIGHT_PURPLE.toString()+"SA-General"+ChatColor.WHITE.toString()+"]", null);
						sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a General of Savania");
					}else{
						sender.sendMessage("Invalid City.  Try, abatton, oceian or savania");
					}
				}else{
					sender.sendMessage("Player not online or invalid. Safety Enabled. Upgrade denied.");
				}

			}else if(arg.length == 3 && arg[0].equalsIgnoreCase("gremove")){
				if(Conflict.Abatton.getGenerals().contains(arg[2]) && arg[1].equalsIgnoreCase("abatton")){
					Conflict.Abatton.getGenerals().remove(plugin.getServer().getPlayer(arg[2]).getName());
					Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2]).getName()).setPrefix("", null);
					sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is no longer a General of Abatton");
				}
				else if(Conflict.Oceian.getGenerals().contains(arg[2]) && arg[1].equalsIgnoreCase("oceian")){
					Conflict.Oceian.getGenerals().remove(plugin.getServer().getPlayer(arg[2]).getName());
					Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2]).getName()).setPrefix("", null);
					sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is no longer a General of Oceian");
				}
				else if(Conflict.Savania.getGenerals().contains(arg[2]) && arg[1].equalsIgnoreCase("savania")){
					Conflict.Savania.getGenerals().remove(plugin.getServer().getPlayer(arg[2]).getName());
					Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2]).getName()).setPrefix("", null);
					sender.sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is no longer a General of Savania");
				}else{
					sender.sendMessage("Invalid City or Player isn't a General");
				}
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("worth")){
				sender.sendMessage("Worth - [AB - "+Conflict.Abatton.getMoney()+" OC - "+Conflict.Oceian.getMoney()+" SA - "+Conflict.Savania.getMoney()+"]");
			}else if(arg.length == 4 && arg[0].equalsIgnoreCase("worth") && arg[1].equalsIgnoreCase("modify")){
				if(arg[2].equalsIgnoreCase("abatton")){
					Conflict.Abatton.addMoney(Integer.parseInt(arg[3]));
					sender.sendMessage("Abatton Worth = "+Conflict.Abatton.getMoney());
				}
				else if(arg[2].equalsIgnoreCase("oceian")){
					Conflict.Oceian.addMoney(Integer.parseInt(arg[3]));
					sender.sendMessage("Oceian Worth = "+Conflict.Oceian.getMoney());
				}
				else if(arg[2].equalsIgnoreCase("savania")){
					Conflict.Savania.addMoney(Integer.parseInt(arg[3]));
					sender.sendMessage("Savania Worth = "+Conflict.Savania.getMoney());
				}else{
					sender.sendMessage("Invalid City.  Try, abatton, oceian or savania");
				}
			}else if(arg.length == 4 && arg[0].equalsIgnoreCase("worth") && arg[1].equalsIgnoreCase("set")){
				if(arg[2].equalsIgnoreCase("abatton")){
					Conflict.Abatton.setMoney(Integer.parseInt(arg[3]));
					sender.sendMessage("Abatton Worth = "+Conflict.Abatton.getMoney());
				}
				else if(arg[2].equalsIgnoreCase("oceian")){
					Conflict.Oceian.setMoney(Integer.parseInt(arg[3]));
					sender.sendMessage("Oceian Worth = "+Conflict.Oceian.getMoney());
				}
				else if(arg[2].equalsIgnoreCase("savania")){
					Conflict.Savania.setMoney(Integer.parseInt(arg[3]));
					sender.sendMessage("Savania Worth = "+Conflict.Savania.getMoney());
				}else{
					sender.sendMessage("Invalid City.  Try, abatton, oceian or savania");
				}
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("generals")){
				sender.sendMessage("AB - "+Conflict.Abatton.getGenerals());
				sender.sendMessage("OC - "+Conflict.Oceian.getGenerals());
				sender.sendMessage("SA - "+Conflict.Savania.getGenerals());
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("save")){
                Conflict.saveInfoFile();
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("reload")){
                Conflict.loadInfoFile(Conflict.information, Conflict.infoFile);
                BeyondInfo.loader();
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("cc") && arg.length == 0){
			if(Conflict.Abatton.hasPlayer((player).getName()))(player).performCommand("ch Abatton poontang");
			else if(Conflict.Oceian.hasPlayer((player).getName()))(player).performCommand("ch Oceian cuntcloister");
			else if(Conflict.Savania.hasPlayer((player).getName()))(player).performCommand("ch Savania dicktrick");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("post") && arg.length == 0){
			if(BeyondUtil.rarity((player).getItemInHand()) >= 60){
				(player).chat(BeyondUtil.getColorOfRarity(BeyondUtil.rarity((player).getItemInHand()))+"["+(player).getItemInHand().getType().name()+"] Rarity Index : "+BeyondUtil.rarity((player).getItemInHand()));
				post = (player).getItemInHand();
			}
			else (player).sendMessage("This item has no Rarity Index so it can't be posted to chat");
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("look") && arg.length == 0){
			if(post != null){
				player.sendMessage(ChatColor.YELLOW.toString()+" -------------------------------- ");
				player.sendMessage(BeyondUtil.getColorOfRarity(BeyondUtil.rarity(post))+"["+post.getType().name()+"] Rarity Index : "+BeyondUtil.rarity(post));
				for(int i = 0; i<post.getEnchantments().keySet().size(); i++){
					player.sendMessage(" -- "+ChatColor.BLUE.toString()+((Enchantment)(post.getEnchantments().keySet().toArray()[i])).getName()+ChatColor.WHITE.toString()+" LVL"+BeyondUtil.getColorOfLevel(post.getEnchantmentLevel(((Enchantment)(post.getEnchantments().keySet().toArray()[i]))))+post.getEnchantmentLevel(((Enchantment)(post.getEnchantments().keySet().toArray()[i]))));
				}
				if(post.getEnchantments().keySet().size() <= 0)player.sendMessage(ChatColor.WHITE.toString()+" -- This Item Has No Enchants");
				player.sendMessage(ChatColor.YELLOW.toString()+" -------------------------------- ");
			}else (player).sendMessage("There is no item to look at");
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("gear") && arg.length == 0){
			double total = (BeyondUtil.rarity((player).getInventory().getHelmet())+BeyondUtil.rarity((player).getInventory().getLeggings())+BeyondUtil.rarity((player).getInventory().getBoots())+BeyondUtil.rarity((player).getInventory().getChestplate())+BeyondUtil.rarity((player).getInventory().getItemInHand()))/5.00;
			(player).sendMessage(BeyondUtil.getColorOfRarity(total)+"Your Gear Rating is : "+total);
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("gear") && arg.length == 1){
			if(plugin.getServer().getPlayer(arg[1]) != null){
				Player target = plugin.getServer().getPlayer(arg[1]);
                org.bukkit.inventory.PlayerInventory inv = target.getInventory();
				double total = (BeyondUtil.rarity(inv.getHelmet())+BeyondUtil.rarity(inv.getLeggings())+BeyondUtil.rarity(inv.getBoots())+BeyondUtil.rarity(inv.getChestplate())+BeyondUtil.rarity(inv.getItemInHand()))/5.00;
				player.sendMessage(target.getName()+" has a Gear Rating of "+BeyondUtil.getColorOfRarity(total)+total);
			}else player.sendMessage("This player either doesn't exist, or isn't online");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("abatton") && arg.length == 0 && Conflict.UNASSIGNED_PLAYERS.contains((player).getName())){
			if((Conflict.Savania.getPopulation()-Conflict.Abatton.getPopulation()) < -10 || (Conflict.Oceian.getPopulation()-Conflict.Abatton.getPopulation()) < -10){
				(player).sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");
			}else{
				Conflict.Abatton.addPlayer((player).getName());
				Conflict.UNASSIGNED_PLAYERS.remove((player).getName());
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("oceian") && arg.length == 0 && Conflict.UNASSIGNED_PLAYERS.contains((player).getName())){
			if((Conflict.Savania.getPopulation()-Conflict.Oceian.getPopulation()) < -10 || (Conflict.Abatton.getPopulation()-Conflict.Oceian.getPopulation()) < -10){
				(player).sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");

			}else{
				Conflict.Oceian.addPlayer((player).getName());
				Conflict.UNASSIGNED_PLAYERS.remove((player).getName());
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("savania") && arg.length == 0 && Conflict.UNASSIGNED_PLAYERS.contains((player).getName())){
			if((Conflict.Abatton.getPopulation()-Conflict.Savania.getPopulation()) < -10 || (Conflict.Oceian.getPopulation()-Conflict.Savania.getPopulation()) < -10){
				(player).sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");

			}else{
				Conflict.Savania.addPlayer((player).getName());
				Conflict.UNASSIGNED_PLAYERS.remove((player).getName());
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("spawn") && arg.length == 0){
			if(Conflict.Abatton.getSpawn() != null && Conflict.Abatton.hasPlayer((player).getName()))(player).teleport(Conflict.Abatton.getSpawn());
			else if(Conflict.Oceian.getSpawn() != null && Conflict.Oceian.hasPlayer((player).getName()))(player).teleport(Conflict.Oceian.getSpawn());
			else if(Conflict.Savania.getSpawn() != null && Conflict.Savania.hasPlayer((player).getName()))(player).teleport(Conflict.Savania.getSpawn());
			else(player).teleport((player).getWorld().getSpawnLocation());
			return true;
		}else if(cmd.getName().equalsIgnoreCase("nulls") && arg.length == 0 && (player).isOp()){
			(player).teleport((player).getWorld().getSpawnLocation());
		}else if(cmd.getName().equalsIgnoreCase("setcityspawn") && arg.length == 0){
			if(Conflict.Abatton.getGenerals().contains((player).getName())) Conflict.Abatton.setSpawn(player.getLocation()) ;
			else if(Conflict.Oceian.getGenerals().contains((player).getName())) Conflict.Oceian.setSpawn(player.getLocation()) ;
			else if(Conflict.Savania.getGenerals().contains((player).getName())) Conflict.Savania.setSpawn(player.getLocation()) ;
			else(player).sendMessage(ChatColor.LIGHT_PURPLE.toString()+"Unable to use this command");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("purchase") && arg.length == 0){
			sender.sendMessage("Possible Perks: weapondrops, armordrops, potiondrops, tooldrops, bowdrops, shield, strike, endergrenade, enchantup, golddrops");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("purchase") && arg.length == 1){
			if(Conflict.Abatton.getGenerals().contains((player).getName())){
				if(Conflict.Abatton.getMoney() >= 500){
					if(!Conflict.Abatton.getPerks().contains(arg[0].toLowerCase())){
						if(arg[0].equalsIgnoreCase("weapondrops")){
							Conflict.Abatton.addPerk("weapondrops");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("armordrops")){
							Conflict.Abatton.addPerk("armordrops");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("potiondrops")){
							Conflict.Abatton.addPerk("potiondrops");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("tooldrops")){
							Conflict.Abatton.addPerk("tooldrops");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("bowdrops")){
							Conflict.Abatton.addPerk("bowdrops");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("shield")){
							Conflict.Abatton.addPerk("shield");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("strike")){
							Conflict.Abatton.addPerk("strike");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("endergrenade")){
							Conflict.Abatton.addPerk("endergrenade");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("enchantup")){
							Conflict.Abatton.addPerk("enchantup");
							Conflict.Abatton.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("golddrops")){
							Conflict.Abatton.addPerk("golddrops");
							Conflict.Abatton.subtractMoney(500);
						}else(player).sendMessage("Invalid Perk");
					}else(player).sendMessage("Abatton currently owns perk: "+arg[0]);
				}else(player).sendMessage("Abatton does not have enough gold to purchase any ability");
			}else if(Conflict.Oceian.getGenerals().contains((player).getName())){
				if(Conflict.Oceian.getMoney() >= 500){
					if(!Conflict.Oceian.getPerks().contains(arg[0].toLowerCase())){
						if(arg[0].equalsIgnoreCase("weapondrops")){
							Conflict.Oceian.addPerk("weapondrops");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("armordrops")){
							Conflict.Oceian.addPerk("armordrops");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("potiondrops")){
							Conflict.Oceian.addPerk("potiondrops");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("tooldrops")){
							Conflict.Oceian.addPerk("tooldrops");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("bowdrops")){
							Conflict.Oceian.addPerk("bowdrops");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("shield")){
							Conflict.Oceian.addPerk("shield");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("strike")){
							Conflict.Oceian.addPerk("strike");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("endergrenade")){
							Conflict.Oceian.addPerk("endergrenade");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("enchantup")){
							Conflict.Oceian.addPerk("enchantup");
							Conflict.Oceian.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("golddrops")){
							Conflict.Oceian.addPerk("golddrops");
							Conflict.Oceian.subtractMoney(500);
						}else(player).sendMessage("Invalid Perk");
					}else(player).sendMessage("Oceian currently owns perk: "+arg[0]);
				}else(player).sendMessage("Oceian does not have enough gold to purchase any ability");
			}else if(Conflict.Savania.getGenerals().contains((player).getName())){
				if(Conflict.Savania.getMoney() >= 500){
					if(!Conflict.Savania.getPerks().contains(arg[0].toLowerCase())){
						if(arg[0].equalsIgnoreCase("weapondrops")){
							Conflict.Savania.addPerk("weapondrops");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("armordrops")){
							Conflict.Savania.addPerk("armordrops");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("potiondrops")){
							Conflict.Savania.addPerk("potiondrops");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("tooldrops")){
							Conflict.Savania.addPerk("tooldrops");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("bowdrops")){
							Conflict.Savania.addPerk("bowdrops");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("shield")){
							Conflict.Savania.addPerk("shield");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("strike")){
							Conflict.Savania.addPerk("strike");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("endergrenade")){
							Conflict.Savania.addPerk("endergrenade");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("enchantup")){
							Conflict.Savania.addPerk("enchantup");
							Conflict.Savania.subtractMoney(500);
						}else if(arg[0].equalsIgnoreCase("golddrops")){
							Conflict.Savania.addPerk("golddrops");
							Conflict.Savania.subtractMoney(500);
						}else(player).sendMessage("Invalid Perk");
					}else(player).sendMessage("Savania currently owns perk: "+arg[0]);
				}else(player).sendMessage("Savania does not have enough gold to purchase any ability");
			}else(player).sendMessage("You are not allowed to run this command.");
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("warstats") && arg.length == 0){
            if (Conflict.war != null) {
                Conflict.war.postWarAutoList(sender);
            }
            return true;
        }
        else if(cmd.getName().equalsIgnoreCase("perks")) {
            City city = null;
            if (arg.length == 0 && sender instanceof Player) {
                city = Conflict.getPlayerCity((player).getName());
            }
            if (arg.length == 1 && (player).isOp()) {
                for (City c : Conflict.cities) {
                    if (c.getName().equals(arg[0])) {
                        city = c;
                        break;
                    }
                }
            }

            if (city == null) { return false; }
            
            sender.sendMessage("" + city + " mini-perks: " + city.getPerks().toString());
            
            // TODO: Give nodes in this message as well.
            sender.sendMessage("" + city + " nodes: " + city.getTrades().toString());
            
            return true;
        }
        else if(cmd.getName().equalsIgnoreCase("bnn") && Conflict.war != null && arg.length == 2 && arg[0].equalsIgnoreCase("reporter") && arg[1].equalsIgnoreCase("enable") && (sender instanceof Player)) {
            Conflict.war.reporters.add(player);                
        }
        return false;
	}
}
