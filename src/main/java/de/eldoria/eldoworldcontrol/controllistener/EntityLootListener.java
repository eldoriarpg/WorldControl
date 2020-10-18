package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;


public class EntityLootListener extends BaseControlListener {
    public EntityLootListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }

        if (validator.canLoot(killer, event.getEntity().getType())) return;

        if (messages) {
            sender.sendLocalizedError(killer, "permission.error.loot",
                    Replacement.create("ENTITY", event.getEntity().getType(), '6'));
        }
        event.getDrops().clear();
        event.setDroppedExp(0);
    }
}
