package de.eldoria.eldoworldcontrol.core.config.dropreplacements;

import de.eldoria.eldoutilities.serialization.SerializationUtil;
import de.eldoria.eldoutilities.serialization.TypeResolvingMap;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@SerializableAs("ewcDropReplacements")
public class DropReplacements implements ConfigurationSerializable {
    private final Map<Material, DropReplacement> replacementMap = new EnumMap<>(Material.class);

    public DropReplacements() {
    }

    public DropReplacements(Map<String, Object> objectMap) {
        TypeResolvingMap map = SerializationUtil.mapOf(objectMap);
        List<DropReplacement> replacements = map.getValueOrDefault("replacements", Collections.emptyList());
        replacements.forEach(r -> replacementMap.put(r.getMaterial(), r));
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return SerializationUtil.newBuilder()
                .add("replacements", new ArrayList<>(replacementMap.values()))
                .build();
    }

    public boolean containsKey(Material material) {
        return replacementMap.containsKey(material);
    }

    public DropReplacement get(Material key) {
        return replacementMap.get(key);
    }
}
