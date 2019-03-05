package de.eldoria.eldoworldcontrol.listener.actionControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.ActionPermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {
    @EventHandler
    public void onInteract (PlayerInteractEvent event) {
            Player p = event.getPlayer();
            Material materialName = event.getClickedBlock().getType();
            if (ActionPermission.checkPermission(p, ActionPermission.INTERACT, materialName.toString())) {
                return;
            }

            event.setCancelled(true);
    }
}
