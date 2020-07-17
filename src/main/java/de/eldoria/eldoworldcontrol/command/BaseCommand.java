package de.eldoria.eldoworldcontrol.command;

import de.eldoria.eldoworldcontrol.core.MessageSender;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionVerboseLogger;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {

    private final PermissionVerboseLogger logger;

    public BaseCommand(PermissionVerboseLogger logger) {
        this.logger = logger;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            return about(commandSender);
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only a player can do this.");
            return true;
        }

        Player player = (Player) commandSender;

        if ("verbose".equalsIgnoreCase(strings[0])) {
            return verboseLogging(strings, player);
        }

        MessageSender.sendMessage(player, "invalid command");
        return true;
    }

    private boolean verboseLogging(String[] strings, Player player) {
        if (strings.length == 1) {
            boolean state = logger.toggleVerboseLogging(player);
            if (state) {
                MessageSender.sendMessage(player, "Verbose logging activated");
            } else {
                MessageSender.sendMessage(player, "Verbose logging disabled");
            }
            return true;
        }

        var arg = strings[1];

        if ("on".equalsIgnoreCase(arg)) {
            logger.setVerboseLoggingState(player, true);
            MessageSender.sendMessage(player, "Verbose logging activated");
            return true;
        }

        if ("off".equalsIgnoreCase(arg)) {
            logger.setVerboseLoggingState(player, false);
            MessageSender.sendMessage(player, "Verbose logging disabled");
            return true;
        }

        Player target = Bukkit.getPlayer(arg);

        if (target != null) {
            logger.setVerboseLoggingTarget(player, target);
            MessageSender.sendMessage(player, "Observing permission checks of player " + target.getName());
            return true;
        }
        MessageSender.sendMessage(player, "Invalid input. Provide player name or \"on\" or \"off\" to toggle logging.");
        return true;
    }

    private boolean about(CommandSender commandSender) {
        TextComponent rainbowdashlabs = new TextComponent("Rainbowdash Labs");
        rainbowdashlabs.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3https://github.com/RainbowDashLabs").create()));
        rainbowdashlabs.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/RainbowDashLabs"));

        TextComponent adwirawien = new TextComponent("Adwirawien");
        adwirawien.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3https://github.com/Adwirawien").create()));
        adwirawien.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Adwirawien"));

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            p.sendRawMessage("§3§b§uWorld Control\n" +
                    "§7Brought to you by §c§uEldoria\n" +
                    "§7Written by §3§u" + rainbowdashlabs.getInsertion() + " §7and §3§u"
                    + adwirawien.getInsertion() + "\n" +
                    "§7Support via §3Discord §7under §3Chojo#0001");
            p.sendMessage("§3§b§uWorld Control\n" +
                    "§7Brought to you by §c§uEldoria\n" +
                    "§7Written by §3§u" + rainbowdashlabs.getInsertion() + " §7and §3§u"
                    + adwirawien.getInsertion() + "\n" +
                    "§7Support via §3Discord §7under §3Chojo#0001");
        }
        return true;
    }
}
