package de.eldoria.eldoworldcontrol.core.config;

import de.eldoria.eldoutilities.serialization.SerializationUtil;
import de.eldoria.eldoutilities.serialization.TypeResolvingMap;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@SerializableAs("ewcModuleSetting")
public class ModuleSetting implements ConfigurationSerializable {
    private String clazz;
    private boolean enabled = true;
    private boolean errorMessageEnabled = true;

    public ModuleSetting(Class<? extends BaseControlListener> clazz) {
        this.clazz = clazz.getSimpleName();
    }

    public ModuleSetting(Map<String, Object> objectMap) {
        TypeResolvingMap map = SerializationUtil.mapOf(objectMap);
        clazz = map.getValue("clazz");
        enabled = map.getValue("enabled");
        errorMessageEnabled = map.getValue("errorMessageEnabled");
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return SerializationUtil.newBuilder()
                .add("clazz", clazz)
                .add("enabled", enabled)
                .add("errorMessageEnabled", errorMessageEnabled)
                .build();
    }
}
