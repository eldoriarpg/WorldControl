package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantListener extends BaseControlListener {
    public EnchantListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        Player p = event.getEnchanter();
        Material materialName = event.getItem().getType();

        if(validator.canEnchant(p, materialName)) return;

        event.setCancelled(true);
    }
}
