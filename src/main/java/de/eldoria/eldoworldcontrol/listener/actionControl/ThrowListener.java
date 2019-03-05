package de.eldoria.eldoworldcontrol.listener.actionControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.ActionPermission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;

import javax.swing.*;

public class ThrowListener implements Listener {
    @EventHandler
    public void onThrow(PlayerInteractEvent event) {
        if (isThrowable(event.getItem().getType())) {

            Player p = event.getPlayer();
            Material m = event.getItem().getType();
            if (m == Material.SPLASH_POTION || m == Material.LINGERING_POTION) {
                PotionMeta meta = (PotionMeta) event.getItem().getItemMeta();
                PotionData data = meta.getBasePotionData();

                if (!ActionPermission.checkPermission(p, ActionPermission.THROW, "potion."
                        + (m == Material.SPLASH_POTION ? "splash." : "lingering.") + data.getType())) {
                    event.setCancelled(true);
                }
            }
            if (!ActionPermission.checkPermission(p, ActionPermission.THROW, m.toString())) {
                event.setCancelled(true);
            }
        }
    }


    private boolean isThrowable(Material mat) {
        switch (mat) {
            case EGG:
            case ENDER_EYE:
            case ENDER_PEARL:
            case SNOWBALL:
            case EXPERIENCE_BOTTLE:
            case POTION:
            case LINGERING_POTION:
            case TRIDENT:
                return true;

        }
        return false;
    }
}
