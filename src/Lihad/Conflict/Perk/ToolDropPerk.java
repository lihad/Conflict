package Lihad.Conflict.Perk;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Lihad.Conflict.*;
import Lihad.Conflict.Util.BeyondUtil;

public class ToolDropPerk extends PassivePerk {

    int percentChance = 3;

    public ToolDropPerk() {
        super("ToolDrop"); 
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
                    ItemStack stack = new ItemStack(BeyondUtil.toolTypeRandomizer(), 1);
                    BeyondUtil.addRandomEnchant(stack);
                    int next = Conflict.random.nextInt(100);
                    if(next<30) BeyondUtil.addRandomEnchant(stack);
                    if(next<20) BeyondUtil.addRandomEnchant(stack);
                    if(next<5) BeyondUtil.addRandomEnchant(stack);
                    event.getDrops().add(stack);
                    player.sendMessage("A "+ Conflict.ITEMCOLOR + stack.getType().toString()+ Conflict.TEXTCOLOR +" dropped! Rarity Index: "+BeyondUtil.getColorOfRarity(BeyondUtil.rarity(stack))+BeyondUtil.rarity(stack));
                }
            }
        }
    }

};