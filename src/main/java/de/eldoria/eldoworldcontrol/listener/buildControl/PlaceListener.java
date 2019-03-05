package de.eldoria.eldoworldcontrol.listener.buildControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.BuildPermission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        Material materialName = event.getBlock().getType();
        if (!BuildPermission.checkPermission(p, BuildPermission.PLACE, materialName.toString())) {
            event.setCancelled(true);
        }

    }

}
