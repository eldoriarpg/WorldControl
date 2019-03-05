package de.eldoria.eldoworldcontrol.listener.playermovement;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.MovementPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class GlideListener implements Listener {
    @EventHandler
    public void onGlide(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (!MovementPermission.checkPermission(p, MovementPermission.GLIDE)) {
                event.setCancelled(true);
            }
        }
    }
}
