package Lihad.Conflict;

import java.util.Calendar;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class War {

    enum CityEnum {
        None,
        Contested,
        Abatton,
        Oceian,
        Savania
    };
    
    public static CityEnum getPlayerCityEnum(String playerName) {
        if (Conflict.Abatton.hasPlayer(playerName)) return CityEnum.Abatton;
        if (Conflict.Oceian.hasPlayer(playerName)) return CityEnum.Oceian;
        if (Conflict.Savania.hasPlayer(playerName)) return CityEnum.Savania;
        return null;
    }
    
    public static Calendar cal = Calendar.getInstance();

    public static boolean warShouldStart() {

        cal.setTime(new Date(System.currentTimeMillis()));

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && cal.get(Calendar.HOUR_OF_DAY) == 19) return true;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && cal.get(Calendar.HOUR_OF_DAY) == 13) return true;
        return false;
    }
    
    public boolean warShouldEnd() {
        
        if (allNodesConquered) {
            return true;
        }
        
        cal.setTime(new Date(System.currentTimeMillis()));

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && (cal.get(Calendar.HOUR_OF_DAY) == 19 || cal.get(Calendar.HOUR_OF_DAY) == 20)) return false;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && (cal.get(Calendar.HOUR_OF_DAY) == 13 || cal.get(Calendar.HOUR_OF_DAY) == 14)) return false;
        return true;
    }


    class WarNode {
        public String name;
        public Location location;
        public int captureCounter = 0;
        public CityEnum owner = CityEnum.None;
        public CityEnum captureCityTemp;

        public Map<CityEnum, Integer> cityCounters = new HashMap<CityEnum, Integer>();
        public boolean conquered = false;
        
        public WarNode(String n, Location l) {
            name = n;
            location = l;
        };
    }

    public List<WarNode> nodes = new LinkedList<WarNode>();
    boolean allNodesConquered = false;

    public java.util.Set<Player> reporters = new java.util.HashSet<Player>();
    
    public War() {
    
        for (Node n : Conflict.nodes) {
            WarNode wn = new WarNode(n.name, n.getLocation());
            this.nodes.add(wn);
        }
    }
    
    public void postWarAutoList(org.bukkit.command.CommandSender sender){
        for (WarNode node : nodes) {
            String message = ChatColor.GOLD + node.name +" Tally | ";

            if (node.conquered) {
                message += ChatColor.GREEN + "Conquered by " + node.owner;
            }
            else if (node.cityCounters.isEmpty()) {
                message += ChatColor.DARK_AQUA + "Not claimed by any city!";
            }
            else {
                for (Map.Entry entry : node.cityCounters.entrySet()) {
                    CityEnum city = (CityEnum)entry.getKey();
                    int count = (Integer)entry.getValue();
                    
                    String countText = (city == node.owner) ? ("" + ChatColor.YELLOW + "[" + count + "]") : ("" + ChatColor.WHITE + count);
                    message += "" + ChatColor.AQUA + city + ": " + countText + "; ";
                }
            }

            if (sender != null) {
                sender.sendMessage(message);
            }
            else {
                Bukkit.getServer().broadcastMessage(message);
            }
        }
    }
    
    public void executeWarTick() {
    
        for (WarNode node : nodes) {
            node.captureCityTemp = CityEnum.None;
        }

        Player[] players = Bukkit.getServer().getOnlinePlayers();

        for(Player player : players){
            if(player.getLocation().getWorld().getName().equalsIgnoreCase("survival")){
            
                for (WarNode node : nodes) {
                    if (node.conquered) {
                        continue;
                    }
                    if (player.getLocation().distanceSquared(node.location) < 3*3){  
                        if(node.captureCityTemp == CityEnum.None){
                            node.captureCityTemp = getPlayerCityEnum(player.getName());
                        }
                        else if(node.captureCityTemp == CityEnum.Contested){
                            continue;
                        }
                        else if( getPlayerCityEnum(player.getName()) == node.owner ) {
                            continue;
                        }
                        else if( getPlayerCityEnum(player.getName()) == node.captureCityTemp ) {
                            node.captureCounter++;
                            player.sendMessage(ChatColor.GOLD+"Taking point. "+node.captureCounter+"/30");
                        }else{
                            node.captureCityTemp = CityEnum.Contested;
                            node.captureCounter = 0;
                        }
                    }
                }
            }
        }
        
        boolean unconqueredNodesRemain = false;
        
        for (WarNode node : nodes) {
            if (!node.conquered) {
                unconqueredNodesRemain = true;

                if((node.captureCityTemp != CityEnum.None) && (node.captureCityTemp != CityEnum.Contested) && node.captureCounter >= 30) {
                    node.owner = node.captureCityTemp;
                    node.captureCounter = 0;
                    Bukkit.getServer().broadcastMessage("" + ChatColor.RED + node.owner + " has taken control of " + node.name + "!");

                    if (!node.cityCounters.containsKey(node.owner)) {
                        node.cityCounters.put(node.owner, 0);
                    }
                }
                
                if (node.owner != CityEnum.None) {
                    int counter = node.cityCounters.get(node.owner);
                    counter++;
                    
                    if (counter > 3600) {
                        node.conquered = true;
                    }
                    node.cityCounters.put(node.owner, counter);
                }
            }
        }
        if (!unconqueredNodesRemain) {
            allNodesConquered = true;
        }
    }

    public void endWar() {
    
        LinkedList<String> prizes = new LinkedList<String>(Arrays.asList("blacksmith", "potions", "enchantments", "richportal", "mystportal"));
        
        if (prizes.size() != nodes.size()) { 
            Conflict.severe("Node count is off!  Expected " + prizes.size() + " but found " + nodes.size() + ".  Do you need to add or remove trades?");
        }

        // Mix up our prize bag!
        java.util.Collections.shuffle(prizes);
        
        Bukkit.getServer().broadcastMessage(ChatColor.RED+"Lay down your weapons and shag a wench!  The war has ended!");

        for (WarNode node : nodes) {
            
            CityEnum winner = CityEnum.None;
            
            if (node.conquered) {
                winner = node.owner;
            }
            else {
                // Not conquered.  We have to go through the cities and find the highest
                int highestScore = 0;
                for (Map.Entry entry : node.cityCounters.entrySet()) {
                    if ((Integer)entry.getValue() > highestScore) {
                        highestScore = (Integer)entry.getValue();
                        winner = (CityEnum)entry.getKey();
                    }
                }
            }
            
            String prize = prizes.pop();
            
            // TODO: Fix this once city objects are in
            if      (winner == CityEnum.Abatton) { Conflict.Abatton.addTrade(prize); }
            else if (winner == CityEnum.Oceian)  { Conflict.Oceian.addTrade(prize); }
            else if (winner == CityEnum.Savania) { Conflict.Savania.addTrade(prize); }
            
            // And move the trade location to the node.
            if      (prize == "blacksmith")     { Conflict.TRADE_BLACKSMITH = node.location; }
            else if (prize == "potions")        { Conflict.TRADE_POTIONS = node.location; }
            else if (prize == "enchantments")   { Conflict.TRADE_ENCHANTMENTS = node.location; }
            else if (prize == "richportal")     { Conflict.TRADE_RICHPORTAL = node.location; }
            else if (prize == "mystportal")     { Conflict.TRADE_MYSTPORTAL = node.location; }
            
            Bukkit.getServer().broadcastMessage("" + ChatColor.GOLD + winner + ChatColor.GRAY + " has won the " + node.name + ", and gains the " + ChatColor.LIGHT_PURPLE + prize + ChatColor.GRAY + " perk!");
        }
    }

};