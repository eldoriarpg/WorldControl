package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class ThrowListener extends BaseControlListener {
    public ThrowListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onThrow(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (isNotThrowable(event.getItem().getType())) return;

        Player p = event.getPlayer();
        Material material = event.getItem().getType();

        if (material == Material.SPLASH_POTION || material == Material.LINGERING_POTION) {
            PotionMeta meta = (PotionMeta) event.getItem().getItemMeta();
            PotionType data = meta.getBasePotionData().getType();

            if (material == Material.SPLASH_POTION && validator.canThrowSplashPotion(p, data)) {
                return;
            } else if (validator.canThrowLingeringPotion(p, data)) {
                return;
            }
        } else {
            if (validator.canThrow(p, material)) return;
        }

        sender.sendLocalizedError(p, "permission.error.throw",
                Replacement.create("MAT", material, '6'));
        event.setCancelled(true);
    }


    private boolean isNotThrowable(Material mat) {
        switch (mat) {
            case EGG:
            case ENDER_EYE:
            case ENDER_PEARL:
            case SNOWBALL:
            case EXPERIENCE_BOTTLE:
            case POTION:
            case LINGERING_POTION:
            case TRIDENT:
                return false;

        }
        return true;
    }
}
