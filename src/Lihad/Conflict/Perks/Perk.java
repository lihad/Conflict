package Lihad.Conflict.Perks;
import Lihad.Conflict.*;

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
    void setPurchaseCost(int c) { purchaseCost = c; }
    void setUseCost(int c) { useCost = c; }
    int getPurchaseCost() { return purchaseCost; }
    int getUseCost() { return useCost; }

    public static Perk getPerkByName(String name) {
        for (Perk p : Conflict.perks) {
            if(name.equalsIgnoreCase(p.getName())){
                return p;
            }
        }
        return null;
    }
};