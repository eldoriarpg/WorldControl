package de.eldoria.eldoworldcontrol.listener.playermovement;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.MovementPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class SprintListener implements Listener {
    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event){
        Player p = event.getPlayer();
        if(!MovementPermission.checkPermission(p, MovementPermission.SPRINT)){
            event.setCancelled(true);
            p.setSprinting(false);
        }
    }
}
