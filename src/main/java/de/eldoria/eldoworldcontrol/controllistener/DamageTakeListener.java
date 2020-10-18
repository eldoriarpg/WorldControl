package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageTakeListener extends BaseControlListener {

    public DamageTakeListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onDamageTake(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {

            Player p = (Player) event.getEntity();

            if (validator.canReceiveDamageThrough(p, event.getCause())) return;
            sender.sendLocalizedError(p, "permission.error.damageTake",
                    Replacement.create("CAUSE", event.getCause(), '6'));
            event.setCancelled(true);
            p.setHealth(20);
        }
    }
}
