package de.eldoria.eldoworldcontrol.core.data;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PermissionGroups {
    private final Logger log = Bukkit.getLogger();
    private Map<Material, Set<String>> materialGroups;
    private Map<EntityType, Set<String>> entityGroups;
    private Map<PotionType, Set<String>> potionGroups;
    private Map<EntityDamageEvent.DamageCause, Set<String>> damageCauseGroups;

    public PermissionGroups(FileConfiguration configuration) {
        loadItemGroups(configuration);
    }

    /**
     * Load item groups from config file.
     * @param config file configuration object.
     */
    private void loadItemGroups(FileConfiguration config) {
        materialGroups = new EnumMap<>(Material.class);
        entityGroups = new EnumMap<>(EntityType.class);
        potionGroups = new EnumMap<>(PotionType.class);
        damageCauseGroups = new EnumMap<>(DamageCause.class);

        ConfigurationSection configGroups = config.getConfigurationSection("PermissionGroups");

        if (configGroups == null) {
            log.warning("Permission group section is missing. Ignoring");
            return;
        }

        Set<String> groups = configGroups.getKeys(false);

        // List of not yet detected entries
        Map<String, List<String>> possiblyEntries = new HashMap<>();

        // populate possibly Entites
        for (String group : groups) {
            possiblyEntries.put(group, configGroups.getStringList(group));
        }

        detect(materialGroups, possiblyEntries, Material::matchMaterial);

        detect(entityGroups, possiblyEntries, this::parseEntityType);

        detect(potionGroups, possiblyEntries, this::parsePotionType);

        detect(damageCauseGroups, possiblyEntries, this::parseDamageCause);

        // TODO report unparseable items.
    }

    /**
     * Try to parse the string to a enum.
     *
     * @param matchResults data to match to groups. The key is the name of the group.
     *                     The values will be parsed to a enum value.
     *                     If the value could be parsed the string will be removed from the list.
     * @param matchData    A empty enum map of type E. Parsed values will be added to a set of groups.
     *                     A enum value can have multiple groups.
     * @param function     function to parse a string value to enum of type E.
     *                     Should return null, if the string can't be parsed.
     * @param <E>          Enum type for parsing and mapping of the groups.
     */
    private <E extends Enum<E>> void detect(Map<E, Set<String>> matchResults, Map<String, List<String>> matchData,
                                            Function<String, E> function) {
        List<String> matches = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : matchData.entrySet()) {
            String group = entry.getKey();
            List<E> positiveMatches = entry.getValue().stream()
                    .map(s -> {
                        E match = function.apply(s);
                        if (Objects.nonNull(match)) {
                            matches.add(s);
                        }
                        return match;
                    }).filter(Objects::nonNull)
                    .collect(Collectors.toList());

            for (String match : matches) {
                matchData.get(group).remove(match);
            }

            for (E match : positiveMatches) {
                matchResults.computeIfAbsent(match, k -> new HashSet<>()).add(group);
            }
        }
    }

    /**
     * Parse string to a entity type.
     *
     * @param string string to parse
     * @return entity type value or null if the string could not be parsed
     */
    private EntityType parseEntityType(String string) {
        for (EntityType type : EntityType.values()) {
            if (string.equalsIgnoreCase(type.toString())) {
                return type;
            }
        }
        return null;
    }

    /**
     * Parse string to potion type.
     *
     * @param string string to parse
     * @return potion type value or null if the string could not be parsed
     */
    private PotionType parsePotionType(String string) {
        for (PotionType type : PotionType.values()) {
            if (string.equalsIgnoreCase(type.toString())) {
                return type;
            }
        }
        return null;
    }

    /**
     * Parse string to damage cause.
     *
     * @param string string to parse
     * @return potion type value or null if the string could not be parsed
     */
    private DamageCause parseDamageCause(String string) {
        for (DamageCause type : DamageCause.values()) {
            if (string.equalsIgnoreCase(type.toString())) {
                return type;
            }
        }
        return null;
    }

    /**
     * Get all groups to which the entity type belongs to.
     *
     * @param type type to receive the groups of
     * @return set of groups if the type belongs to one or more groups or empty set
     */
    public Set<String> getGroups(EntityType type) {
        if (entityGroups.containsKey(type)) {
            return entityGroups.get(type);
        }
        return Collections.emptySet();
    }

    /**
     * Get all groups to which the damage cause belongs to.
     *
     * @param cause cause to receive the groups of
     * @return set of groups if the cause belongs to one or more groups or empty set
     */
    public Set<String> getGroups(DamageCause cause) {
        if (damageCauseGroups.containsKey(cause)) {
            return damageCauseGroups.get(cause);
        }
        return Collections.emptySet();
    }

    /**
     * Get all groups to which the material belongs to.
     *
     * @param material material to receive the groups of
     * @return set of groups if the material belongs to one or more groups or empty set
     */
    public Set<String> getGroups(Material material) {
        if (materialGroups.containsKey(material)) {
            return materialGroups.get(material);
        }
        return Collections.emptySet();
    }

    /**
     * Get all groups to which the material belongs to.
     *
     * @param type type to receive the groups of
     * @return set of groups if the potion type belongs to one or more groups or empty set
     */
    public Set<String> getGroups(PotionType type) {
        if (potionGroups.containsKey(type)) {
            return potionGroups.get(type);
        }
        return Collections.emptySet();
    }
}
