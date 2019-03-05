package de.eldoria.eldoworldcontrol.listener.playermovement;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.MovementPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;

public class SwimListener implements Listener {
    @EventHandler
    public void onSwim(EntityToggleSwimEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if(!MovementPermission.checkPermission(p,MovementPermission.SWIM)){
                event.setCancelled(true);
            }
        }
    }
}
