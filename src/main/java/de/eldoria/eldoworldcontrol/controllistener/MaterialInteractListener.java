package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

public class MaterialInteractListener extends BaseControlListener {
    public MaterialInteractListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (event.isCancelled()) return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getPlayer().isSneaking() && event.getClickedBlock().getType().isSolid()) {
            PlayerInventory inventory = event.getPlayer().getInventory();
            if (inventory.getItemInMainHand().getType().isBlock()) {
                return;
            }
            if (inventory.getItemInOffHand().getType().isBlock()) {
                return;
            }
        }

        Material material = event.getClickedBlock().getType();


        if (validator.canInteract(p, material)) return;

        event.setCancelled(true);
    }
}
