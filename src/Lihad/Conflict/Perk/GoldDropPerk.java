package Lihad.Conflict.Perk;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.*;
import Lihad.Conflict.Util.BeyondUtil;

public class GoldDropPerk extends PassivePerk {

    int percentChance = 3;

    public GoldDropPerk() {
        super("GoldDrop");
        purchasable = true;
    }

    @Override
    public void save(org.bukkit.configuration.ConfigurationSection section) {
        super.save(section);
        section.set("percentchance", percentChance);
    }

    @Override
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event){
        org.bukkit.entity.LivingEntity entity = event.getEntity();
        if(entity instanceof org.bukkit.entity.Sheep) {
            if (Conflict.random.nextInt(100) < percentChance) {
                Player player = entity.getKiller();
                if (player != null && Conflict.playerCanUsePerk(player, this)) {
                    ItemStack stack = new ItemStack(org.bukkit.Material.GOLD_INGOT, 1);
                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                }
            }
        }
    }

};