package Lihad.Conflict;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import Lihad.Conflict.BeyondInfo;
import Lihad.Conflict.Perk.*;

public class Node {

    public Node(String n) { name = n; }
    public Node(String n, Location l, int r) { name = n; center = l; setRadius(r);}

    String name;

    public String getName() { return name; }

    int radius;
    int radiusSquared;
    
    boolean blockProtected = false;

    public int getRadius() { return this.radius; }
    public void setRadius(int r) { radius = r; radiusSquared = r*r; }
    public boolean isInRadius(Location l) { 
        if (!center.getWorld().equals(l.getWorld())) {
            return false;
        }
        return (center.distanceSquared(l) <= radiusSquared); 
    }
    public boolean isBlockProtected() { return blockProtected; }

    Location center;

    public Location getLocation() { return center; }
    public void setLocation(Location l) { center = l; }
    
    public Set<Perk> perks = new HashSet<Perk>();

    public Set<Perk> getPerks() { return perks; }
    public void addPerk(Perk p) { perks.add(p); }
    public void clearPerks() { perks.clear(); }
    
    public boolean load(ConfigurationSection section) {

        Location l = null;
        l = BeyondInfo.toLocation(section, "location");
        if (l == null) {
            // Node must have location to load
            return false;
        }
        setLocation(l);
        
        // optional attributes
        setRadius(section.getInt("radius", 10));
        blockProtected = section.getBoolean("preventblockedits", false);
        
        // Perks attached to this node
        List<String> perkNames = section.getStringList("perks");
        if (perkNames != null) {
            perks.clear();
            for (String n : perkNames) {
                Perk p = Perk.getPerkByName(n);
                if (n != null) {
                    addPerk(p);
                }
            }
        }
        
        return true;
    }
    
    public void save(ConfigurationSection section) {
    
        section.set("location", BeyondInfo.toString(center));
        section.set("radius", radius);
        section.set("type", "Node");
        section.set("preventblockedits", blockProtected);

        if (perks.size() > 0) {
            List<String> perkNames = new LinkedList<String>();
            for (Perk p : perks) {
                perkNames.add(p.getName());
            }
            section.set("perks", perkNames);
        }
    }

};
