package de.eldoria.eldoworldcontrol.command.worldcontrol;

import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.simplecommands.EldoCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

public class About extends EldoCommand {
    private final Plugin plugin;

    public About(ILocalizer localizer, MessageSender messageSender, Plugin plugin) {
        super(localizer, messageSender);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PluginDescriptionFile descr = plugin.getDescription();
        messageSender().sendLocalizedMessage(sender, "about",
                Replacement.create("PLUGIN_NAME", "World Control", 'b'),
                Replacement.create("AUTHORS", String.join(", ", descr.getAuthors()), 'b'),
                Replacement.create("VERSION", descr.getVersion(), 'b'),
                Replacement.create("WEBSITE", descr.getWebsite(), 'b'),
                Replacement.create("DISCORD", "https://discord.gg/rfRuUge").addFormatting('b'));
        return true;
    }

}
