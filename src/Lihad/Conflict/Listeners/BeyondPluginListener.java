package Lihad.Conflict.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import Lihad.Conflict.Conflict;

public class BeyondPluginListener implements Listener {
    public BeyondPluginListener() { }
    @EventHandler
    public void onPluginEnable(PluginEnableEvent event){
    	if(event.getPlugin().getDescription().getName().equals("Permissions"))Conflict.setupPermissions();
    	else if(event.getPlugin().getDescription().getName().equals("PermissionsEx"))Conflict.setupPermissionsEx();
    }
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event){
    	if(event.getPlugin().getDescription().getName().equals("Permissions"))
    		Conflict.setupPermissions();
    	else if(event.getPlugin().getDescription().getName().equals("PermissionsEx"))
    		Conflict.setupPermissionsEx();
    }
}
