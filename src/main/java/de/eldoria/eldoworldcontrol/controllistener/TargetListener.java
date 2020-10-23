package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class TargetListener extends BaseControlListener {

    public TargetListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player) {

            Player p = (Player) event.getTarget();

            if (validator.canBeMobTarget(p, event.getEntityType())) return;

            if (messages) {
                sender.sendLocalizedError(p, "permission.error.target",
                        Replacement.create("ENTITY", event.getEntityType(), '6'));
            }
            event.setCancelled(true);
            event.setTarget(null);
        }
    }
}
