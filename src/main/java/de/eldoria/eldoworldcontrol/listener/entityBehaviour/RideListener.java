package de.eldoria.eldoworldcontrol.listener.entityBehaviour;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.EntityPermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class RideListener implements Listener {
    @EventHandler
    public void onPlayerStartRide(EntityMountEvent event){
        if(event.getEntity() instanceof Player){
            Player p = (Player) event.getEntity();
            if(!EntityPermission.checkPermission(p, EntityPermission.RIDE, event.getEntityType().toString())){
                event.setCancelled(true);
            }
        }
    }
}
