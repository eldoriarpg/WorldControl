package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupListener extends BaseControlListener {

    public PickupListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player p = (Player) event.getEntity();
        Material material = event.getItem().getItemStack().getType();

        if (validator.canPickup(p, material)) return;

        if (messages) {
            sender.sendLocalizedError(p, "permission.error.pickup",
                    Replacement.create("MAT", material, '6'));
        }
        event.setCancelled(true);

    }
}