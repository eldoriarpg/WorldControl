package de.eldoria.eldoworldcontrol.core.permissions;

import de.eldoria.eldoworldcontrol.core.MessageSender;
import org.bukkit.entity.Player;

public class LoggingSettings {
    private Player owner;
    private Player target;

    public LoggingSettings(Player owner) {
        this.owner = owner;
    }

    public LoggingSettings(Player owner, Player target) {
        this.owner = owner;
        this.target = target;
    }

    public void dispatchLogMessage(Player p, String permission, boolean state) {
        if (target == null || target == p) {
            MessageSender.sendMessage(owner, permission + " (" + (state ? "§agranted" : "§cdenied"));
        }
    }

    public void updateTarget(Player target) {
        this.target = target;
    }
}
