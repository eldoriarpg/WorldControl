package de.eldoria.eldoworldcontrol.listener.playerbehaviour;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.PlayerBehaviourPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener implements Listener {
    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        Player p = (Player) event.getEntity();
        if (!PlayerBehaviourPermission.checkPermission(p, PlayerBehaviourPermission.RECEIVE_HUNGER)) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }

}
