package de.eldoria.eldoworldcontrol.permissionValidation.Enums;

import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.entity.Player;

public enum BuildPermission {
    BUILD, BLOCK_BREAK, BLOCK_BREAK_AND_RECEIVE, PLACE;

    public static boolean checkPermission(Player p, BuildPermission perm) {
        return checkPermission(p, perm, "");
    }

    public static boolean checkPermission(Player p, BuildPermission perm, String addition) {
        if (p.hasPermission(Permission.BUILD)) {
            return true;
        }

        switch (perm) {
            case BLOCK_BREAK:
                return p.hasPermission(Permission.BLOCK_BREAK + addition);
            case BLOCK_BREAK_AND_RECEIVE:
                return p.hasPermission(Permission.BLOCK_BREAK_AND_RECEIVE + addition);
            case PLACE:
                return p.hasPermission(Permission.PLACE + addition);
        }
        return false;
    }
}
