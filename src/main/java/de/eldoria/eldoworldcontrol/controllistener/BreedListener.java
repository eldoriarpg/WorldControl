package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityBreedEvent;

public class BreedListener extends BaseControlListener {
    public BreedListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onBreed(EntityBreedEvent event) {
        LivingEntity breeder = event.getBreeder();
        if (!(breeder instanceof Player)) return;

        Player player = (Player) breeder;

        if (validator.canBreed(player, event.getEntityType())) {
            return;
        }

        sender.sendLocalizedError(player, "permission.error.breed",
                Replacement.create("ENTITY", event.getEntityType(),'6'));
        event.setCancelled(true);
        event.setExperience(0);
    }
}
