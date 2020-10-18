package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener extends BaseControlListener {
    public LoginListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        if (validator.canJoin(event.getPlayer())) return;
        event.setJoinMessage(null);
        event.getPlayer().kickPlayer(localizer.getMessage("permission.error.login"));

    }
}
