package Lihad.Conflict;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class PotionPerk extends BlockPerk {

    public PotionPerk(String n) { super(n); }

    @Override
    public void activate(Player player) {

        if(player.getItemInHand().getType() == Material.GLASS_BOTTLE){

            int uses = 0;
            if( Conflict.TRADE_POTIONS_PLAYER_USES.containsKey(player.getName()) ) {
                uses = Conflict.TRADE_POTIONS_PLAYER_USES.get(player.getName());
            }

            if( uses >= 256) {
                player.sendMessage("You have accessed potions too many times today");
                return;
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

            Conflict.TRADE_POTIONS_PLAYER_USES.put(player.getName(), uses);
            player.updateInventory();
            
        }
    }
};