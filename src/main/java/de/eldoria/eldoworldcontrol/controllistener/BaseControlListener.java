package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.event.Listener;

public abstract class BaseControlListener implements Listener {
    protected PermissionValidator validator;

    public BaseControlListener(PermissionValidator validator) {
        this.validator = validator;
    }
}
