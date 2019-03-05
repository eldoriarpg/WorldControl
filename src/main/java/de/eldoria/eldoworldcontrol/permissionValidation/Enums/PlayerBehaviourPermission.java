package de.eldoria.eldoworldcontrol.permissionValidation.Enums;

import de.eldoria.eldoworldcontrol.permissionValidation.Permission;
import org.bukkit.entity.Player;

public enum PlayerBehaviourPermission {
    PLAYER_BEHAVIOUR, RECEIVE_HUNGER, LOGIN;

    public static boolean checkPermission(Player p, PlayerBehaviourPermission perm) {
        return checkPermission(p, perm, "");
    }

    public static boolean checkPermission(Player p, PlayerBehaviourPermission perm, String addition) {
        if (p.hasPermission(Permission.PLAYER_BEHAVIOUR)) {
            return true;
        }

        switch (perm) {
            case RECEIVE_HUNGER:
                return p.hasPermission(Permission.RECEIVE_HUNGER);
            case LOGIN:
                return p.hasPermission(Permission.LOGIN);
        }
        return false;
    }
}