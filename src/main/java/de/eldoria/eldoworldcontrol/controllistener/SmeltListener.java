package de.eldoria.eldoworldcontrol.controllistener;

import de.eldoria.eldoutilities.localization.Replacement;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class SmeltListener extends BaseControlListener {
    public SmeltListener(PermissionValidator validator) {
        super(validator);
    }

    // TODO: This listener currently works only, when a user directly puts the items into the furnace.
    // This will not detect if a user uses a hopper to fill the furnace.
    // This could be done with a recursive scan from a chest container and trace back the hopper order.
    // This is currently too much effort for a small payout and will also needs a database,
    // which makes this plugin less leightweight for the moment.
    @EventHandler
    public void onSmeltInput(InventoryClickEvent event) {
        InventoryAction action = event.getAction();

        if (!(action == InventoryAction.PLACE_ALL
                || action == InventoryAction.PLACE_SOME
                || action == InventoryAction.PLACE_ONE)) {
            return;
        }

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getType() != InventoryType.FURNACE) return;

        if (event.getSlotType() != InventoryType.SlotType.CRAFTING) return;

        if (!(event.getClickedInventory() instanceof Player) || event.getCurrentItem() == null) return;

        Player player = (Player) event.getWhoClicked();
        if (validator.canSmelt(player, event.getCurrentItem().getType())) return;

        sender.sendLocalizedError(player, "permission.error.smelt",
                Replacement.create("MAT", event.getCurrentItem().getType(), '6'));
        event.setCancelled(true);
    }
}
