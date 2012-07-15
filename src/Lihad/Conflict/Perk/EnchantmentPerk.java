package Lihad.Conflict.Perk;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.Util.BeyondUtil;
import Lihad.Conflict.Conflict;

public class EnchantmentPerk extends BlockPerk {

    public EnchantmentPerk() {
        super("EnchantmentBlock");
        purchasable = true;
    }

    Map<String, Integer> playerUses = new HashMap<String, Integer>();

    @Override
    public boolean activate(Player player) {

        ItemStack stack = player.getItemInHand();
        
        if (!BeyondUtil.isDiamondItem(stack)) {
            //player.sendMessage("Enchants node only works on diamond gear!");
            return false;
        }

        int uses = 0;
        if( playerUses.containsKey(player.getName()) ) {
            uses = playerUses.get(player.getName());
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
        playerUses.put(player.getName(), uses);
        player.updateInventory();
        return true;
    }
};