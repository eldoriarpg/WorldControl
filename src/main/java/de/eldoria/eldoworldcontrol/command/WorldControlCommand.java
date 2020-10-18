package de.eldoria.eldoworldcontrol.command;

import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.simplecommands.EldoCommand;
import de.eldoria.eldoworldcontrol.command.worldcontrol.About;
import de.eldoria.eldoworldcontrol.command.worldcontrol.Reload;
import de.eldoria.eldoworldcontrol.command.worldcontrol.Verbose;
import de.eldoria.eldoworldcontrol.core.EldoWorldControl;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionVerboseLogger;
import org.bukkit.plugin.Plugin;

public class WorldControlCommand extends EldoCommand {
    public WorldControlCommand(Plugin plugin, PermissionVerboseLogger logger) {
        super(ILocalizer.getPluginLocalizer(EldoWorldControl.class), MessageSender.get(EldoWorldControl.class));
        registerCommand("verbose", new Verbose(localizer(), messageSender(), logger));
        registerCommand("about", new About(localizer(), messageSender(), plugin));
        registerCommand("reload", new Reload(localizer(), messageSender(), plugin));
    }
}
