package de.eldoria.eldoworldcontrol.permissionvalidation.permissions;

import de.eldoria.eldoworldcontrol.permissionvalidation.Permission;
import org.bukkit.entity.Player;

public enum ActionPermission {
    ACTION, CRAFT, DROP, ENCHANT, INTERACT, PICKUP, USE_BED, BUCKET, BUCKET_FILL, BUCKET_EMPTY, USE, THROW;

    public static boolean checkPermission(Player p, ActionPermission perm) {
        return checkPermission(p, perm, "");
    }

    public static boolean checkPermission(Player p, ActionPermission perm, String addition) {
        if (p.hasPermission(Permission.ACTION)) {
            return true;
        }

        switch (perm) {
            case CRAFT:
                return p.hasPermission(Permission.CRAFT + addition);
            case DROP:
                return p.hasPermission(Permission.DROP + addition);
            case ENCHANT:
                return p.hasPermission(Permission.ENCHANT + addition);
            case INTERACT:
                return p.hasPermission(Permission.INTERACT + addition);
            case PICKUP:
                return p.hasPermission(Permission.PICKUP + addition);
            case USE_BED:
                return p.hasPermission(Permission.USE_BED + addition);
            case BUCKET:
                return p.hasPermission(Permission.BUCKET);
            case BUCKET_FILL:
                if (p.hasPermission(Permission.BUCKET))
                    return true;
                return p.hasPermission(Permission.BUCKET_FILL + addition);
            case BUCKET_EMPTY:
                if (p.hasPermission(Permission.BUCKET))
                    return true;
                return p.hasPermission(Permission.BUCKET_EMPTY + addition);
            case USE:
                return p.hasPermission(Permission.USE + addition);
            case THROW:
                return p.hasPermission(Permission.THROW + addition);



        }

        return false;
    }
}
