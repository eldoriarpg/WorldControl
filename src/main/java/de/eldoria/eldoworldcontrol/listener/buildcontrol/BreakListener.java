package de.eldoria.eldoworldcontrol.listener.buildcontrol;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.BuildPermission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Material materialName = event.getBlock().getType();
        if (BuildPermission.checkPermission(p, BuildPermission.BLOCK_BREAK_AND_RECEIVE, materialName.toString())) {
            return;
        } else if (BuildPermission.checkPermission(p, BuildPermission.BLOCK_BREAK, materialName.toString())) {
            event.getBlock().getDrops().clear();
            return;
        }
        event.setCancelled(true);
    }
}
