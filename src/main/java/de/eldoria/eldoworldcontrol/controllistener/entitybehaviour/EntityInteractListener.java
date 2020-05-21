package de.eldoria.eldoworldcontrol.controllistener.entitybehaviour;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteractListener extends BaseControlListener {
    public EntityInteractListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player p = event.getPlayer();

        if (validator.canInteract(p, event.getRightClicked().getType())) return;

        event.setCancelled(true);
    }
}
