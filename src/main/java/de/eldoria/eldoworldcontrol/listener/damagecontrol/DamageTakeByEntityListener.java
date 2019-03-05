package de.eldoria.eldoworldcontrol.listener.damagecontrol;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.DamagePermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageTakeByEntityListener implements Listener {
    @EventHandler
    public void onDamageTakeByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (!DamagePermission.checkPermission(p,DamagePermission.TAKE, event.getDamager().getType().toString())) {
                event.setCancelled(true);
                event.setDamage(0);
            }
        }
    }
}
