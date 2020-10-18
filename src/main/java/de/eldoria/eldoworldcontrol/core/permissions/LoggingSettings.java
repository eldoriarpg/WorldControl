package de.eldoria.eldoworldcontrol.core.permissions;

import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoworldcontrol.core.EldoWorldControl;
import org.bukkit.entity.Player;

public class LoggingSettings {
    private final Player owner;
    private Player target;

    public LoggingSettings(Player owner) {
        this.owner = owner;
    }

    public LoggingSettings(Player owner, Player target) {
        this.owner = owner;
        this.target = target;
    }

    public void dispatchLogMessage(Player p, String message) {
        if (target == null || target == p) {
            MessageSender.get(EldoWorldControl.class).sendMessage(owner, message);
        }
    }

    public void updateTarget(Player target) {
        this.target = target;
    }
}
