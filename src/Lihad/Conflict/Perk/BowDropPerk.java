package Lihad.Conflict.Perk;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.*;
import Lihad.Conflict.Util.BeyondUtil;

public class BowDropPerk extends PassivePerk {

    int percentChance = 3;

    public BowDropPerk() {
        super("BowDrop");
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
        if(entity instanceof org.bukkit.entity.Skeleton) {
            if (Conflict.random.nextInt(100) < percentChance) {
                Player player = entity.getKiller();
                if (player != null && Conflict.playerCanUsePerk(player, this)) {
                    ItemStack stack = new ItemStack(org.bukkit.Material.BOW, 1);
                    BeyondUtil.addRandomEnchant(stack);
                    int next = Conflict.random.nextInt(100);
                    if(next<30) BeyondUtil.addRandomEnchant(stack);
                    if(next<20) BeyondUtil.addRandomEnchant(stack);
                    if(next<5) BeyondUtil.addRandomEnchant(stack);
                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), stack);
                }
            }
        }
    }

};