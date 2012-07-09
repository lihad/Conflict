package Lihad.Conflict.Perk;

import org.bukkit.entity.Player;

import Lihad.Conflict.*;

public class ShieldPerk extends PassivePerk {

    int damageReduction = 1;
    
    public ShieldPerk() {
        super("Shield");
        purchasable = true;
    }

    @Override
    public void save(org.bukkit.configuration.ConfigurationSection section) {
        super.save(section);
        section.set("damagereduction", damageReduction);
    }
    
    @Override
    public void onEntityDamageByEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (Conflict.playerCanUsePerk(player, (Perk)this)) {
                event.setDamage(event.getDamage() - damageReduction);
            }
        }
    }
};
