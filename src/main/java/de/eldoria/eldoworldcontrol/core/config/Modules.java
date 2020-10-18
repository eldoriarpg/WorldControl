package de.eldoria.eldoworldcontrol.core.config;

import de.eldoria.eldoutilities.serialization.SerializationUtil;
import de.eldoria.eldoutilities.serialization.TypeResolvingMap;
import de.eldoria.eldoworldcontrol.controllistener.BedListener;
import de.eldoria.eldoworldcontrol.controllistener.BreakListener;
import de.eldoria.eldoworldcontrol.controllistener.BreedListener;
import de.eldoria.eldoworldcontrol.controllistener.BucketListener;
import de.eldoria.eldoworldcontrol.controllistener.ConsumeListener;
import de.eldoria.eldoworldcontrol.controllistener.CraftListener;
import de.eldoria.eldoworldcontrol.controllistener.DamageDealByEntityListener;
import de.eldoria.eldoworldcontrol.controllistener.DamageTakeByEntityListener;
import de.eldoria.eldoworldcontrol.controllistener.DamageTakeListener;
import de.eldoria.eldoworldcontrol.controllistener.DropListener;
import de.eldoria.eldoworldcontrol.controllistener.EnchantListener;
import de.eldoria.eldoworldcontrol.controllistener.EntityInteractListener;
import de.eldoria.eldoworldcontrol.controllistener.EntityLootListener;
import de.eldoria.eldoworldcontrol.controllistener.FishListener;
import de.eldoria.eldoworldcontrol.controllistener.HungerListener;
import de.eldoria.eldoworldcontrol.controllistener.LoginListener;
import de.eldoria.eldoworldcontrol.controllistener.MaterialInteractListener;
import de.eldoria.eldoworldcontrol.controllistener.PickupListener;
import de.eldoria.eldoworldcontrol.controllistener.PlaceListener;
import de.eldoria.eldoworldcontrol.controllistener.RideListener;
import de.eldoria.eldoworldcontrol.controllistener.ShearListener;
import de.eldoria.eldoworldcontrol.controllistener.SmeltListener;
import de.eldoria.eldoworldcontrol.controllistener.TameListener;
import de.eldoria.eldoworldcontrol.controllistener.TargetListener;
import de.eldoria.eldoworldcontrol.controllistener.ThrowListener;
import de.eldoria.eldoworldcontrol.controllistener.UseListener;
import de.eldoria.eldoworldcontrol.controllistener.util.BaseControlListener;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SerializableAs("ewcModules")
public class Modules implements ConfigurationSerializable {
    private static final List<Class<? extends BaseControlListener>> LISTENER = new ArrayList<>();

    static {
        LISTENER.add(BedListener.class);
        LISTENER.add(BreakListener.class);
        LISTENER.add(BreedListener.class);
        LISTENER.add(BucketListener.class);
        LISTENER.add(ConsumeListener.class);
        LISTENER.add(CraftListener.class);
        LISTENER.add(DamageDealByEntityListener.class);
        LISTENER.add(DamageTakeByEntityListener.class);
        LISTENER.add(DamageTakeListener.class);
        LISTENER.add(DropListener.class);
        LISTENER.add(EnchantListener.class);
        LISTENER.add(EntityInteractListener.class);
        LISTENER.add(EntityLootListener.class);
        LISTENER.add(FishListener.class);
        LISTENER.add(HungerListener.class);
        LISTENER.add(LoginListener.class);
        LISTENER.add(MaterialInteractListener.class);
        LISTENER.add(PickupListener.class);
        LISTENER.add(PlaceListener.class);
        LISTENER.add(RideListener.class);
        LISTENER.add(ShearListener.class);
        LISTENER.add(SmeltListener.class);
        LISTENER.add(TameListener.class);
        LISTENER.add(TargetListener.class);
        LISTENER.add(ThrowListener.class);
        LISTENER.add(UseListener.class);
    }

    private final Map<Class<? extends BaseControlListener>, ModuleSetting> settings = new HashMap<>();

    public Modules() {
        LISTENER.forEach(l -> settings.put(l, new ModuleSetting(l)));
    }

    public Modules(Map<String, Object> objectMap) {
        TypeResolvingMap map = SerializationUtil.mapOf(objectMap);
        List<ModuleSetting> settings = map.getValueOrDefault("moduleSettings", new ArrayList<>());
        for (Class<? extends BaseControlListener> clazz : LISTENER) {
            Optional<ModuleSetting> first = settings.stream()
                    .filter(s -> s.getClazz().equalsIgnoreCase(clazz.getSimpleName()))
                    .findFirst();
            this.settings.put(clazz, first.orElse(new ModuleSetting(clazz)));
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return SerializationUtil.newBuilder()
                .add("moduleSettings", new ArrayList<>(settings.values()))
                .build();
    }

    public ModuleSetting getSettings(Class<? extends BaseControlListener> clazz) {
        return settings.get(clazz);
    }

    public Map<Class<? extends BaseControlListener>, ModuleSetting> getModuleSettings() {
        return Collections.unmodifiableMap(settings);
    }
}
