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

        // Nodes are hardcoded.  Nodes, trades, and perks in information.yaml is deprecated
        // TODO: Load from node.xml, once files are pre-populated
        
        Conflict.nodes.clear();
        org.bukkit.World survival = org.bukkit.Bukkit.getServer().getWorld("survival");

        // Warzones
        Conflict.nodes.add(new Warzone("Tower",   new Location(survival, 998,115,7), 200));
        Conflict.nodes.add(new Warzone("Cavern",  new Location(survival, 303,28,960), 200));
        Conflict.nodes.add(new Warzone("Fort",    new Location(survival, -809,66,589), 200));
        Conflict.nodes.add(new Warzone("Pit",     new Location(survival, -807,6,-587), 200));
        Conflict.nodes.add(new Warzone("Castle",  new Location(survival, 311,76,-955), 200));
        
        // Block nodes
        Node node = new Node("LihadCityBlacksmith", new Location(survival, 157,69,-70), 10);
        node.addPerk(Conflict.Blacksmith);
        Conflict.nodes.add(node);
	}

	public static void saveConfig() {
    
        //---------------
        // Old-style save
        // TODO:  Delete this after new save code is in

        Conflict.Abatton.save(Conflict.information.getConfigurationSection("Capitals.Abatton"));
        Conflict.Oceian .save(Conflict.information.getConfigurationSection("Capitals.Oceian"));
        Conflict.Savania.save(Conflict.information.getConfigurationSection("Capitals.Savania"));
    
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
            perksConfig.save(perksConfigFile);

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
	