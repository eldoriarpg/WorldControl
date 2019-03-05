package de.eldoria.eldoworldcontrol.listener.entityBehaviour;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.ActionPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class InteractListener implements Listener {
     @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event){
         Player p = event.getPlayer();
         if(!ActionPermission.checkPermission(p, ActionPermission.INTERACT, event.getRightClicked().getType().toString())){
             event.setCancelled(true);
         }
     }
}
