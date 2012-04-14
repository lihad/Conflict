package Lihad.Conflict.Command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.Conflict;
import Lihad.Conflict.Util.BeyondUtil;

public class CommandHandler implements CommandExecutor {
	public static Conflict plugin;
	public ItemStack post;
	public CommandHandler(Conflict instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string,
			String[] arg) {
		if(cmd.getName().equalsIgnoreCase("point") && arg.length == 2 && arg[0].equalsIgnoreCase("set") && sender instanceof Player && ((Player)sender).isOp()){
			if(!Conflict.PLAYER_SET_SELECT.isEmpty() && Conflict.PLAYER_SET_SELECT.containsKey(((Player)sender).getName())){
				((Player)sender).sendMessage(ChatColor.LIGHT_PURPLE.toString()+"Selection turned off");
				Conflict.PLAYER_SET_SELECT.remove(((Player)sender).getName());
			}else if(arg[1].equalsIgnoreCase("Abatton") || arg[1].equalsIgnoreCase("Oceian") || arg[1].equalsIgnoreCase("Savania")
					|| arg[1].equalsIgnoreCase("blacksmith") || arg[1].equalsIgnoreCase("potions") || arg[1].equalsIgnoreCase("enchantments")
					|| arg[1].equalsIgnoreCase("richportal") || arg[1].equalsIgnoreCase("mystportal")
					|| arg[1].equalsIgnoreCase("drifterabatton") || arg[1].equalsIgnoreCase("drifteroceian") || arg[1].equalsIgnoreCase("driftersavania")){
				((Player)sender).sendMessage(ChatColor.LIGHT_PURPLE.toString()+"Please select a position");
				Conflict.PLAYER_SET_SELECT.put(((Player)sender).getName(), arg[1]);
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("rarity") && arg.length == 0){
			if(BeyondUtil.rarity(((Player)sender).getItemInHand()) >= 60)((Player)sender).sendMessage("The Rarity Index of your "+ChatColor.BLUE.toString()+((Player)sender).getItemInHand().getType().name()+" is "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity(((Player)sender).getItemInHand()))+BeyondUtil.rarity(((Player)sender).getItemInHand()));
			else ((Player)sender).sendMessage("This item has no Rarity Index");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("myst") && arg.length == 3){
			if(((Player)sender).getWorld().getName().equals("mystworld")){	
				if(Integer.parseInt(arg[0]) < 15000 && Integer.parseInt(arg[0]) > 500 
						&& Integer.parseInt(arg[1]) < 15000 && Integer.parseInt(arg[1]) > 500 
						&& Integer.parseInt(arg[2]) < 15000 && Integer.parseInt(arg[2]) > 500){
					((Player)sender).teleport(new Location(plugin.getServer().getWorld("survival"),Integer.parseInt(arg[0]),Integer.parseInt(arg[1]),Integer.parseInt(arg[2])));
				}
			}else ((Player)sender).sendMessage("You are not in the correct world to use this command");
		}else if(cmd.getName().equalsIgnoreCase("protectcity") && arg.length == 1){
			if(Integer.parseInt(arg[0]) <= 500 && Integer.parseInt(arg[0]) > -1){
				if(Conflict.ABATTON_GENERALS.contains(((Player)sender).getName())){
					Conflict.ABATTON_PROTECTION = Integer.parseInt(arg[0]);
				}else if(Conflict.OCEIAN_GENERALS.contains(((Player)sender).getName())){
					Conflict.OCEIAN_PROTECTION = Integer.parseInt(arg[0]);
				}else if(Conflict.SAVANIA_GENERALS.contains(((Player)sender).getName())){
					Conflict.SAVANIA_PROTECTION = Integer.parseInt(arg[0]);
				}
			}else{
				((Player)sender).sendMessage("Invalid number. 0-500");
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("cca") && ((Player)sender).isOp()){
			if(arg.length == 1 && arg[0].equalsIgnoreCase("count")){
				((Player)sender).sendMessage("Counts - [AB - "+Conflict.ABATTON_PLAYERS.size()+" OC - "+Conflict.OCEIAN_PLAYERS.size()+" SA - "+Conflict.SAVANIA_PLAYERS.size()+"]");
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("ttrade")){
				((Player)sender).sendMessage("Abatton Temp - "+Conflict.ABATTON_TRADES_TEMP);
				((Player)sender).sendMessage("Oceian Temp - "+Conflict.OCEIAN_TRADES_TEMP);
				((Player)sender).sendMessage("Savania Temp - "+Conflict.SAVANIA_TRADES_TEMP);
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("trade")){
				((Player)sender).sendMessage("Abatton - "+Conflict.ABATTON_TRADES);
				((Player)sender).sendMessage("Oceian - "+Conflict.OCEIAN_TRADES);
				((Player)sender).sendMessage("Savania - "+Conflict.SAVANIA_TRADES);
			}else if(arg.length == 3 && arg[0].equalsIgnoreCase("cmove")){
				if(plugin.getServer().getPlayer(arg[2]) != null){
					if(arg[1].equalsIgnoreCase("abatton")){
						Conflict.ABATTON_PLAYERS.add(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.OCEIAN_PLAYERS.remove(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.SAVANIA_PLAYERS.remove(plugin.getServer().getPlayer(arg[2]).getName());
						((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a Member of Abatton");
					}
					else if(arg[1].equalsIgnoreCase("oceian")){
						Conflict.ABATTON_PLAYERS.remove(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.OCEIAN_PLAYERS.add(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.SAVANIA_PLAYERS.remove(plugin.getServer().getPlayer(arg[2]).getName());
						((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a Member of Oceian");
					}
					else if(arg[1].equalsIgnoreCase("savania")){
						Conflict.ABATTON_PLAYERS.remove(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.OCEIAN_PLAYERS.remove(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.SAVANIA_PLAYERS.add(plugin.getServer().getPlayer(arg[2]).getName());
						((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a Member of Savania");
					}else{
						((Player)sender).sendMessage("Invalid City.  Try, abatton, oceian or savania");
					}
				}else{
					((Player)sender).sendMessage("Player not online or invalid. Safety Enabled. Upgrade denied.");
				}
			}else if(arg.length == 3 && arg[0].equalsIgnoreCase("gassign")){
				if(plugin.getServer().getPlayer(arg[2]) != null){
					if(arg[1].equalsIgnoreCase("abatton")){
						Conflict.ABATTON_GENERALS.add(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2])).setPrefix(ChatColor.WHITE.toString()+"["+ChatColor.LIGHT_PURPLE.toString()+"AB-General"+ChatColor.WHITE.toString()+"]", null);
						((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a General of Abatton");
					}
					else if(arg[1].equalsIgnoreCase("oceian")){
						Conflict.OCEIAN_GENERALS.add(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2])).setPrefix(ChatColor.WHITE.toString()+"["+ChatColor.LIGHT_PURPLE.toString()+"OC-General"+ChatColor.WHITE.toString()+"]", null);
						((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a General of Oceian");
					}
					else if(arg[1].equalsIgnoreCase("savania")){
						Conflict.SAVANIA_GENERALS.add(plugin.getServer().getPlayer(arg[2]).getName());
						Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2])).setPrefix(ChatColor.WHITE.toString()+"["+ChatColor.LIGHT_PURPLE.toString()+"SA-General"+ChatColor.WHITE.toString()+"]", null);
						((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is now a General of Savania");
					}else{
						((Player)sender).sendMessage("Invalid City.  Try, abatton, oceian or savania");
					}
				}else{
					((Player)sender).sendMessage("Player not online or invalid. Safety Enabled. Upgrade denied.");
				}

			}else if(arg.length == 3 && arg[0].equalsIgnoreCase("gremove")){
				if(Conflict.ABATTON_GENERALS.contains(arg[2]) && arg[1].equalsIgnoreCase("abatton")){
					Conflict.ABATTON_GENERALS.remove(plugin.getServer().getPlayer(arg[2]).getName());
					Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2]).getName()).setPrefix("", null);
					((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is no longer a General of Abatton");
				}
				else if(Conflict.OCEIAN_GENERALS.contains(arg[2]) && arg[1].equalsIgnoreCase("oceian")){
					Conflict.OCEIAN_GENERALS.remove(plugin.getServer().getPlayer(arg[2]).getName());
					Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2]).getName()).setPrefix("", null);
					((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is no longer a General of Oceian");
				}
				else if(Conflict.SAVANIA_GENERALS.contains(arg[2]) && arg[1].equalsIgnoreCase("savania")){
					Conflict.SAVANIA_GENERALS.remove(plugin.getServer().getPlayer(arg[2]).getName());
					Conflict.ex.getUser(plugin.getServer().getPlayer(arg[2]).getName()).setPrefix("", null);
					((Player)sender).sendMessage("Player "+plugin.getServer().getPlayer(arg[2]).getName()+" is no longer a General of Savania");
				}else{
					((Player)sender).sendMessage("Invalid City or Player isn't a General");
				}
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("worth")){
				((Player)sender).sendMessage("Worth - [AB - "+Conflict.ABATTON_WORTH+" OC - "+Conflict.OCEIAN_WORTH+" SA - "+Conflict.SAVANIA_WORTH+"]");
			}else if(arg.length == 4 && arg[0].equalsIgnoreCase("worth") && arg[1].equalsIgnoreCase("modify")){
				if(arg[2].equalsIgnoreCase("abatton")){
					Conflict.ABATTON_WORTH = Conflict.ABATTON_WORTH + Integer.parseInt(arg[3]);
					((Player)sender).sendMessage("Abatton Worth = "+Conflict.ABATTON_WORTH);
				}
				else if(arg[2].equalsIgnoreCase("oceian")){
					Conflict.OCEIAN_WORTH = Conflict.OCEIAN_WORTH + Integer.parseInt(arg[3]);
					((Player)sender).sendMessage("Oceian Worth = "+Conflict.OCEIAN_WORTH);
				}
				else if(arg[2].equalsIgnoreCase("savania")){
					Conflict.SAVANIA_WORTH = Conflict.SAVANIA_WORTH + Integer.parseInt(arg[3]);
					((Player)sender).sendMessage("Savania Worth = "+Conflict.SAVANIA_WORTH);
				}else{
					((Player)sender).sendMessage("Invalid City.  Try, abatton, oceian or savania");
				}
			}else if(arg.length == 4 && arg[0].equalsIgnoreCase("worth") && arg[1].equalsIgnoreCase("set")){
				if(arg[2].equalsIgnoreCase("abatton")){
					Conflict.ABATTON_WORTH = Integer.parseInt(arg[3]);
					((Player)sender).sendMessage("Abatton Worth = "+Conflict.ABATTON_WORTH);
				}
				else if(arg[2].equalsIgnoreCase("oceian")){
					Conflict.OCEIAN_WORTH = Integer.parseInt(arg[3]);
					((Player)sender).sendMessage("Oceian Worth = "+Conflict.OCEIAN_WORTH);
				}
				else if(arg[2].equalsIgnoreCase("savania")){
					Conflict.SAVANIA_WORTH = Integer.parseInt(arg[3]);
					((Player)sender).sendMessage("Savania Worth = "+Conflict.SAVANIA_WORTH);
				}else{
					((Player)sender).sendMessage("Invalid City.  Try, abatton, oceian or savania");
				}
			}else if(arg.length == 1 && arg[0].equalsIgnoreCase("generals")){
				((Player)sender).sendMessage("AB - "+Conflict.ABATTON_GENERALS);
				((Player)sender).sendMessage("OC - "+Conflict.OCEIAN_GENERALS);
				((Player)sender).sendMessage("SA - "+Conflict.SAVANIA_GENERALS);
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("cc") && arg.length == 0){
			if(Conflict.ABATTON_PLAYERS.contains(((Player)sender).getName()))((Player)sender).performCommand("ch Abatton poontang");
			else if(Conflict.OCEIAN_PLAYERS.contains(((Player)sender).getName()))((Player)sender).performCommand("ch Oceian cuntcloister");
			else if(Conflict.SAVANIA_PLAYERS.contains(((Player)sender).getName()))((Player)sender).performCommand("ch Savania dicktrick");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("post") && arg.length == 0){
			if(BeyondUtil.rarity(((Player)sender).getItemInHand()) >= 60){
				((Player)sender).chat(BeyondUtil.getColorOfRarity(BeyondUtil.rarity(((Player)sender).getItemInHand()))+"["+((Player)sender).getItemInHand().getType().name()+"] Rarity Index : "+BeyondUtil.rarity(((Player)sender).getItemInHand()));
				post = ((Player)sender).getItemInHand();
			}
			else ((Player)sender).sendMessage("This item has no Rarity Index so it can't be posted to chat");
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("look") && arg.length == 0){
			if(post != null){
				Player player =((Player)sender);
				player.sendMessage(ChatColor.YELLOW.toString()+" -------------------------------- ");
				player.sendMessage(BeyondUtil.getColorOfRarity(BeyondUtil.rarity(post))+"["+post.getType().name()+"] Rarity Index : "+BeyondUtil.rarity(post));
				for(int i = 0; i<post.getEnchantments().keySet().size(); i++){
					player.sendMessage(" -- "+ChatColor.BLUE.toString()+((Enchantment)(post.getEnchantments().keySet().toArray()[i])).getName()+ChatColor.WHITE.toString()+" LVL"+BeyondUtil.getColorOfLevel(post.getEnchantmentLevel(((Enchantment)(post.getEnchantments().keySet().toArray()[i]))))+post.getEnchantmentLevel(((Enchantment)(post.getEnchantments().keySet().toArray()[i]))));
				}
				if(post.getEnchantments().keySet().size() <= 0)player.sendMessage(ChatColor.WHITE.toString()+" -- This Item Has No Enchants");

				player.sendMessage(ChatColor.YELLOW.toString()+" -------------------------------- ");

			}else ((Player)sender).sendMessage("There is no item to look at");
			return true;
		}

		else if(cmd.getName().equalsIgnoreCase("gear") && arg.length == 0){
			double total = (BeyondUtil.rarity(((Player)sender).getInventory().getHelmet())+BeyondUtil.rarity(((Player)sender).getInventory().getLeggings())+BeyondUtil.rarity(((Player)sender).getInventory().getBoots())+BeyondUtil.rarity(((Player)sender).getInventory().getChestplate())+BeyondUtil.rarity(((Player)sender).getInventory().getItemInHand()))/5.00;
			((Player)sender).sendMessage(BeyondUtil.getColorOfRarity(total)+"Your Gear Rating is : "+total);
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("gear") && arg.length == 1){
			if(plugin.getServer().getPlayer(arg[1]) != null){
				Player player = plugin.getServer().getPlayer(arg[1]);
				double total = (BeyondUtil.rarity(player.getInventory().getHelmet())+BeyondUtil.rarity(player.getInventory().getLeggings())+BeyondUtil.rarity(player.getInventory().getBoots())+BeyondUtil.rarity(player.getInventory().getChestplate())+BeyondUtil.rarity(player.getInventory().getItemInHand()))/5.00;
				((Player)sender).sendMessage(player.getName()+" has a Gear Rating of "+BeyondUtil.getColorOfRarity(total)+total);
			}else ((Player)sender).sendMessage("This player either doesn't exist, or isn't online");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("abatton") && arg.length == 0 && Conflict.UNASSIGNED_PLAYERS.contains(((Player)sender).getName())){
			if((Conflict.SAVANIA_PLAYERS.size()-Conflict.ABATTON_PLAYERS.size()) < -10 || (Conflict.OCEIAN_PLAYERS.size()-Conflict.ABATTON_PLAYERS.size()) < -10){
				((Player)sender).sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");
			}else{
				Conflict.ABATTON_PLAYERS.add(((Player)sender).getName());
				Conflict.UNASSIGNED_PLAYERS.remove(((Player)sender).getName());
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("oceian") && arg.length == 0 && Conflict.UNASSIGNED_PLAYERS.contains(((Player)sender).getName())){
			if((Conflict.SAVANIA_PLAYERS.size()-Conflict.OCEIAN_PLAYERS.size()) < -10 || (Conflict.ABATTON_PLAYERS.size()-Conflict.OCEIAN_PLAYERS.size()) < -10){
				((Player)sender).sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");

			}else{
				Conflict.OCEIAN_PLAYERS.add(((Player)sender).getName());
				Conflict.UNASSIGNED_PLAYERS.remove(((Player)sender).getName());
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("savania") && arg.length == 0 && Conflict.UNASSIGNED_PLAYERS.contains(((Player)sender).getName())){
			if((Conflict.ABATTON_PLAYERS.size()-Conflict.SAVANIA_PLAYERS.size()) < -10 || (Conflict.OCEIAN_PLAYERS.size()-Conflict.SAVANIA_PLAYERS.size()) < -10){
				((Player)sender).sendMessage(ChatColor.BLUE.toString()+"This Capital is Over Capacity!  Try joining one of the others, or wait and try later.");

			}else{
				Conflict.SAVANIA_PLAYERS.add(((Player)sender).getName());
				Conflict.UNASSIGNED_PLAYERS.remove(((Player)sender).getName());
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("spawn") && arg.length == 0){
			if(Conflict.ABATTON_LOCATION_SPAWN != null && Conflict.ABATTON_PLAYERS.contains(((Player)sender).getName()))((Player)sender).teleport(Conflict.ABATTON_LOCATION_SPAWN);
			else if(Conflict.OCEIAN_LOCATION_SPAWN != null && Conflict.OCEIAN_PLAYERS.contains(((Player)sender).getName()))((Player)sender).teleport(Conflict.OCEIAN_LOCATION_SPAWN);
			else if(Conflict.SAVANIA_LOCATION_SPAWN != null && Conflict.SAVANIA_PLAYERS.contains(((Player)sender).getName()))((Player)sender).teleport(Conflict.SAVANIA_LOCATION_SPAWN);
			else((Player)sender).teleport(((Player)sender).getWorld().getSpawnLocation());
			return true;
		}else if(cmd.getName().equalsIgnoreCase("nulls") && arg.length == 0 && ((Player)sender).isOp()){
			((Player)sender).teleport(((Player)sender).getWorld().getSpawnLocation());
		}else if(cmd.getName().equalsIgnoreCase("setcityspawn") && arg.length == 0){
			if(Conflict.ABATTON_GENERALS.contains(((Player)sender).getName())) Conflict.ABATTON_LOCATION_SPAWN = ((Player)sender).getLocation() ;
			else if(Conflict.OCEIAN_GENERALS.contains(((Player)sender).getName())) Conflict.OCEIAN_LOCATION_SPAWN = ((Player)sender).getLocation() ;
			else if(Conflict.SAVANIA_GENERALS.contains(((Player)sender).getName())) Conflict.SAVANIA_LOCATION_SPAWN = ((Player)sender).getLocation() ;
			else((Player)sender).sendMessage(ChatColor.LIGHT_PURPLE.toString()+"Unable to use this command");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("purchase") && arg.length == 0){
			((Player)sender).sendMessage("Possible Perks: weapondrops, armordrops, potiondrops, tooldrops, bowdrops, shield, strike");
			return true;
		}else if(cmd.getName().equalsIgnoreCase("purchase") && arg.length == 1){
			if(Conflict.ABATTON_GENERALS.contains(((Player)sender).getName())){
				if(Conflict.ABATTON_WORTH >= 500){
					if(!Conflict.ABATTON_PERKS.contains(arg[0].toLowerCase())){
						if(arg[0].equalsIgnoreCase("weapondrops")){
							Conflict.ABATTON_PERKS.add("weapondrops");
							Conflict.ABATTON_WORTH = Conflict.ABATTON_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("armordrops")){
							Conflict.ABATTON_PERKS.add("armordrops");
							Conflict.ABATTON_WORTH = Conflict.ABATTON_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("potiondrops")){
							Conflict.ABATTON_PERKS.add("potiondrops");
							Conflict.ABATTON_WORTH = Conflict.ABATTON_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("tooldrops")){
							Conflict.ABATTON_PERKS.add("tooldrops");
							Conflict.ABATTON_WORTH = Conflict.ABATTON_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("bowdrops")){
							Conflict.ABATTON_PERKS.add("bowdrops");
							Conflict.ABATTON_WORTH = Conflict.ABATTON_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("shield")){
							Conflict.ABATTON_PERKS.add("shield");
							Conflict.ABATTON_WORTH = Conflict.ABATTON_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("strike")){
							Conflict.ABATTON_PERKS.add("strike");
							Conflict.ABATTON_WORTH = Conflict.ABATTON_WORTH - 500;
						}else((Player)sender).sendMessage("Invalid Perk");
					}else((Player)sender).sendMessage("Abatton currently owns perk: "+arg[0]);
				}else((Player)sender).sendMessage("Abatton does not have enough gold to purchase any ability");
			}else if(Conflict.OCEIAN_GENERALS.contains(((Player)sender).getName())){
				if(Conflict.OCEIAN_WORTH >= 500){
					if(!Conflict.OCEIAN_PERKS.contains(arg[0].toLowerCase())){
						if(arg[0].equalsIgnoreCase("weapondrops")){
							Conflict.OCEIAN_PERKS.add("weapondrops");
							Conflict.OCEIAN_WORTH = Conflict.OCEIAN_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("armordrops")){
							Conflict.OCEIAN_PERKS.add("armordrops");
							Conflict.OCEIAN_WORTH = Conflict.OCEIAN_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("potiondrops")){
							Conflict.OCEIAN_PERKS.add("potiondrops");
							Conflict.OCEIAN_WORTH = Conflict.OCEIAN_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("tooldrops")){
							Conflict.OCEIAN_PERKS.add("tooldrops");
							Conflict.OCEIAN_WORTH = Conflict.OCEIAN_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("bowdrops")){
							Conflict.OCEIAN_PERKS.add("bowdrops");
							Conflict.OCEIAN_WORTH = Conflict.OCEIAN_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("shield")){
							Conflict.OCEIAN_PERKS.add("shield");
							Conflict.OCEIAN_WORTH = Conflict.OCEIAN_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("strike")){
							Conflict.OCEIAN_PERKS.add("strike");
							Conflict.OCEIAN_WORTH = Conflict.OCEIAN_WORTH - 500;
						}else((Player)sender).sendMessage("Invalid Perk");
					}else((Player)sender).sendMessage("Oceian currently owns perk: "+arg[0]);
				}else((Player)sender).sendMessage("Oceian does not have enough gold to purchase any ability");
			}else if(Conflict.SAVANIA_GENERALS.contains(((Player)sender).getName())){
				if(Conflict.SAVANIA_WORTH >= 500){
					if(!Conflict.SAVANIA_PERKS.contains(arg[0].toLowerCase())){
						if(arg[0].equalsIgnoreCase("weapondrops")){
							Conflict.SAVANIA_PERKS.add("weapondrops");
							Conflict.SAVANIA_WORTH = Conflict.SAVANIA_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("armordrops")){
							Conflict.SAVANIA_PERKS.add("armordrops");
							Conflict.SAVANIA_WORTH = Conflict.SAVANIA_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("potiondrops")){
							Conflict.SAVANIA_PERKS.add("potiondrops");
							Conflict.SAVANIA_WORTH = Conflict.SAVANIA_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("tooldrops")){
							Conflict.SAVANIA_PERKS.add("tooldrops");
							Conflict.SAVANIA_WORTH = Conflict.SAVANIA_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("bowdrops")){
							Conflict.SAVANIA_PERKS.add("bowdrops");
							Conflict.SAVANIA_WORTH = Conflict.SAVANIA_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("shield")){
							Conflict.SAVANIA_PERKS.add("shield");
							Conflict.SAVANIA_WORTH = Conflict.SAVANIA_WORTH - 500;
						}else if(arg[0].equalsIgnoreCase("strike")){
							Conflict.SAVANIA_PERKS.add("strike");
							Conflict.SAVANIA_WORTH = Conflict.SAVANIA_WORTH - 500;
						}else((Player)sender).sendMessage("Invalid Perk");
					}else((Player)sender).sendMessage("Savania currently owns perk: "+arg[0]);
				}else((Player)sender).sendMessage("Savania does not have enough gold to purchase any ability");
			}else((Player)sender).sendMessage("You are not allowed to run this command.");
			return true;
		}
		return false;
	}
}
