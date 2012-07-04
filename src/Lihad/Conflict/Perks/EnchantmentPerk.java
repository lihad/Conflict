package Lihad.Conflict.Perks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.Util.BeyondUtil;
import Lihad.Conflict.Conflict;

public class EnchantmentPerk extends BlockPerk {

    public EnchantmentPerk(String n) { super(n); }

    @Override
    public boolean activate(Player player) {

        ItemStack stack = player.getItemInHand();
        
        if (!BeyondUtil.isDiamondItem(stack)) {
            //player.sendMessage("Enchants node only works on diamond gear!");
            return false;
        }

        int uses = 0;
        if( Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.containsKey(player.getName()) ) {
            uses = Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.get(player.getName());
        }

        if( uses >= 5) {
            player.sendMessage("You have accessed enchantments too many times today");
            return true;
        }

        ItemStack newItem = BeyondUtil.addRandomEnchant(stack);
        if (newItem == null) { 
            player.sendMessage("Can't enchant that!");
            return false;
        }
        player.setItemInHand(newItem);
        uses++;
        Conflict.TRADE_ENCHANTMENTS_PLAYER_USES.put(player.getName(), uses);
        player.updateInventory();
        return true;
    }
};