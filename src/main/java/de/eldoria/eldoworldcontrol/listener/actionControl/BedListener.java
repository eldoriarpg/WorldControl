package de.eldoria.eldoworldcontrol.listener.actionControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.ActionPermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedListener implements Listener {
    @EventHandler
    public void OnBedEnter(PlayerBedEnterEvent event){
        if(event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK){
            Player p = event.getPlayer();
            if(!ActionPermission.checkPermission(p,ActionPermission.USE_BED)){
                event.setCancelled(true);
            }
        }
    }
}
