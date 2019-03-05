package de.eldoria.eldoworldcontrol.listener.actionControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.ActionPermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantListener implements Listener {
    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        Player p = event.getEnchanter();
        Material materialName = event.getItem().getType();
        if (ActionPermission.checkPermission(p, ActionPermission.ENCHANT ,materialName.toString())) {
            return;
        }
        event.setCancelled(true);

    }
}
