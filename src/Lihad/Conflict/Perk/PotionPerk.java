package Lihad.Conflict.Perk;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import Lihad.Conflict.Conflict;

public class PotionPerk extends BlockPerk {

    public PotionPerk() {
        super("Potions"); 
        purchasable = true;
    }

    Map<String, Integer> playerUses = new HashMap<String, Integer>();

    @Override
    public boolean activate(Player player) {

        if(player.getItemInHand().getType() == Material.GLASS_BOTTLE){

            int uses = 0;
            if( playerUses.containsKey(player.getName()) ) {
                uses = playerUses.get(player.getName());
            }

            if( uses >= 256) {
                player.sendMessage("You have accessed potions too many times today");
                return true;
            }

            // Transform as many bottles as we can, up to either the stack size or the number of remaining uses
            int numPotions = java.lang.Math.min(player.getItemInHand().getAmount(), 256 - uses);
            
            if (numPotions < player.getItemInHand().getAmount()) {
                ItemStack leftover = new ItemStack(Material.GLASS_BOTTLE, player.getItemInHand().getAmount() - numPotions);
                player.getWorld().dropItemNaturally(player.getLocation(), leftover);
            }
            
            ItemStack stack = new ItemStack(Material.EXP_BOTTLE, numPotions);
            player.setItemInHand(stack);
            uses += numPotions;

            playerUses.put(player.getName(), uses);
            player.updateInventory();
            return true;
        }
        // Not glass bottle
        return false;
    }
};