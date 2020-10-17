package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.entityutils.ProjectileSender;
import de.eldoria.eldoutilities.entityutils.ProjectileUtil;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * This listener checks from which entites a player can receive damage.
 */
public class DamageTakeByEntityListener extends BaseControlListener {
    public DamageTakeByEntityListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onDamageTakeByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();

            ProjectileSender sender = ProjectileUtil.getProjectileSource(event.getDamager());

            if (!sender.isEmpty()) {
                if (sender.isEntity()) {
                    if (validator.canTakeDamageFrom(p, sender.getEntityType())) return;
                }

                if (sender.isBlock()) {
                    if (validator.canTakeDamageFrom(p, sender.getBlockType())) return;
                }
            } else {
                if (validator.canTakeDamageFrom(p, event.getDamager().getType())) return;
            }

            event.setCancelled(true);
            event.setDamage(0);
        }
    }
}
