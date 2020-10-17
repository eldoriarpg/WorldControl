package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class BucketListener extends BaseControlListener {

    public BucketListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player p = event.getPlayer();

        if (validator.canFillBucketWith(p, event.getBlockClicked().getType())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerInteractAtEntityEvent event) {
        Player p = event.getPlayer();
        EntityType t = event.getRightClicked().getType();
        if (isNotBucketEntity(t)) return;

        if (hasNoBucket(p)) return;
        if (validator.canFillBucketWith(p, t)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player p = event.getPlayer();

        if (validator.canEmptyBucketWith(p, event.getBucket())) return;

        event.setCancelled(true);
    }

    public boolean isNotBucketEntity(EntityType type) {
        switch (type) {
            case COW:
            case MUSHROOM_COW:
            case COD:
            case SALMON:
            case PUFFERFISH:
            case TROPICAL_FISH:
                return false;
        }
        return true;
    }

    public boolean hasNoBucket(Player p) {
        return p.getInventory().getItemInMainHand().getType() == Material.BUCKET ||
                p.getInventory().getItemInOffHand().getType() == Material.BUCKET;
    }

}