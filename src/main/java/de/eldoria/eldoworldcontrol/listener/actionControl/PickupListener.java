package de.eldoria.eldoworldcontrol.listener.actionControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.ActionPermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupListener implements Listener {

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            Material materialName = event.getItem().getItemStack().getType();
            if (ActionPermission.checkPermission(p,ActionPermission.PICKUP, materialName.toString())) {
                return;
            }
            event.setCancelled(true);
        }

    }
}