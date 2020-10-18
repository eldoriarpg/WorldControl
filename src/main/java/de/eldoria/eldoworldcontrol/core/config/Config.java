package de.eldoria.eldoworldcontrol.core.config;

import de.eldoria.eldoutilities.configuration.EldoConfig;
import de.eldoria.eldoworldcontrol.core.EldoWorldControl;
import de.eldoria.eldoworldcontrol.core.config.dropreplacements.DropReplacements;
import de.eldoria.eldoworldcontrol.core.config.permissiongroups.PermissionGroups;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

@Getter
public class Config extends EldoConfig {
    private int version;
    private General general;
    private Modules modules;
    private DropReplacements dropReplacements;
    private PermissionGroups permissionGroups;

    public Config(Plugin plugin) {
        super(plugin);
        FileConfiguration config = plugin.getConfig();
        version = config.getInt("version", 0);
        if (version == 0) {
            init();
        }
        reload();
    }

    private void init() {
        EldoWorldControl.logger().info("§2Initial Config Setup");
        FileConfiguration config = plugin.getConfig();
        version = 1;
        general = new General();
        modules = new Modules();
        dropReplacements = new DropReplacements();
        permissionGroups = new PermissionGroups();
        save();
        EldoWorldControl.logger().info("§2Config Setup done.");
    }

    @Override
    public void save() {
        FileConfiguration config = plugin.getConfig();
        config.set("version", version);
        config.set("general", general);
        config.set("modules", modules);
        config.set("dropReplacements", dropReplacements);
        config.set("permissionGroups", permissionGroups);
        plugin.saveConfig();
    }

    @Override
    public void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        version = config.getInt("version");
        general = config.getObject("general", General.class);
        modules = config.getObject("modules", Modules.class);
        dropReplacements = config.getObject("dropReplacements", DropReplacements.class);
        permissionGroups = config.getObject("permissionGroups", PermissionGroups.class);
    }
}
