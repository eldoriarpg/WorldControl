package de.eldoria.eldoworldcontrol.listener.entityBehaviour;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.EntityPermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class TargetListener implements Listener {

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player) {
            Player p = (Player) event.getTarget();
            if (!EntityPermission.checkPermission(p, EntityPermission.MOB_TARGET, event.getEntityType().toString())) {
                event.setCancelled(true);
                event.setTarget(null);
            }
        }
    }
}
