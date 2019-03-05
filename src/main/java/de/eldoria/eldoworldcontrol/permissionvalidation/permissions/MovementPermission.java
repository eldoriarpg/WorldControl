package de.eldoria.eldoworldcontrol.permissionvalidation.permissions;

import de.eldoria.eldoworldcontrol.permissionvalidation.Permission;
import org.bukkit.entity.Player;

public enum MovementPermission {
    MOVEMENT, SNEAK, SPRINT, SWIM, GLIDE;

    public static boolean checkPermission(Player p, MovementPermission perm) {
        return checkPermission(p, perm, "");
    }

    public static boolean checkPermission(Player p, MovementPermission perm, String addition) {
        if (p.hasPermission(Permission.MOVEMENT)) {
            return true;
        }

        switch (perm) {
            case SNEAK:
                return p.hasPermission(Permission.SNEAK + addition);
            case SPRINT:
                return p.hasPermission(Permission.SPRINT + addition);
            case SWIM:
                return p.hasPermission(Permission.SWIM + addition);
            case GLIDE:
                return p.hasPermission(Permission.GLIDE + addition);
        }
        return false;
    }
}
