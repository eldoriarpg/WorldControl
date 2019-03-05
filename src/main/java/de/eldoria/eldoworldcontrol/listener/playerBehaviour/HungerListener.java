package de.eldoria.eldoworldcontrol.listener.playerBehaviour;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.PlayerBehaviourPermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
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
