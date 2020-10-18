package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;

public class LoginListener extends BaseControlListener {
    private final Set<Player> kicked = new HashSet<>();

    public LoginListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerJoinEvent event) {
        if (validator.canJoin(event.getPlayer())) return;
        event.setJoinMessage(null);
        kicked.add(event.getPlayer());
        event.getPlayer().kickPlayer(localizer.getMessage("permission.error.login"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerQuitEvent event) {
        if (kicked.contains(event.getPlayer())) {
            event.setQuitMessage(null);
            kicked.remove(event.getPlayer());
        }
    }
}
