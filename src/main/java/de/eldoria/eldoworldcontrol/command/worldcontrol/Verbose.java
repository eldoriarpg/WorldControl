package de.eldoria.eldoworldcontrol.command.worldcontrol;

import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.simplecommands.EldoCommand;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionVerboseLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Verbose extends EldoCommand {
    private final PermissionVerboseLogger logger;

    public Verbose(ILocalizer localizer, MessageSender messageSender, PermissionVerboseLogger logger) {
        super(localizer, messageSender);
        this.logger = logger;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!isPlayer(sender)) {
            messageSender().sendLocalizedError(sender, "error.console");
            return false;
        }

        if (denyAccess(sender, "ewc.command.verbose")) {
            return true;
        }

        Player player = getPlayerFromSender(sender);

        if (args.length == 0) {
            boolean state = logger.toggleVerboseLogging(player);
            if (state) {
                messageSender().sendLocalizedMessage(player, "verbose.on");
            } else {
                messageSender().sendLocalizedMessage(player, "verbose.off");
            }
            return true;
        }

        String arg = args[0];

        if ("on".equalsIgnoreCase(arg)) {
            logger.setVerboseLoggingState(player, true);
            messageSender().sendLocalizedMessage(player, "verbose.on");
            return true;
        }

        if ("off".equalsIgnoreCase(arg)) {
            logger.setVerboseLoggingState(player, false);
            messageSender().sendLocalizedMessage(player, "verbose.off");
            return true;
        }

        Player target = Bukkit.getPlayer(arg);

        if (target != null) {
            logger.setVerboseLoggingTarget(player, target);
            messageSender().sendLocalizedMessage(player, "verbose.on.player", Replacement.create("PLAYER", player));
            return true;
        }
        messageSender().sendLocalizedError(player, "verbose.invalidInput");
        return true;
    }
}
