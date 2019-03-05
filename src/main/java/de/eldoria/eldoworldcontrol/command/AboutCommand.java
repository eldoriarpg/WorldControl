package de.eldoria.eldoworldcontrol.command;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AboutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        TextComponent rainbowdashlabs = new TextComponent("Rainbowdash Labs");
        rainbowdashlabs.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3https://github.com/RainbowDashLabs").create()));
        rainbowdashlabs.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/RainbowDashLabs"));

        TextComponent adwirawien = new TextComponent("Adwirawien");
        adwirawien.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3https://github.com/Adwirawien").create()));
        adwirawien.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Adwirawien"));

        commandSender.sendMessage("§3§b§uWorld Control\n" +
                "§7Brought to you by §c§uEldoria\n" +
                "§7Written by §3§u" + rainbowdashlabs + " §7and §3§u" + adwirawien + "\n" +
                "§7Support via §3Discord §7under §3Chojo#0001");
        return true;
    }
}
