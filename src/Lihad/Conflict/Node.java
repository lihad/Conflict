package Lihad.Conflict;

import java.util.Set;
import java.util.HashSet;

import org.bukkit.Location;

public class Node {

    public Node(String n) { name = n; }
    public Node(String n, Location l) { name = n; center = l;}

    String name;

    int radius;
    int radiusSquared;

    Location center;

    public int getRadius() { return this.radius; }
    public int getRadiusSquared() { return radiusSquared; }
    public void setRadius(int r) { radius = r; radiusSquared = r*r; }

    public String getName() { return name; }
    
    public Location getLocation() { return center; }
    public void setLocation(Location l) { center = l; }
};