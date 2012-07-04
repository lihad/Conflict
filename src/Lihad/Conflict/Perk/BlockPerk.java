package Lihad.Conflict.Perk;

//import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BlockPerk extends Perk {

    public BlockPerk(String n) { super(n); }

    // Activate
    // Returns true if this perk was activated.
    public boolean activate(Player p) { return false;}

};