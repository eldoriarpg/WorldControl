package de.eldoria.eldoworldcontrol.core;

import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.eldoworldcontrol.ConfigValidator;
import de.eldoria.eldoworldcontrol.command.WorldControlCommand;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.data.PermissionGroups;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionVerboseLogger;
import de.eldoria.eldoworldcontrol.core.reloading.SharedData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class EldoWorldControl extends EldoPlugin {
    private static EldoWorldControl instance;
    private static boolean debug = false;
    private final List<BaseControlListener> modules = new ArrayList<>();
    private final Logger logger = Bukkit.getLogger();
    private PermissionGroups groups;
    private PermissionValidator permissionValidator;
    private PluginManager pm;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        logger.info("World control started!");

        // Just do things here which only need a single setup.
        pm = Bukkit.getPluginManager();

        if (instance == null) {
            instance = this;
        }

        MessageSender.create(this, "§6[WC§6]", '2', 'c');
        ILocalizer.create(this,
                getConfig().getString("language", "en_US"),
                "messages",
                "messages",
                Locale.US,
                "en_US", "de_DE");

        PermissionVerboseLogger logger = new PermissionVerboseLogger();
        permissionValidator = new PermissionValidator(logger);

        registerCommand("worldControl", new WorldControlCommand(this, logger));

        // Regular setup with the reload method
        reload();
    }

    public void reload() {
        this.reloadConfig();
        ConfigValidator.validate(this);
        FileConfiguration config = this.getConfig();
        debug = config.getBoolean("debug");
        SharedData data = new SharedData(config, permissionValidator);
        permissionValidator.reload(data);
        HandlerList.unregisterAll(this);
        initModules(data);
    }

    private void initModules(SharedData data) {
        PermissionValidator validator = data.getValidator();
        ConfigurationSection modules = data.getConfiguration().getConfigurationSection("listener");

        // load modules from config
        if (modules == null) {
            logger.warning("No listener section was found.");
            return;
        }

        // load modules names
        Set<String> keys = modules.getKeys(false);

        for (String key : keys) {
            // state of modules
            boolean state = modules.getBoolean(key);
            Class<? extends BaseControlListener> loadedClass;

            // Find listener class
            try {
                Class<?> loadClass = getClassLoader()
                        .loadClass("de.eldoria.eldoworldcontrol.controllistener." + key);
                loadedClass = (Class<? extends BaseControlListener>) loadClass;
            } catch (ClassNotFoundException e) {
                logger.warning("Invalid modules: " + key);
                continue;
            } catch (ClassCastException e) {
                logger.warning(key + " is not a listener.");
                continue;
            }

            Optional<BaseControlListener> registeredListener = getRegisteredListener(loadedClass);

            if (state) {
                // check if listener is already registered. reload if registered
                if (registeredListener.isPresent()) {
                    logger.info("Module " + key + " is active.");
                    registeredListener.get().reload(data);
                    continue;
                }

                // Register a new plugin handler and initialize
                try {
                    BaseControlListener listener = loadedClass
                            .getConstructor(PermissionValidator.class)
                            .newInstance(validator);
                    listener.init(data);
                    pm.registerEvents(listener, this);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    logger.warning("Something went wrong while initialising: " + key);
                }

                logger.info("Registered modules " + key);
            } else {
                // check if listener is not registered
                if (!registeredListener.isPresent()) {
                    logger.info("Module " + key + " in inactive.");
                    continue;
                }

                // unregister listener
                HandlerList.unregisterAll(registeredListener.get());
                logger.info("Unregistered modules " + key);
            }
        }
    }

    private Optional<BaseControlListener> getRegisteredListener(Class<? extends BaseControlListener> checkClass) {
        ArrayList<RegisteredListener> listeners = HandlerList.getRegisteredListeners(this);

        for (RegisteredListener listener : listeners) {
            BaseControlListener baseListener = (BaseControlListener) listener.getListener();
            if (baseListener.getClass() == checkClass) {
                return Optional.of(baseListener);
            }
        }
        return Optional.empty();
    }
}
