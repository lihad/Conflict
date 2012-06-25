package Lihad.Conflict;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.Util.BeyondUtil;

public class EnchantmentPerk extends BlockPerk {

    public EnchantmentPerk(String n) { super(n); }

    @Override
    public void activate(Player player) {

        int uses = 0;
        if( Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.containsKey(player.getName()) ) {
            uses = Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(player.getName());
        }

        if( uses >= 5) {
            player.sendMessage("You have accessed enchantments too many times today");
            return;
        }

        ItemStack stack = player.getItemInHand();
        
        if (BeyondUtil.isDiamondItem(stack)) {
            ItemStack newItem = BeyondUtil.addRandomEnchant(stack);
            if (newItem == null) { 
                player.sendMessage("Can't enchant that!");
                return;
            }
            player.setItemInHand(newItem);
            uses++;
            Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(player.getName(), uses);
            player.updateInventory();
        }
        else {
            player.sendMessage("Enchants node only works on diamond gear!");
        }
    }
};