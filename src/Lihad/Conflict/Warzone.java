package Lihad.Conflict;

import java.util.Set;
import java.util.HashSet;

import org.bukkit.Location;

import Lihad.Conflict.Perk.*;

public class Warzone extends Node {
    public Warzone(String n, Location l, int r) {
        super(n, l, r); 
        blockProtected = true;
    }
    
    @Override
    public void save(org.bukkit.configuration.ConfigurationSection section) {
        super.save(section);
        section.set("type", "Warzone");
    }
}
