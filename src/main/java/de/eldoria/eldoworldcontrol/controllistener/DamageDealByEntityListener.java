package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.entityutils.ProjectileSender;
import de.eldoria.eldoutilities.entityutils.ProjectileUtil;
import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
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
        ProjectileSender sender = ProjectileUtil.getProjectileSource(event.getDamager());

        if (!sender.isEmpty()) {
            if (sender.isEntity() && sender.getEntity() instanceof Player) {
                Player player = (Player) sender.getEntity();
                if (!validator.canDealDamageTo(player, event.getEntityType())) {
                    super.sender.sendLocalizedError(player, "permission.error.dealDamageEntity",
                            Replacement.create("ENTITY", event.getEntityType(), '6'));
                    event.setDamage(0);
                    event.setCancelled(true);
                }
                return;
            }
        }

        if (!(event.getDamager() instanceof Player)) return;

        Player p = (Player) event.getDamager();

        if (validator.canDealDamageTo(p, event.getEntityType())) return;
        super.sender.sendLocalizedError(p, "permission.error.dealDamageEntity");
        event.setDamage(0);
        event.setCancelled(true);
    }
}
