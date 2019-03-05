package de.eldoria.eldoworldcontrol.listener.actionControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.ActionPermission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player p = Bukkit.getPlayer(event.getWhoClicked().getName());
            Material materialName = event.getInventory().getResult().getType();
            if (ActionPermission.checkPermission(p, ActionPermission.CRAFT , materialName.toString())) {
                return;
            }
            event.setCancelled(true);
        }
    }
}
