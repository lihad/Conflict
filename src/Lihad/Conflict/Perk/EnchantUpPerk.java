package Lihad.Conflict.Perk;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;

import Lihad.Conflict.Conflict;
import Lihad.Conflict.Util.BeyondUtil;

public class EnchantUpPerk extends PassivePerk {

    int successLevels = 25;
    int failureLevels = 10;

    public EnchantUpPerk() {
        super("EnchantUp");
        purchasable = true;
    }

    @Override
    public void save(org.bukkit.configuration.ConfigurationSection section) {
        super.save(section);
        section.set("successlevels", successLevels);
        section.set("failurelevels", failureLevels);
    }

    @Override
    public void onPlayerInteractEntity(org.bukkit.event.player.PlayerInteractEntityEvent event) { 
    
        Player player = event.getPlayer();
        if(event.getRightClicked() instanceof Villager) {
            if (player.getLevel() >= successLevels) {
                if (Conflict.playerCanUsePerk(player, this)) {
                    ItemStack item = player.getItemInHand();
                    if(item != null && (!item.getEnchantments().isEmpty())) {
                        Enchantment enchantment = (Enchantment) item.getEnchantments().keySet().toArray()[(Conflict.random.nextInt(item.getEnchantments().size()))];
                        if(item.getEnchantmentLevel(enchantment) < BeyondUtil.maxEnchantLevel(enchantment)) {
                            item.addUnsafeEnchantment(enchantment, item.getEnchantmentLevel(enchantment)+1);
                            player.sendMessage(Conflict.PERKCOLOR + "WOOT!! "+enchantment.toString()+" is now level "+item.getEnchantmentLevel(enchantment));
                            player.setLevel(player.getLevel() - successLevels);
                            player.updateInventory();
                        }
                        else{
                            item.addUnsafeEnchantment(enchantment, 1);
                            player.sendMessage(Conflict.PERKCOLOR + "Oh no!  That enchant was way too high for me to handle...");
                            player.setLevel(player.getLevel() - failureLevels);
                        }

                    }
                    else {
                        player.sendMessage(Conflict.PERKCOLOR + "MMMhmm.  It would seem as if this item has no enchants on it for me to upgrade.");
                    }
                }
            }
        }
    }
    
};