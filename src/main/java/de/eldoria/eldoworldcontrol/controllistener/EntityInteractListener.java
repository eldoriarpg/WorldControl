package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
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
        sender.sendLocalizedError(p, "permission.error.interactEntity",
                Replacement.create("ENTITY", event.getRightClicked().getType(), '6'));
        event.setCancelled(true);
    }
}
