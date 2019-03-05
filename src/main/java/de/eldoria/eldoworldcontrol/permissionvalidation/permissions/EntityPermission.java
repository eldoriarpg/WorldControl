package de.eldoria.eldoworldcontrol.permissionvalidation.permissions;

import de.eldoria.eldoworldcontrol.permissionvalidation.Permission;
import org.bukkit.entity.Player;

public enum EntityPermission {
    ENTITY, MOB_TARGET, TAME, RIDE;

    public static boolean checkPermission(Player p, EntityPermission perm) {
        return checkPermission(p, perm, "");
    }

    public static boolean checkPermission(Player p, EntityPermission perm, String addition) {
        if (p.hasPermission(Permission.ENTITY)) {
            return true;
        }

        switch (perm) {
            case MOB_TARGET:
                return p.hasPermission(Permission.MOB_TARGET + addition);
            case TAME:
                return p.hasPermission(Permission.TAME + addition);
            case RIDE:
                return p.hasPermission(Permission.RIDE + addition);
        }
        return false;
    }
}
