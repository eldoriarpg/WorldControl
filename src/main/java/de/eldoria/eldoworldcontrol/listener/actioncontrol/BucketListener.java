package de.eldoria.eldoworldcontrol.listener.actioncontrol;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.ActionPermission;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class BucketListener implements Listener {
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player p = event.getPlayer();
        if (!ActionPermission.checkPermission(p, ActionPermission.BUCKET_FILL, event.getBlockClicked().getType().name())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerInteractAtEntityEvent event) {
        Player p = event.getPlayer();
        EntityType t = event.getRightClicked().getType();
        if (isBucketEntity(t)) {
            if (hasBucket(p)) {
                if (!ActionPermission.checkPermission(p , ActionPermission.BUCKET_FILL, t.toString())) {

                }
            }
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player p = event.getPlayer();
        if (!ActionPermission.checkPermission(p, ActionPermission.BUCKET_EMPTY, event.getBucket().name())) {
            event.setCancelled(true);
        }

    }

    public boolean isBucketEntity(EntityType type) {
        switch (type) {
            case COW:
            case MUSHROOM_COW:
            case COD:
            case SALMON:
            case PUFFERFISH:
            case TROPICAL_FISH:
                return true;
        }
        return false;
    }

    public boolean hasBucket(Player p) {
        return p.getInventory().getItemInMainHand().getType() == Material.BUCKET ||
                p.getInventory().getItemInOffHand().getType() == Material.BUCKET;
    }

}
