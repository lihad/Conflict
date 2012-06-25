package Lihad.Conflict;

import org.bukkit.entity.Player;

import Lihad.Conflict.Util.BeyondUtil;

public class BlacksmithPerk extends BlockPerk {

    public BlacksmithPerk(String n) { super(n); }

    public void Activate(Player player) {

        if(player.getItemInHand().getDurability() != 0)  {

            int uses = 0;
            if( Conflict.TRADE_BLACKSMITH_PLAYER_USES.containsKey(player.getName()) ) {
                uses = Conflict.TRADE_BLACKSMITH_PLAYER_USES.get(player.getName());
            }
            
            if( uses >= 5) {
               player.sendMessage("You have accessed blacksmith too many times today");
                return;
            }

            player.getItemInHand().setDurability((short) 0);
            uses++;
            
            Conflict.TRADE_BLACKSMITH_PLAYER_USES.put(player.getName(), uses);
            player.updateInventory();
        }
        else {
            player.sendMessage("This item is either unable to be repaired or is at max durability");
        }
    
    }
};