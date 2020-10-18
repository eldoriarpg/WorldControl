package de.eldoria.eldoworldcontrol.core;

import de.eldoria.eldoutilities.localization.ILocalizer;
import de.eldoria.eldoutilities.messages.MessageSender;
import de.eldoria.eldoutilities.plugin.EldoPlugin;
import de.eldoria.eldoworldcontrol.command.WorldControlCommand;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import de.eldoria.eldoworldcontrol.core.config.Config;
import de.eldoria.eldoworldcontrol.core.config.General;
import de.eldoria.eldoworldcontrol.core.config.ModuleSetting;
import de.eldoria.eldoworldcontrol.core.config.Modules;
import de.eldoria.eldoworldcontrol.core.config.dropreplacements.DropReplacement;
import de.eldoria.eldoworldcontrol.core.config.dropreplacements.DropReplacements;
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
import java.util.Map;
import java.util.Optional;

public class EldoWorldControl extends EldoPlugin {
    private static boolean debug = false;
    private PermissionValidator permissionValidator;
    private final boolean initialized = false;
    private Config config;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        getLogger().info("§2Initializing Eldo World Control!");

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

            registerCommand("worldControl", new WorldControlCommand(this, logger));
        }

        // Regular setup with the reload method
        reload();
    }

    public void reload() {
        this.reloadConfig();
        debug = config.getGeneral().isDebug();
        SharedData data = new SharedData(config, permissionValidator);
        permissionValidator.reload(data);
        HandlerList.unregisterAll(this);
        initModules(data);
    }

    private void initModules(SharedData data) {
        PermissionValidator validator = data.getPermissionValidator();

        for (Map.Entry<Class<? extends BaseControlListener>, ModuleSetting> entry
                : data.getConfig().getModules().getModuleSettings().entrySet()) {

            Class<? extends BaseControlListener> clazz = entry.getKey();
            ModuleSetting setting = entry.getValue();

            // state of modules
            boolean state = setting.isEnabled();

            Class<? extends BaseControlListener> listenerClazz;

            // Find listener class
            try {
                Class<?> loadClass = getClassLoader()
                        .loadClass("de.eldoria.eldoworldcontrol.controllistener." + setting.getClazz());
                listenerClazz = (Class<? extends BaseControlListener>) loadClass;
            } catch (ClassNotFoundException e) {
                getLogger().warning("Invalid modules: " + setting.getClazz());
                continue;
            } catch (ClassCastException e) {
                getLogger().warning(setting.getClazz() + " is not a listener.");
                continue;
            }

            Optional<BaseControlListener> registeredListener = getRegisteredListener(listenerClazz);

            if (state) {
                // check if listener is already registered. reload if registered
                if (registeredListener.isPresent()) {
                    getLogger().info("Module " + listenerClazz + " is active.");
                    registeredListener.get().reload(data);
                    continue;
                }

                // Register a new plugin handler and initialize
                try {
                    BaseControlListener listener = listenerClazz
                            .getConstructor(PermissionValidator.class)
                            .newInstance(validator);
                    listener.init(data);
                    getPluginManager().registerEvents(listener, this);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    getLogger().warning("Something went wrong while initialising: " + setting.getClazz());
                }
                getLogger().info("Registered modules " + setting.getClazz());
            } else {
                // check if listener is not registered
                if (!registeredListener.isPresent()) {
                    getLogger().info("Module " + setting.getClazz() + " in inactive.");
                    continue;
                }

                // unregister listener
                HandlerList.unregisterAll(registeredListener.get());
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
