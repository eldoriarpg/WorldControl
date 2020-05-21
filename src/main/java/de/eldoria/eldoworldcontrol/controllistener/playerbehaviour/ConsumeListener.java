package de.eldoria.eldoworldcontrol.controllistener.playerbehaviour;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumeListener extends BaseControlListener {
    public ConsumeListener(PermissionValidator validator) {
        super(validator);
    }

    public void onConsume(PlayerItemConsumeEvent event) {
        if (validator.canConsume(event.getPlayer(), event.getItem().getType())) return;

        event.setCancelled(true);
    }
}
