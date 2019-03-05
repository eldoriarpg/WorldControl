package de.eldoria.eldoworldcontrol.listener.entitybehaviour;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.EntityPermission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class TameListener implements Listener {
    @EventHandler
    public void onTame(EntityTameEvent event){
        Player p = Bukkit.getPlayer(event.getOwner().getUniqueId());
        if(!EntityPermission.checkPermission(p, EntityPermission.TAME, event.getEntityType().toString())){
            event.setCancelled(true);
        }
    }
}
