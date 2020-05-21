package de.eldoria.eldoworldcontrol.controllistener.actions;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
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
            Material materialName = event.getInventory().getResult().getType();

            if (validator.canCraft(p, materialName)) return;

            event.setCancelled(true);
        }
    }
}
