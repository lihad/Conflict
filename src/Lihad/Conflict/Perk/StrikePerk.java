package Lihad.Conflict.Perk;

import org.bukkit.entity.Player;

import Lihad.Conflict.*;

public class StrikePerk extends PassivePerk {

    int extraDamage = 1;

    public StrikePerk() {
        super("Strike"); 
        purchasable = true;
    }

    @Override
    public void save(org.bukkit.configuration.ConfigurationSection section) {
        super.save(section);
        section.set("extradamage", extraDamage);
    }
    
    @Override
    public void onEntityDamageByEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            if (Conflict.playerCanUsePerk(player, this)) {
                event.setDamage(event.getDamage()+extraDamage);
            }
        }
    }
};
