package de.eldoria.eldoworldcontrol.controllistener.actions;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class MaterialInteractListener extends BaseControlListener {
    public MaterialInteractListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (event.isCancelled()) return;

        Material material = event.getClickedBlock().getType();

        if (validator.canInteract(p, material)) return;

        event.setCancelled(true);
    }
}
