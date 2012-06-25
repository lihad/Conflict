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

        // TODO: New config format will store names with nodes. For now, they're all nameless.
        org.bukkit.World survival = org.bukkit.Bukkit.getServer().getWorld("survival");
        Conflict.Blacksmith     .setNode(new Node("", toLocation(Conflict.information, "Trades.blacksmith", new Location(survival, -807,6,-587))));
        Conflict.Potions        .setNode(new Node("", toLocation(Conflict.information, "Trades.potions", new Location(survival, 998,115,7))));
        Conflict.Enchantments   .setNode(new Node("", toLocation(Conflict.information, "Trades.enchantments", new Location(survival, 311,76,-955))));
        Conflict.MystPortal     .setNode(new Node("", toLocation(Conflict.information, "Trades.mystportal", new Location(survival, -809,66,589))));
        Conflict.RichPortal     .setNode(new Node("", toLocation(Conflict.information, "Trades.richportal", new Location(survival, 303,28,960))));

        // TODO: New config format needs to store node radius too.
        Conflict.Blacksmith     .getNode().setRadius(200);
        Conflict.Potions        .getNode().setRadius(200);
        Conflict.Enchantments   .getNode().setRadius(200);
        Conflict.MystPortal     .getNode().setRadius(200);
        Conflict.RichPortal     .getNode().setRadius(200);
        
        Conflict.nodes.clear();
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.0.Name", "Tower"),   toLocation(Conflict.information, "Nodes.0.Location", Conflict.Potions.getNode().getLocation())));
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.1.Name", "Cavern"),  toLocation(Conflict.information, "Nodes.1.Location", Conflict.RichPortal.getNode().getLocation())));
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.2.Name", "Fort"),    toLocation(Conflict.information, "Nodes.2.Location", Conflict.MystPortal.getNode().getLocation())));
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.3.Name", "Pit"),     toLocation(Conflict.information, "Nodes.3.Location", Conflict.Blacksmith.getNode().getLocation())));
        Conflict.nodes.add(new Node(Conflict.information.getString("Nodes.4.Name", "Castle"),  toLocation(Conflict.information, "Nodes.4.Location", Conflict.Enchantments.getNode().getLocation())));
	}

	public static void writer(){

        Conflict.Abatton.saveConfig(Conflict.information.getConfigurationSection("Capitals.Abatton"));
        Conflict.Oceian.saveConfig (Conflict.information.getConfigurationSection("Capitals.Oceian"));
        Conflict.Savania.saveConfig(Conflict.information.getConfigurationSection("Capitals.Savania"));
    
        Conflict.information.set("Trades.blacksmith", toString(Conflict.Blacksmith.getNode().getLocation()));
        Conflict.information.set("Trades.potions", toString(Conflict.Potions.getNode().getLocation()));
        Conflict.information.set("Trades.enchantments", toString(Conflict.Enchantments.getNode().getLocation()));
        //Conflict.information.set("Trades.richportal", toString(Conflict.TRADE_RICHPORTAL));
        Conflict.information.set("Trades.mystportal", toString(Conflict.MystPortal.getNode().getLocation()));
        
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
	