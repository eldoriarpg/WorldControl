package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumeListener extends BaseControlListener {
    public ConsumeListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if (validator.canConsume(event.getPlayer(), event.getItem().getType())) return;
        if (messages) {
            sender.sendLocalizedError(event.getPlayer(), "permission.error.consume",
                    Replacement.create("MAT", event.getItem().getType(), '6'));
        }
        event.setCancelled(true);
    }
}
