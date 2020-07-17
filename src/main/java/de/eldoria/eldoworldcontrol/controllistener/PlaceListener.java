package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceListener extends BaseControlListener {
    public PlaceListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        Material material = event.getBlock().getType();

        if (validator.canPlace(p, material)) return;

        event.setCancelled(true);
    }

}
