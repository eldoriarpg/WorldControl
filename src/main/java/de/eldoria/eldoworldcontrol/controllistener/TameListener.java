package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTameEvent;

public class TameListener extends BaseControlListener {
    public TameListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onTame(EntityTameEvent event) {
        Player p = Bukkit.getPlayer(event.getOwner().getUniqueId());

        if (validator.canTame(p, event.getEntityType())) return;

        event.setCancelled(true);
    }
}
