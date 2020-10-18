package de.eldoria.eldoworldcontrol.core.reloading;

import de.eldoria.eldoworldcontrol.core.config.Config;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import lombok.Getter;

@Getter
public class SharedData {
    private Config config;
    private PermissionValidator permissionValidator;

    public SharedData(Config config, PermissionValidator permissionValidator) {
        this.config = config;
        this.permissionValidator = permissionValidator;
    }
}
