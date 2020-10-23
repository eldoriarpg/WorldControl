package de.eldoria.eldoworldcontrol.core;

import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.eldoworldcontrol.command.WorldControlCommand;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.config.Config;
import de.eldoria.eldoworldcontrol.core.config.General;
import de.eldoria.eldoworldcontrol.core.config.dropreplacements.DropReplacement;
import de.eldoria.eldoworldcontrol.core.config.dropreplacements.DropReplacements;
import de.eldoria.eldoworldcontrol.core.config.modules.ModuleSetting;
import de.eldoria.eldoworldcontrol.core.config.modules.Modules;
import de.eldoria.eldoworldcontrol.core.config.permissiongroups.PermissionGroup;
import de.eldoria.eldoworldcontrol.core.config.permissiongroups.PermissionGroups;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionValidator;
import de.eldoria.eldoworldcontrol.core.permissions.PermissionVerboseLogger;
import de.eldoria.eldoworldcontrol.core.reloading.SharedData;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class EldoWorldControl extends EldoPlugin {
    private static boolean debug = false;
    private PermissionValidator permissionValidator;
    private boolean initialized = false;
    private Config config;
    private SharedData data;

    @Override
    public void onDisable() {
        getLogger().info("§2World Control shutdown!");
    }

    @Override
    public void onEnable() {
        getLogger().info("§2Initializing World Control!");

        if (!initialized) {
            initSerialization();
            config = new Config(this);

            MessageSender.create(this, "§6[WC§6]", '2', 'c');
            ILocalizer.create(this,
                    config.getGeneral().getLanguage(),
                    "messages",
                    "messages",
                    Locale.US,
                    "en_US", "de_DE");

            PermissionVerboseLogger logger = new PermissionVerboseLogger();
            permissionValidator = new PermissionValidator(logger);
            data = new SharedData(config, permissionValidator);

            registerCommand("worldControl", new WorldControlCommand(this, logger));
            initialized = true;
            //new SpigotUpdateChecker(new SpigotUpdateData(this, config.getGeneral().getPermissionSpace() + "command.reload", true, 0));
        }

        // Regular setup with the reload method
        reload();
        getLogger().info("§2World Control initialized!");
    }

    public void reload() {
        config.reload();
        debug = config.getGeneral().isDebug();
        ILocalizer.getPluginLocalizer(this).setLocale(config.getGeneral().getLanguage());
        permissionValidator.reload(data);
        //HandlerList.unregisterAll(this);
        initModules();
    }

    private void initModules() {
        data.getConfig().getModules().getModuleSettings().values().forEach(this::initModule);
    }

    private void initModule(ModuleSetting setting) {
        Class<? extends BaseControlListener> listenerClazz = setting.getClazz();

        // state of modules
        boolean state = setting.isEnabled();

        // search for the listener in registered listeners.
        Optional<BaseControlListener> registeredListener = getRegisteredListener(listenerClazz);

        if (state) {
            // check if listener is already registered. reload if registered
            if (registeredListener.isPresent()) {
                if (debug) {
                    getLogger().info("Module " + listenerClazz + " is active.");
                }
                registeredListener.get().reload(data);
                return;
            }

            // Register a new plugin handler and initialize
            try {
                BaseControlListener listener = listenerClazz
                        .getConstructor(PermissionValidator.class)
                        .newInstance(data.getPermissionValidator());
                listener.init(data);
                getPluginManager().registerEvents(listener, this);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                getLogger().warning("Something went wrong while initialising: " + setting.getClazz());
            }
            if (debug) {
                getLogger().info("Registered module " + setting.getClazz());
            }
        } else {
            // check if listener is not registered
            if (!registeredListener.isPresent()) {
                if (debug) {
                    getLogger().info("Module " + setting.getClazz() + " in inactive.");
                }
                return;
            }

            // unregister listener
            HandlerList.unregisterAll(registeredListener.get());
            if (debug) {
                getLogger().info("Unregistered modules " + setting.getClazz());
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

    private void initSerialization() {
        ConfigurationSerialization.registerClass(DropReplacement.class);
        ConfigurationSerialization.registerClass(DropReplacements.class);
        ConfigurationSerialization.registerClass(PermissionGroup.class);
        ConfigurationSerialization.registerClass(PermissionGroups.class);
        ConfigurationSerialization.registerClass(General.class);
        ConfigurationSerialization.registerClass(Modules.class);
        ConfigurationSerialization.registerClass(ModuleSetting.class);
    }
}
