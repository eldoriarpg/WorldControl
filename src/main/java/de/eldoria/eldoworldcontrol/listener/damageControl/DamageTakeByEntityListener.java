package de.eldoria.eldoworldcontrol.listener.damageControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.DamagePermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
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
