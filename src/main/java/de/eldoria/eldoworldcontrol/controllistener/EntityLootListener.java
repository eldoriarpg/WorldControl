package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class EntityLootListener extends BaseControlListener {
    private final Map<Integer, Player> lastDamage = new HashMap<>();

    public EntityLootListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        int entityId = entity.getEntityId();

        // Register player on entity
        if (damager instanceof Player) {
            lastDamage.put(entityId, (Player) damager);
            Bukkit.getLogger().info("Registered player " + ((Player) damager).getDisplayName() + " for entity " + event.getEntity().getEntityId());
            return;
        }

        ProjectileSender sender = ListenerUtil.getProjectileSource(damager);
        if (sender.isEntity() && sender.getEntity() instanceof Player) {
            lastDamage.put(entityId, (Player) sender.getEntity());
            Bukkit.getLogger().info("Registered player "
                    + ((Player) sender.getEntity()).getDisplayName()
                    + " for entity " + event.getEntity().getEntityId());
            return;
        }

        Bukkit.getLogger().info("Removed entity " + event.getEntity().getEntityId());
        lastDamage.remove(entityId);
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Bukkit.getLogger().info("Entity " + event.getEntity().getEntityId() + " was killed.");
        Player player = lastDamage.remove(event.getEntity().getEntityId());

        if (player == null) {
            Bukkit.getLogger().info("No player registered for kill of entity " + event.getEntity().getEntityId());
            return;
        }

        if (validator.canLoot(player, event.getEntity().getType())) return;

        event.getDrops().clear();
        event.setDroppedExp(0);
    }
}
