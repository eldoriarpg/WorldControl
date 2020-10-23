package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class ShearListener extends BaseControlListener {

    public ShearListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
        if (validator.canShear(event.getPlayer(), event.getEntity().getType())) return;

        if (messages) {
            sender.sendLocalizedError(event.getPlayer(), "permission.error.shear",
                    Replacement.create("ENTITY", event.getEntity().getType(), '6'));
        }
        event.setCancelled(true);
    }
}
