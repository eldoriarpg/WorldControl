package de.eldoria.eldoworldcontrol.listener.actioncontrol;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.ActionPermission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class UseListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Material m1 = p.getInventory().getItemInMainHand().getType();
        Material m2 = p.getInventory().getItemInOffHand().getType();
        if (m1 == Material.AIR && m2 == Material.AIR) return;

        if (m1 != Material.AIR && ActionPermission.checkPermission(p, ActionPermission.USE, m1.toString())) {
            return;
        }
        if (m2 != Material.AIR && ActionPermission.checkPermission(p, ActionPermission.USE, m2.toString())) {
            return;
        }

        event.setCancelled(true);

    }
}
