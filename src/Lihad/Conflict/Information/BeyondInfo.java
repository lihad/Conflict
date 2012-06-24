package Lihad.Conflict.Information;


import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import Lihad.Conflict.Conflict;
import Lihad.Conflict.Node;

public class BeyondInfo {
	public static List<String> something = new LinkedList<String>();

	public static void loader(){
    
        Conflict.Abatton.loadConfig(Conflict.information.getConfigurationSection("Capitals.Abatton"));
        Conflict.Oceian.loadConfig (Conflict.information.getConfigurationSection("Capitals.Oceian"));
        Conflict.Savania.loadConfig(Conflict.information.getConfigurationSection("Capitals.Savania"));

		Conflict.TRADE_BLACKSMITH =     toLocation(Conflict.information, "Trades.blacksmith");
		Conflict.TRADE_POTIONS =        toLocation(Conflict.information, "Trades.potions");
		Conflict.TRADE_ENCHANTMENTS =   toLocation(Conflict.information, "Trades.enchantments");
		Conflict.TRADE_RICHPORTAL =     toLocation(Conflict.information, "Trades.richportal");
		Conflict.TRADE_MYSTPORTAL =     toLocation(Conflict.information, "Trades.mystportal");
        
        Conflict.nodes.clear();
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.0.Name", "Tower"),   toLocation(Conflict.information, "Nodes.0.Location", Conflict.TRADE_POTIONS)));
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.1.Name", "Cavern"),  toLocation(Conflict.information, "Nodes.1.Location", Conflict.TRADE_RICHPORTAL)));
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.2.Name", "Fort"),    toLocation(Conflict.information, "Nodes.2.Location", Conflict.TRADE_MYSTPORTAL)));
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.3.Name", "Pit"),     toLocation(Conflict.information, "Nodes.3.Location", Conflict.TRADE_BLACKSMITH)));
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.4.Name", "Castle"),  toLocation(Conflict.information, "Nodes.4.Location", Conflict.TRADE_ENCHANTMENTS)));
	}

	public static void writer(){

        Conflict.Abatton.saveConfig(Conflict.information.getConfigurationSection("Capitals.Abatton"));
        Conflict.Oceian.saveConfig (Conflict.information.getConfigurationSection("Capitals.Oceian"));
        Conflict.Savania.saveConfig(Conflict.information.getConfigurationSection("Capitals.Savania"));
    
		Conflict.information.set("Trades.blacksmith", toString(Conflict.TRADE_BLACKSMITH));
		Conflict.information.set("Trades.potions", toString(Conflict.TRADE_POTIONS));
		Conflict.information.set("Trades.enchantments", toString(Conflict.TRADE_ENCHANTMENTS));
		Conflict.information.set("Trades.richportal", toString(Conflict.TRADE_RICHPORTAL));
		Conflict.information.set("Trades.mystportal", toString(Conflict.TRADE_MYSTPORTAL));
        
        int i = 0;
        for (Node n : Conflict.nodes) {
            Conflict.information.set("Nodes." + i + ".Name", n.getName());
            Conflict.information.set("Nodes." + i + ".Location", toString(n.getLocation()));
            i++;
        }
	}
	public static Location toLocation(ConfigurationSection section, String path){
		String[] array;
		String str = section.getString(path);
		if(str == null) return null;
		array = str.split(",");
		Location location = new Location(org.bukkit.Bukkit.getServer().getWorld(array[3]), Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
		return location;
	}
	public static Location toLocation(ConfigurationSection section, String path, Location def) {
        Location l = toLocation(section, path);
        if (l == null) { return def; }
        return l;
    }
	public static String toString(Location location){
		if(location == null) return null;
		return (location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ()+","+location.getWorld().getName());
	}
}
	