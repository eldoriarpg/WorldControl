package de.eldoria.eldoworldcontrol.core.config;

import de.eldoria.eldoutilities.serialization.SerializationUtil;
import de.eldoria.eldoutilities.serialization.TypeResolvingMap;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@SerializableAs("ewcGeneral")
public class General implements ConfigurationSerializable {
    private boolean debug = true;
    private String language = "en_US";

    public General() {
    }

    public General(Map<String, Object> objectMap){
        TypeResolvingMap map = SerializationUtil.mapOf(objectMap);
        debug = map.getValueOrDefault("debug", debug);
        language = map.getValueOrDefault("language", language);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return null;
    }
}
