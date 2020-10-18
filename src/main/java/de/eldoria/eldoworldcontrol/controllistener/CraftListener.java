package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftListener extends BaseControlListener {

    public CraftListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player p = Bukkit.getPlayer(event.getWhoClicked().getName());
            Material material = event.getInventory().getResult().getType();

            if (validator.canCraft(p, material)) return;
            sender.sendLocalizedError(p, "permission.error.craft",
                    Replacement.create("MAT", material, '6'));
            event.setCancelled(true);
        }
    }
}
