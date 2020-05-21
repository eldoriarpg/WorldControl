package de.eldoria.eldoworldcontrol.controllistener.actions;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;


public class UseListener extends BaseControlListener {

    public UseListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (event.getHand() == null) return;

        EquipmentSlot hand = event.getHand();

        if (hand == EquipmentSlot.HAND) {
            Material mainMaterial = p.getInventory().getItemInMainHand().getType();
            if (mainMaterial != Material.AIR && validator.canUse(p, mainMaterial)) {
                return;
            }
        }

        if (hand == EquipmentSlot.OFF_HAND) {
            Material offHandMaterial = p.getInventory().getItemInOffHand().getType();
            if (offHandMaterial != Material.AIR && validator.canUse(p, offHandMaterial)) {
                return;
            }
        }

        event.setCancelled(true);

    }
}
