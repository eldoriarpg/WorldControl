package de.eldoria.eldoworldcontrol.listener.playermovement;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.MovementPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SneakListener implements Listener {
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();
        if (!MovementPermission.checkPermission(p, MovementPermission.SNEAK)) {
            event.setCancelled(true);
            p.setSneaking(false);
        }
    }
}
