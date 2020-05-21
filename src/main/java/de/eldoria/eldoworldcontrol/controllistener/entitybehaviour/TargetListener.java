package de.eldoria.eldoworldcontrol.controllistener.entitybehaviour;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
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

            event.setCancelled(true);
            event.setTarget(null);
        }
    }
}
