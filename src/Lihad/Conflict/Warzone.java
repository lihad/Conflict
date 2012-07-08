package Lihad.Conflict;

import java.util.Set;
import java.util.HashSet;

import org.bukkit.Location;

import Lihad.Conflict.Perk.*;

public class Warzone extends Node {
    public Warzone(String n) { super(n); }
    
    @Override
    public void save(org.bukkit.configuration.ConfigurationSection section) {
        super.save(section);
        section.set("type", "Warzone");
    }
}
