package de.eldoria.eldoworldcontrol.core.reloading;

import de.eldoria.eldoworldcontrol.core.data.PermissionGroups;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.configuration.file.FileConfiguration;

public class SharedData {
    private FileConfiguration configuration;
    private PermissionValidator permissionValidator;
    private PermissionGroups groups;

    public SharedData(FileConfiguration config, PermissionValidator permissionValidator) {
        this.configuration = config;
        this.permissionValidator = permissionValidator;
        groups = new PermissionGroups(configuration);
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public PermissionGroups getGroups() {
        return groups;
    }

    public PermissionValidator getValidator() {
        return permissionValidator;
    }
}
