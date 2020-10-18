package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.config.dropreplacements.DropReplacements;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import de.eldoria.eldoworldcontrol.core.reloading.SharedData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class BreakListener extends BaseControlListener {

    private DropReplacements replacements;

    public BreakListener(PermissionValidator permissionValidator) {
        super(permissionValidator);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Material material = event.getBlock().getType();

        if (validator.canBreakAndReceive(p, material)) {
            return;
        } else if (validator.canBreak(p, material)) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            if (replacements.containsKey(material)) {
                Collection<ItemStack> drops = event.getBlock().getDrops();
                event.getBlock().getWorld()
                        .dropItem(event.getBlock().getLocation(), replacements.get(material).getItemStack());
            }
            return;
        }
        sender.sendLocalizedError(p, "permission.error.breakBlock",
                Replacement.create("MAT", material, '6'));
        event.setCancelled(true);
    }

    @Override
    public void reload(SharedData data) {
        replacements = data.getConfig().getDropReplacements();
    }
}
