package de.eldoria.eldoworldcontrol.controllistener.playerbehaviour;

import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
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

        event.setCancelled(true);
        event.setFoodLevel(20);
    }
}
