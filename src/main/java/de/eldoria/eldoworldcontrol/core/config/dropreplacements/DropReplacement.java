package de.eldoria.eldoworldcontrol.core.config.dropreplacements;

import de.eldoria.eldoutilities.serialization.SerializationUtil;
import de.eldoria.eldoutilities.serialization.TypeResolvingMap;
import de.eldoria.eldoutilities.utils.EnumUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@SerializableAs("ewcDropReplacement")
public class DropReplacement implements ConfigurationSerializable {
    private final Material material;
    private final Material replacement;
    private final int amount;

    public DropReplacement(Map<String, Object> objectMap) {
        TypeResolvingMap map = SerializationUtil.mapOf(objectMap);
        material = map.getValueOrDefault("material", Material.AIR, s -> EnumUtil.parse(s, Material.class));
        replacement = map.getValueOrDefault("replacement", Material.AIR, s -> EnumUtil.parse(s, Material.class));
        amount = map.getValueOrDefault("amount", 1);
    }

    public ItemStack getItemStack() {
        return new ItemStack(replacement, amount);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return SerializationUtil.newBuilder()
                .add("material", material)
                .add("replacement", replacement)
                .add("amount", amount)
                .build();
    }
}
