package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener extends BaseControlListener {
    public HungerListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        Player p = (Player) event.getEntity();

        if (validator.canReceiveHunger(p)) return;

        sender.sendLocalizedError(p, "permission.error.hunger");
        event.setCancelled(true);
        event.setFoodLevel(20);
    }
}
