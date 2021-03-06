package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

public class FishListener extends BaseControlListener {
    public FishListener(PermissionValidator validator) {
        super(validator);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        // only block the event when a player caught a fish or something likely.
        // Don't apply to caught entity, because this could also be applied to a catched entity

        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;

        // The item should be always an item.
        Item caught = (Item) event.getCaught();

        // however... better be sure.
        if (caught == null) return;

        if (validator.canFish(event.getPlayer(), caught.getItemStack().getType())) {
            return;
        }

        if (messages) {
            sender.sendLocalizedError(event.getPlayer(), "permission.error.fish",
                    Replacement.create("MAT", caught.getItemStack().getType(), '6'));
        }

        // remove fished item
        event.getCaught().remove();
        //remove exp
        event.setExpToDrop(0);

        // dont cancel event here. would case the hook to stay in water
    }
}
