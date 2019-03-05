package de.eldoria.eldoworldcontrol.permissionvalidation.permissions;

import de.eldoria.eldoworldcontrol.permissionvalidation.Permission;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public enum ContainerPermission {
    CONTAINER, DEPOSIT_ITEMS, WITHDRAW_ITEMS;

    public static boolean checkPermission(Player p, InventoryType type, ContainerPermission perm) {
        return checkPermission(p, type, perm, "");
    }

    public static boolean checkPermission(Player p, InventoryType type, ContainerPermission perm, String addition) {
        if (p.hasPermission(Permission.CONTAINER)) {
            if (p.getGameMode() == GameMode.CREATIVE) {
                if (p.hasPermission(Permission.CONTAINER + "." + Permission.CREATIVE_MODE)) {
                    return true;
                }
            } else {
                return true;
            }
        }

        if (p.hasPermission(getContainerPerm(type))) {
            if (p.getGameMode() == GameMode.CREATIVE) {
                if (p.hasPermission(getContainerPerm(type) + "." + Permission.CREATIVE_MODE)){
                    return true;
                }
            } else {
                return true;
            }
        }

        switch (perm) {
            case DEPOSIT_ITEMS:
                return p.hasPermission(getContainerPerm(type) + Permission.DEPOSIT_ITEM + addition);
            case WITHDRAW_ITEMS:
                return p.hasPermission(getContainerPerm(type) + Permission.WITHDRAW_ITEM + addition);
        }
        return false;
    }


    private static String getContainerPerm(InventoryType type) {
        switch (type) {
            case CHEST:
                return Permission.CHEST;
            case DISPENSER:
                return Permission.DISPENSER;
            case DROPPER:
                return Permission.DROPPER;
            case FURNACE:
                return Permission.FURNACE;
            case WORKBENCH:
                return Permission.WORKBENCH;
            case CRAFTING:
                return Permission.CRAFTING;
            case ENCHANTING:
                return Permission.ENCHANTING;
            case BREWING:
                return Permission.BREWING;
            case PLAYER:
                return Permission.PLAYER;
            case CREATIVE:
                return Permission.CREATIVE;
            case MERCHANT:
                return Permission.MERCHANT;
            case ENDER_CHEST:
                return Permission.ENDER_CHEST;
            case ANVIL:
                return Permission.ANVIL;
            case BEACON:
                return Permission.BEACON;
            case HOPPER:
                return Permission.HOPPER;
            case SHULKER_BOX:
                return Permission.SHULKER_BOX;
        }
        return "";
    }
}