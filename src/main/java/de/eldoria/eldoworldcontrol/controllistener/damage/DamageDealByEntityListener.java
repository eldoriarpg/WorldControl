package de.eldoria.eldoworldcontrol.controllistener.damage;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
import de.eldoria.eldoworldcontrol.controllistener.ListenerUtil;
import de.eldoria.eldoworldcontrol.controllistener.ProjectileSender;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Checks to which entites a player can deal damage to.
 */
public class DamageDealByEntityListener extends BaseControlListener {
    public DamageDealByEntityListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onDamageDeal(EntityDamageByEntityEvent event) {
        ProjectileSender sender = ListenerUtil.getProjectileSource(event.getDamager());

        if (!sender.isEmpty()) {
            if (sender.isEntity() && sender.getEntity() instanceof Player) {
                if (!validator.canDealDamageTo((Player) sender.getEntity(), event.getEntityType())) {
                    event.setCancelled(true);
                }
                return;
            }
        }

        if (!(event.getDamager() instanceof Player)) return;

        Player p = (Player) event.getDamager();

        if (validator.canDealDamageTo(p, event.getEntityType())) return;

        event.setDamage(0);
        event.setCancelled(true);
    }
}