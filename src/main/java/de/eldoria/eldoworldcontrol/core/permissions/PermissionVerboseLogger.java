package de.eldoria.eldoworldcontrol.core.permissions;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermissionVerboseLogger {

    private final Map<UUID, LoggingSettings> verboseLogging = new HashMap<>();

    public void log(Player p, String permission, boolean state) {
        verboseLogging.values().forEach(l -> l.dispatchLogMessage(p, permission, state));
    }

    /**
     * Toggle the verbose logging for this player.
     *
     * @param p player to activate the logging for
     * @return true if logging is now active or false if not
     */
    public boolean toggleVerboseLogging(Player p) {
        if (verboseLogging.containsKey(p.getUniqueId())) {
            verboseLogging.remove(p.getUniqueId());
            return false;
        }

        verboseLogging.put(p.getUniqueId(), new LoggingSettings(p));
        return true;
    }

    /**
     * Set the verbose logging to a state
     *
     * @param p     player to set the loggin state for
     * @param state state of logging
     */
    public void setVerboseLoggingState(Player p, boolean state) {
        if (state) {
            verboseLogging.put(p.getUniqueId(), new LoggingSettings(p));
        } else {
            verboseLogging.remove(p.getUniqueId());
        }
    }

    /**
     * Activate verbose logging for a target
     *
     * @param p      player to activate logging for
     * @param target target of player
     */
    public void setVerboseLoggingTarget(Player p, Player target) {
        verboseLogging.put(p.getUniqueId(), new LoggingSettings(p, target));
    }

}
