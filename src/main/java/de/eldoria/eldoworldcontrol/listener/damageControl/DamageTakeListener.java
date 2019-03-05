package de.eldoria.eldoworldcontrol.listener.damageControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.DamagePermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageTakeListener implements Listener {

    @EventHandler
    public void onDamageTake(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (!DamagePermission.checkPermission(p,DamagePermission.RECEIVE_DAMAGE)) {
                event.setCancelled(true);
                p.setHealth(20);
            }
        }
    }
}
