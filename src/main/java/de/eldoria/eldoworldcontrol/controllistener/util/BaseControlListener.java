package de.eldoria.eldoworldcontrol.controllistener.util;

import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoworldcontrol.core.EldoWorldControl;
import de.eldoria.eldoworldcontrol.core.config.modules.ModuleSetting;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import de.eldoria.eldoworldcontrol.core.reloading.Reloadable;
import de.eldoria.eldoworldcontrol.core.reloading.SharedData;
import org.bukkit.event.Listener;

public abstract class BaseControlListener implements Listener, Reloadable {
    protected PermissionValidator validator;
    protected MessageSender sender;
    protected ILocalizer localizer;
    protected ModuleSetting moduleSetting;
    protected boolean messages;

    public BaseControlListener(PermissionValidator validator) {
        this.validator = validator;
        sender = MessageSender.get(EldoWorldControl.class);
        localizer = ILocalizer.getPluginLocalizer(EldoWorldControl.class);
    }

    @Override
    public void reload(SharedData data) {
        moduleSetting = data.getConfig().getModules().getSettings(getClass());
        messages = moduleSetting.isErrorMessageEnabled();
    }
}
