package Lihad.Conflict.Perk;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import Lihad.Conflict.*;
import Lihad.Conflict.Util.BeyondUtil;

public class PotionDropPerk extends PassivePerk {

    int percentChance = 3;
    
    public PotionDropPerk() {
        super("PotionDrop");
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
        if(entity instanceof org.bukkit.entity.Zombie) {
            if (Conflict.random.nextInt(100) < percentChance) {
                Player player = entity.getKiller();
                if (player != null && Conflict.playerCanUsePerk(player, this)) {
                    Potion potion = new Potion(BeyondUtil.potionTypeRandomizer(), BeyondUtil.potionTierRandomizer());
                    if (BeyondUtil.potionSplashRandomizer()) {
                        potion.splash();
                    }
                    ItemStack stack = new ItemStack(org.bukkit.Material.POTION, 1);
                    potion.apply(stack);
                    event.getDrops().add(stack);
                }
            }
        }
    }

};