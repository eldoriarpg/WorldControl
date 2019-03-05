package de.eldoria.eldoworldcontrol.listener.damageControl;

import de.eldoria.eldoworldcontrol.permissionValidation.Enums.DamagePermission;
import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
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
