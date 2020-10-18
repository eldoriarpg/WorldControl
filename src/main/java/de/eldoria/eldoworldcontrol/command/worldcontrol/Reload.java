package de.eldoria.eldoworldcontrol.command.worldcontrol;

import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.simplecommands.EldoCommand;
import de.eldoria.eldoworldcontrol.core.EldoWorldControl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Reload extends EldoCommand {
    private final EldoWorldControl plugin;

    public Reload(ILocalizer localizer, MessageSender messageSender, Plugin plugin) {
        super(localizer, messageSender);
        this.plugin = (EldoWorldControl) plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (denyAccess(sender, "ewc.command.reload")) {
            return true;
        }
        plugin.reload();
        messageSender().sendLocalizedMessage(sender, "reload.done");
        return true;
    }
}
