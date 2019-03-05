package de.eldoria.eldoworldcontrol.listener.playerbehaviour;

import de.eldoria.eldoworldcontrol.permissionvalidation.Permission;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.manager.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.concurrent.CompletableFuture;

public class LoginListener implements Listener {
    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        UserManager userManager = LuckPerms.getApi().getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(event.getUniqueId());

        userFuture.thenAcceptAsync(user -> {
            if (user.hasPermission(LuckPerms.getApi().getNodeFactory().newBuilder(Permission.LOGIN).build()).asBoolean()) {
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                event.setKickMessage("You don't have the permission to join this server!");
            }
        });

    }
}
