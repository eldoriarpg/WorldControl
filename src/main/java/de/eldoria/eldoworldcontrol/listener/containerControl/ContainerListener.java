package de.eldoria.eldoworldcontrol.listener.containerControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.ContainerPermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ContainerListener implements Listener {
    @EventHandler
    public void onInventoryChange(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player p = (Player) event.getWhoClicked();
            if (event.getAction() == InventoryAction.PLACE_SOME) {
                if (!ContainerPermission.checkPermission(p, event.getClickedInventory().getType(), ContainerPermission.DEPOSIT_ITEMS)) {
                    event.setCancelled(true);
                }
            }

            if (event.getAction() == InventoryAction.PICKUP_SOME) {
                if (!ContainerPermission.checkPermission(p, event.getClickedInventory().getType(), ContainerPermission.WITHDRAW_ITEMS)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}