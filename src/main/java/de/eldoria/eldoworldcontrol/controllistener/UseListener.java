package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
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

        Material mainMaterial = p.getInventory().getItemInMainHand().getType();
        Material offHandMaterial = p.getInventory().getItemInOffHand().getType();

        // Check if both hands are empty or nothing to use
        if ((mainMaterial.isAir() || mainMaterial.isBlock())
                && (offHandMaterial.isAir() || offHandMaterial.isBlock())) {
            return;
        }

        if (hand == EquipmentSlot.HAND) {
            if (mainMaterial.isItem() && validator.canUse(p, mainMaterial)) {
                return;
            }
            if (!mainMaterial.isItem()) return;
        }

        if (hand == EquipmentSlot.OFF_HAND) {
            if (offHandMaterial.isItem() && validator.canUse(p, offHandMaterial)) {
                return;
            }
            if (!mainMaterial.isItem()) return;
        }

        event.setCancelled(true);
    }
}
