package de.eldoria.eldoworldcontrol.controllistener.actions;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropListener extends BaseControlListener {

    public DropListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        Material materialName = event.getItemDrop().getItemStack().getType();

        if (p.getGameMode() == GameMode.CREATIVE) {
            if (validator.canDropInCreative(p, materialName)) return;

        } else {
            if (validator.canDrop(p, materialName)) return;
        }

        event.setCancelled(true);
    }

}
