package de.eldoria.eldoworldcontrol.controllistener.build;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.reloading.Reloadable;
import de.eldoria.eldoworldcontrol.core.reloading.SharedData;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class BreakListener extends BaseControlListener implements Reloadable {
;
    private Map<Material, ItemStack> replacements;

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
            event.getBlock().getDrops().clear();
            if (replacements.containsKey(material)) {
                event.getBlock().getWorld()
                        .dropItem(event.getBlock().getLocation(), replacements.get(material));
            }
            return;
        }
        event.setCancelled(true);
    }

    private void buildReplacementMap(FileConfiguration config) {
        replacements = new EnumMap<>(Material.class);
        ConfigurationSection section = config.getConfigurationSection("DropReplacements");

        if (section != null) {
            Set<String> keys = section.getKeys(false);
            for (String key : keys) {
                Material source = Material.matchMaterial(key);
                String value = section.getString(key);

                int amount = 1;

                if (value == null) return;
                String[] split = value.split(":");
                if (split.length == 2) {
                    try {
                        amount = Integer.parseInt(split[1]);
                    } catch (NumberFormatException e) {
                        Bukkit.getLogger().warning("Found an invalid number on entry: " + key + ":" + value);
                        continue;
                    }
                }

                Material replacement = Material.matchMaterial(split[0]);

                if (source == null || replacement == null) {
                    Bukkit.getLogger().warning("Replacement entry: " + key + ":" + value + " is invalid!");
                    continue;
                }
                replacements.put(source, new ItemStack(replacement, amount));
            }
        } else {
            Bukkit.getLogger().warning("DropReplacement Section is missing in the configuration file.");
        }
    }

    @Override
    public void reload(SharedData data) {
        buildReplacementMap(data.getConfiguration());
    }
}
