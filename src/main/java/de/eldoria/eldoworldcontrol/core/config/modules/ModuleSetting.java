package de.eldoria.eldoworldcontrol.core.config.modules;

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
    private final String clazzName;
    private boolean enabled = false;
    private boolean errorMessageEnabled = true;
    private transient Class<? extends BaseControlListener> clazz = null;

    public ModuleSetting(Class<? extends BaseControlListener> clazz) {
        this.clazzName = clazz.getSimpleName();
        this.clazz = clazz;
    }

    public ModuleSetting(Map<String, Object> objectMap) {
        TypeResolvingMap map = SerializationUtil.mapOf(objectMap);
        clazzName = map.getValue("clazz");
        enabled = map.getValue("enabled");
        errorMessageEnabled = map.getValue("errorMessageEnabled");
    }

    public void setClazz(Class<? extends BaseControlListener> clazz) {
        if (this.clazz != null) return;
        this.clazz = clazz;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return SerializationUtil.newBuilder()
                .add("clazz", clazzName)
                .add("enabled", enabled)
                .add("errorMessageEnabled", errorMessageEnabled)
                .build();
    }
}
