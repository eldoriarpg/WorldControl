package de.eldoria.eldoworldcontrol.controllistener.util;

import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import de.eldoria.eldoworldcontrol.core.reloading.Reloadable;
import org.bukkit.event.Listener;

public abstract class BaseControlListener implements Listener, Reloadable {
    protected PermissionValidator validator;

    public BaseControlListener(PermissionValidator validator) {
        this.validator = validator;
    }

}
