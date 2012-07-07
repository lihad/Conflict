package Lihad.Conflict.Perk;

import org.bukkit.entity.Player;

public class StrikePerk extends PassivePerk {

    public StrikePerk() { super("Strike"); }

    public void Initialize(org.bukkit.plugin.Plugin plugin) {
        //plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @org.bukkit.event.EventHandler
    public void onEntityDamageByEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            //if (Conflict.playerCanUsePerk(
            event.setDamage(event.getDamage()+1);
        }
    }
};
