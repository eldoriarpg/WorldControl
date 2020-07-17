package de.eldoria.eldoworldcontrol;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public final class ConfigValidator {
    private static final String LISTENER = "listener.";

    private ConfigValidator() {
    }

    public static void validate(Plugin plugin) {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        setIfAbsent(config, LISTENER + "BedListener", false);
        setIfAbsent(config, LISTENER + "BreakListener", false);
        setIfAbsent(config, LISTENER + "BreedListener", false);
        setIfAbsent(config, LISTENER + "BucketListener", false);
        setIfAbsent(config, LISTENER + "ConsumeListener", false);
        setIfAbsent(config, LISTENER + "CraftListener", false);
        setIfAbsent(config, LISTENER + "DamageDealByEntityListener", false);
        setIfAbsent(config, LISTENER + "DamageTakeByEntityListener", false);
        setIfAbsent(config, LISTENER + "DropListener", false);
        setIfAbsent(config, LISTENER + "EnchantListener", false);
        setIfAbsent(config, LISTENER + "EntityInteractListener", false);
        setIfAbsent(config, LISTENER + "EntityLootListener", false);
        setIfAbsent(config, LISTENER + "FishListener", false);
        setIfAbsent(config, LISTENER + "HungerListener", false);
        setIfAbsent(config, LISTENER + "MaterialInteractListener", false);
        setIfAbsent(config, LISTENER + "PickupListener", false);
        setIfAbsent(config, LISTENER + "PlaceListener", false);
        setIfAbsent(config, LISTENER + "RideListener", false);
        setIfAbsent(config, LISTENER + "ShearListener", false);
        setIfAbsent(config, LISTENER + "SmeltListener", false);
        setIfAbsent(config, LISTENER + "TameListener", false);
        setIfAbsent(config, LISTENER + "TargetListener", false);
        setIfAbsent(config, LISTENER + "ThrowListener", false);
        setIfAbsent(config, LISTENER + "UseListener", false);

        plugin.saveConfig();
    }

    private static void setIfAbsent(FileConfiguration config, String path, Object value) {
        if (!config.isSet(path)) {
            config.set(path, value);
        }
    }
}
