package Lihad.Conflict.Perk;
import Lihad.Conflict.*;

import org.bukkit.entity.Player;

public class Perk {

    public Perk(String n) { name = n; }
    
    public void Initialize(org.bukkit.plugin.Plugin plugin) { }

    String name;
    public String getName() { return name; }
    
    Node node;
    public Node getNode() { return node; }
    public void setNode(Node n) { node = n; }

    int purchaseCost = 500;
    int useCost = 0;
    int useCostUnowned = 100;
    public void setPurchaseCost(int c) { purchaseCost = c; }
    public void setUseCost(int c) { useCost = c; }
    public void setUseCostUnowned(int c) { useCostUnowned = c; }
    public int getPurchaseCost() { return purchaseCost; }
    public int getUseCost() { return useCost; }
    public int getUseCostUnowned() { return useCostUnowned; }

    public static Perk getPerkByName(String name) {
        for (Perk p : Conflict.perks) {
            if(name.equalsIgnoreCase(p.getName())){
                return p;
            }
        }
        return null;
    }

    public boolean load(org.bukkit.configuration.ConfigurationSection section) {
        purchaseCost = section.getInt("purchasecost", 500);
        useCost = section.getInt("usecost", 0);
        useCostUnowned = section.getInt("usecostunowned", 100);
        return true;
    }
    
    public void save(org.bukkit.configuration.ConfigurationSection section) {
        section.set("purchasecost", purchaseCost);
        section.set("usecost", useCost);
        section.set("usecostunowned", useCostUnowned);
    }

};