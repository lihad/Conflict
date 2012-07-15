package Lihad.Conflict.Perk;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.entity.Player;

import Lihad.Conflict.Util.BeyondUtil;
import Lihad.Conflict.*;

public class BlacksmithPerk extends BlockPerk {

    public BlacksmithPerk() {
        super("Blacksmith"); 
        purchasable = true;
    }

    Map<String, Integer> playerUses = new HashMap<String, Integer>();

    @Override
    public boolean activate(Player player) {

        if(player.getItemInHand().getDurability() != 0)  {

            int uses = 0;
            if( playerUses.containsKey(player.getName()) ) {
                uses = playerUses.get(player.getName());
            }
            
            if( uses >= 5) {
               player.sendMessage("You have accessed blacksmith too many times today");
                return true;
            }

            player.getItemInHand().setDurability((short) 0);
            player.updateInventory();
            uses++;
            
            playerUses.put(player.getName(), uses);
            return true;
        }

        // TODO: Replace with a generic "nothing happens" message if the node couldn't fire any perks.
        player.sendMessage("This item is either unable to be repaired or is at max durability");
        return false;
    
    }
};