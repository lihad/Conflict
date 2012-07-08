package Lihad.Conflict.Information;


import java.util.LinkedList;
import java.util.List;
import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Lihad.Conflict.*;
import Lihad.Conflict.Perk.*;

public class BeyondInfo {
	public static List<String> something = new LinkedList<String>();

	public static void loadConfig(){
    
        Conflict.Abatton.load(Conflict.information.getConfigurationSection("Capitals.Abatton"));
        Conflict.Oceian .load(Conflict.information.getConfigurationSection("Capitals.Oceian"));
        Conflict.Savania.load(Conflict.information.getConfigurationSection("Capitals.Savania"));

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

	public static void saveConfig() {
    
        //---------------
        // Old-style save
        // TODO:  Delete this after new save code is in

        Conflict.Abatton.save(Conflict.information.getConfigurationSection("Capitals.Abatton"));
        Conflict.Oceian .save(Conflict.information.getConfigurationSection("Capitals.Oceian"));
        Conflict.Savania.save(Conflict.information.getConfigurationSection("Capitals.Savania"));
    
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
        
        //---------------
        // New-style save

        try {
            // cityname.yml
            List<String> cityNames = new LinkedList<String>();
            for (City city : Conflict.cities) {
            
                cityNames.add(city.getName());
            
                FileConfiguration cityConfig = new YamlConfiguration();
                
                city.save(cityConfig);
                
                File cityConfigFile = new File(Conflict.plugin.getDataFolder(), city.getName() + ".yml");
                cityConfig.save(cityConfigFile);
            }
            
            // nodes.yml
            FileConfiguration nodesConfig = new YamlConfiguration();
            for (Node node : Conflict.nodes) {
                ConfigurationSection section = nodesConfig.createSection(node.getName());
                node.save(section);
            }
            File nodesConfigFile = new File(Conflict.plugin.getDataFolder(), "nodes.yml");
            nodesConfig.save(nodesConfigFile);

            // perks.yml
            FileConfiguration perksConfig = new YamlConfiguration();
            for (Perk perk : Conflict.perks) {
                ConfigurationSection section = perksConfig.createSection(perk.getName());
                perk.save(section);
            }
            File perksConfigFile = new File(Conflict.plugin.getDataFolder(), "perks.yml");
            perksConfig.save(nodesConfigFile);

            // config.yml
            FileConfiguration defaultConfig = Conflict.plugin.getConfig();
            defaultConfig.set("cities", cityNames);
            Conflict.plugin.saveConfig();

        } catch (java.io.IOException e) {
            e.printStackTrace();
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
	