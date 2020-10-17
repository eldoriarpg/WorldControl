package de.eldoria.eldoworldcontrol.core.permissions;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionVerboseLogger {
    private final Pattern listener = Pattern.compile("controllistener\\.([a-zA-Z]+?)$");
    private final Map<UUID, LoggingSettings> verboseLogging = new HashMap<>();

    public void log(Player p, String permission, boolean state) {
        String s = permission + ": " + (state ? "§agranted" : "§cdenied") + " (" + getListener() + ")";
        verboseLogging.values().forEach(l -> l.dispatchLogMessage(p, s));
    }

    private String getListener() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            Matcher matcher = listener.matcher(stackTraceElement.getClassName());
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "unknown";
    }

    /**
     * Toggle the verbose logging for this player.
     *
     * @param p player to activate the logging for
     *
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
