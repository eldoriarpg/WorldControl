package de.eldoria.eldoworldcontrol.core;

import org.bukkit.entity.Player;

public final class MessageSender {
    private MessageSender() {
    }

    public static void sendMessage(Player p, String message) {
        p.sendMessage("§6[WC§6] §3" + message);
    }
}
