package de.eldoria.eldoworldcontrol;

import de.eldoria.eldoworldcontrol.command.BaseCommand;
import de.eldoria.eldoworldcontrol.controllistener.BaseControlListener;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class EldoWorldControl extends JavaPlugin {
    private PermissionGroups groups;
    private PermissionValidator permissionValidator;
    private final List<BaseControlListener> modules = new ArrayList<>();
    private final Logger logger = Bukkit.getLogger();

    private static EldoWorldControl instance;
    private static boolean debug = false;

    private PluginManager pm;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        // Just do things here which only need a single setup.
        pm = Bukkit.getPluginManager();

        if (instance == null) {
            instance = this;
        }

        PermissionVerboseLogger logger = new PermissionVerboseLogger();
        permissionValidator = new PermissionValidator(logger);

        this.getCommand("eldoworldcontrol").setExecutor(new BaseCommand(logger));

        // Regular setup with the reload method
        reload();
    }

    public void reload() {
        this.reloadConfig();
        FileConfiguration config = this.getConfig();
        debug = config.getBoolean("debug");
        var data = new SharedData(config, permissionValidator);
        permissionValidator.reload(data);
        HandlerList.unregisterAll(this);
        initModules(data);
    }

    private void initModules(SharedData data) {
        PermissionValidator validator = data.getValidator();
        ConfigurationSection module = data.getConfiguration().getConfigurationSection("module");

        // load modules from config
        if (module == null) {
            logger.warning("No module section was found.");
            return;
        }

        // load module names
        Set<String> keys = module.getKeys(false);

        for (var key : keys) {
            // state of module
            boolean state = module.getBoolean(key);
            Class<? extends BaseControlListener> loadedClass;

            // Find listener class
            try {
                Class<?> loadClass = getClassLoader().loadClass(key);
                if (loadClass.isAssignableFrom(BaseControlListener.class)) {
                    loadedClass = (Class<? extends BaseControlListener>) loadClass;
                } else {
                    logger.warning(key + " is not a listener.");
                    continue;
                }
            } catch (ClassNotFoundException e) {
                logger.warning("Invalid module: " + key);
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
                    BaseControlListener listener = loadedClass.getConstructor(PermissionValidator.class).newInstance(validator);
                    listener.init(data);
                    pm.registerEvents(listener, this);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    logger.warning("Something went wrong while initialising: " + key);
                }

                logger.info("Registered module " + key);
            } else {
                // check if listener is not registered
                if (registeredListener.isEmpty()) {
                    logger.info("Module " + key + " in inactive.");
                    continue;
                }

                // unregister listener
                HandlerList.unregisterAll(registeredListener.get());
                logger.info("Unregistered module " + key);
            }
        }
    }

    private Optional<BaseControlListener> getRegisteredListener(Class<? extends BaseControlListener> checkClass) {
        ArrayList<RegisteredListener> listeners = HandlerList.getRegisteredListeners(this);

        for (var listener : listeners) {
            var baseListener = (BaseControlListener) listener.getListener();
            if (baseListener.getClass() == checkClass) {
                return Optional.of(baseListener);
            }
        }
        return Optional.empty();
    }
}
