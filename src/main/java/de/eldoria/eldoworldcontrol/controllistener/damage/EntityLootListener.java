package de.eldoria.eldoworldcontrol.controllistener.damage;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
import de.eldoria.eldoworldcontrol.controllistener.ListenerUtil;
import de.eldoria.eldoworldcontrol.controllistener.ProjectileSender;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityLootListener extends BaseControlListener {
    private final Map<UUID, Player> lastDamage = new HashMap<>();

    public EntityLootListener(PermissionValidator validator) {
        super(validator);
    }

    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        UUID entityId = event.getEntity().getUniqueId();

        if (damager instanceof Player) {
            lastDamage.put(entityId, (Player) damager);
            return;
        }

        ProjectileSender sender = ListenerUtil.getProjectileSource(damager);
        if (sender.isEntity() && sender.getEntity() instanceof Player) {
            lastDamage.put(entityId, (Player) sender.getEntity());
            return;
        }

        lastDamage.remove(entityId);
    }

    public void onEntityKill(EntityDeathEvent event) {
        event.getEntityType();

        Player player = lastDamage.get(event.getEntity().getUniqueId());

        if (player == null) return;

        if(validator.canLoot(player, event.getEntity().getType())) return;

        event.getDrops().clear();
    }
}
