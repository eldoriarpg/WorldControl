package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.spigotmc.event.entity.EntityMountEvent;

public class RideListener extends BaseControlListener {
    public RideListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onPlayerStartRide(EntityMountEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();

            if (validator.canRide(p, event.getEntityType())) return;
            if (messages) {
                sender.sendLocalizedError(p, "permission.error.ride",
                        Replacement.create("ENTITY", event.getEntityType(), '6'));
            }

            event.setCancelled(true);
        }
    }
}
