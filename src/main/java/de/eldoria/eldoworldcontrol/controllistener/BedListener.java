package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedListener extends BaseControlListener {

    public BedListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        Player p = event.getPlayer();

        if (validator.canEnterBed(p)) return;
        sender.sendLocalizedError(p, "permission.error.enterBed");
        event.setCancelled(true);
    }
}
