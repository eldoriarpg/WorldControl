package de.eldoria.eldoworldcontrol.core.config.permissiongroups;

import de.eldoria.eldoutilities.serialization.SerializationUtil;
import de.eldoria.eldoutilities.serialization.TypeResolvingMap;
import de.eldoria.eldoutilities.utils.EnumUtil;
import de.eldoria.eldoworldcontrol.core.EldoWorldControl;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SerializableAs("ewcPermissionGroups")
public class PermissionGroups implements ConfigurationSerializable {
    private final Map<Material, Set<PermissionGroup>> materialGroups = new EnumMap<>(Material.class);
    private final Map<EntityType, Set<PermissionGroup>> entityGroups = new EnumMap<>(EntityType.class);
    private final Map<PotionType, Set<PermissionGroup>> potionGroups = new EnumMap<>(PotionType.class);
    private final Map<EntityDamageEvent.DamageCause, Set<PermissionGroup>> damageCauseGroups = new EnumMap<>(EntityDamageEvent.DamageCause.class);

    public PermissionGroups(Map<String, Object> objectMap) {
        TypeResolvingMap map = SerializationUtil.mapOf(objectMap);
        List<PermissionGroup> groups = map.getValue("permissionGroups");

        for (PermissionGroup group : groups) {
            for (String entry : group.getEntries()) {
                List<EnumType> enumTypes = getEnumTypes(entry);
                if (enumTypes.isEmpty()) {
                    EldoWorldControl.logger().warning("Could not parse " + entry + " in group " + group.getName());
                    continue;
                }
                for (EnumType enumType : enumTypes) {
                    switch (enumType) {
                        case MATERIAL:
                            materialGroups.computeIfAbsent(Material.matchMaterial(entry),
                                    k -> new HashSet<>()).add(group);
                            break;
                        case ENTITY:
                            entityGroups.computeIfAbsent(EnumUtil.parse(entry, EntityType.class),
                                    k -> new HashSet<>()).add(group);
                            break;
                        case POTION:
                            potionGroups.computeIfAbsent(EnumUtil.parse(entry, PotionType.class),
                                    k -> new HashSet<>()).add(group);
                            break;
                        case CAUSE:
                            damageCauseGroups.computeIfAbsent(EnumUtil.parse(entry, EntityDamageEvent.DamageCause.class),
                                    k -> new HashSet<>()).add(group);
                            break;
                    }
                }
            }
        }
    }

    public PermissionGroups() {

    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Set<PermissionGroup> groups = new HashSet<>();
        materialGroups.values().forEach(groups::addAll);
        entityGroups.values().forEach(groups::addAll);
        potionGroups.values().forEach(groups::addAll);
        damageCauseGroups.values().forEach(groups::addAll);

        return SerializationUtil.newBuilder()
                .add("permissionGroups", new ArrayList<>(groups))
                .build();
    }

    private List<EnumType> getEnumTypes(String string) {
        List<EnumType> types = new ArrayList<>();

        if (Material.matchMaterial(string) != null) {
            types.add(EnumType.MATERIAL);
        }
        if (EnumUtil.parse(string, EntityType.class) != null) {
            types.add(EnumType.ENTITY);
        }
        if (EnumUtil.parse(string, PotionType.class) != null) {
            types.add(EnumType.POTION);
        }
        if (EnumUtil.parse(string, EntityDamageEvent.DamageCause.class) != null) {
            types.add(EnumType.CAUSE);
        }
        return types;
    }

    public Set<PermissionGroup> getGroups(Material material) {
        return materialGroups.get(material);
    }
    public Set<PermissionGroup> getGroups(EntityType material) {
        return entityGroups.get(material);
    }
    public Set<PermissionGroup> getGroups(PotionType material) {
        return potionGroups.get(material);
    }
    public Set<PermissionGroup> getGroups(EntityDamageEvent.DamageCause material) {
        return damageCauseGroups.get(material);
    }

    private enum EnumType {MATERIAL, ENTITY, POTION, CAUSE}
}
