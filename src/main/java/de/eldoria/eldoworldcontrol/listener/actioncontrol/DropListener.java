package de.eldoria.eldoworldcontrol.listener.actioncontrol;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.ActionPermission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player p = event.getPlayer();
        Material materialName = event.getItemDrop().getItemStack().getType();
        //TODO: Creative Block
        if (ActionPermission.checkPermission(p, ActionPermission.DROP , materialName.toString())) {
            return;
        }
        event.setCancelled(true);
    }

}
