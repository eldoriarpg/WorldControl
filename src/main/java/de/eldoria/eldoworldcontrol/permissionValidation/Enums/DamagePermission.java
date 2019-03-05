package de.eldoria.eldoworldcontrol.permissionValidation.Enums;

import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.entity.Player;

public enum DamagePermission {
    DAMAGE, RECEIVE_DAMAGE, TAKE, DEAL;

    public static boolean checkPermission(Player p, DamagePermission perm) {
        return checkPermission(p, perm, "");
    }

    public static boolean checkPermission(Player p, DamagePermission perm, String addition) {
        if (p.hasPermission(Permission.DAMAGE)) {
            return true;
        }

        switch (perm) {
            case RECEIVE_DAMAGE:
                return p.hasPermission(Permission.RECEIVE_DAMAGE + addition);
            case TAKE:
                return p.hasPermission(Permission.DAMAGE_TAKE + addition);
            case DEAL:
                return p.hasPermission(Permission.DAMAGE_DEAL + addition);
        }
        return false;
    }
}
