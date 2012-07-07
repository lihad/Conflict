package Lihad.Conflict;

import java.util.Set;
import java.util.HashSet;

import org.bukkit.Location;

import Lihad.Conflict.Perk.*;

public class Node {

    public Node(String n) { name = n; }
    public Node(String n, Location l) { name = n; center = l;}

    public Set<Perk> perks = new HashSet<Perk>();
    
    String name;

    int radius;
    int radiusSquared;

    Location center;

    public int getRadius() { return this.radius; }
    public void setRadius(int r) { radius = r; radiusSquared = r*r; }
    
    public boolean isInRadius(Location l) { 
        if (!center.getWorld().equals(l.getWorld())) {
            return false;
        }
        return (center.distanceSquared(l) <= radiusSquared); 
    }

    public String getName() { return name; }
    
    public Location getLocation() { return center; }
    public void setLocation(Location l) { center = l; }
    
    public void addPerk(Perk p) { perks.add(p); }
};
