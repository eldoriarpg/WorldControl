package de.eldoria.eldoworldcontrol.listener.damagecontrol;

import de.eldoria.eldoworldcontrol.permissionvalidation.permissions.DamagePermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageDealByEntityListener implements Listener {
    @EventHandler
    public void onDamageDeal(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player p = (Player) event.getDamager();
            if (!DamagePermission.checkPermission(p,DamagePermission.DEAL, event.getEntity().getType().toString())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

}
