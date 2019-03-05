package de.eldoria.eldoworldcontrol;

import de.eldoria.eldoworldcontrol.command.AboutCommand;
import de.eldoria.eldoworldcontrol.listener.containercontrol.ContainerListener;
import de.eldoria.eldoworldcontrol.listener.damagecontrol.DamageDealByEntityListener;
import de.eldoria.eldoworldcontrol.listener.damagecontrol.DamageTakeByEntityListener;
import de.eldoria.eldoworldcontrol.listener.damagecontrol.DamageTakeListener;
import de.eldoria.eldoworldcontrol.listener.entitybehaviour.RideListener;
import de.eldoria.eldoworldcontrol.listener.entitybehaviour.TameListener;
import de.eldoria.eldoworldcontrol.listener.entitybehaviour.TargetListener;
import de.eldoria.eldoworldcontrol.listener.playerbehaviour.LoginListener;
import de.eldoria.eldoworldcontrol.listener.actioncontrol.*;
import de.eldoria.eldoworldcontrol.listener.buildcontrol.*;
import de.eldoria.eldoworldcontrol.listener.playerbehaviour.*;
import de.eldoria.eldoworldcontrol.listener.playermovement.GlideListener;
import de.eldoria.eldoworldcontrol.listener.playermovement.SneakListener;
import de.eldoria.eldoworldcontrol.listener.playermovement.SprintListener;
import de.eldoria.eldoworldcontrol.listener.playermovement.SwimListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EldoWorldControl extends JavaPlugin {
    private static FileConfiguration config;

    private static EldoWorldControl instance;

    PluginManager pm;

    public static FileConfiguration getConfigFile() {
        return config;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        pm = Bukkit.getPluginManager();

        if (instance == null) {
            instance = this;
        }

        config = this.getConfig();

        loadModules();

        this.getCommand("eldoworldcontrol").setExecutor(new AboutCommand());
    }

    public static EldoWorldControl getInstance() {
        if (instance == null) {
            instance = new EldoWorldControl();
        }
        return instance;
    }

    public void reload() {
        this.reloadConfig();
        HandlerList.unregisterAll(this);
        loadModules();

    }

    private void registerEvent(Listener l) {
        pm.registerEvents(l, this);
    }


    private void loadModules() {
        if (config.getBoolean("Modules.ActionControl")) {
            if (config.getBoolean("Modules.ActionControl.BedListener")) {
                registerEvent(new BedListener());
            }
            if (config.getBoolean("Modules.ActionControl.BucketListener")) {
                registerEvent(new BucketListener());
            }
            if (config.getBoolean("Modules.ActionControl.CraftListener")) {
                registerEvent(new CraftListener());
            }
            if (config.getBoolean("Modules.ActionControl.DropListener")) {
                registerEvent(new DropListener());
            }
            if (config.getBoolean("Modules.ActionControl.EnchantListener")) {
                registerEvent(new EnchantListener());
            }
            if (config.getBoolean("Modules.ActionControl.InteractListener")) {
                registerEvent(new InteractListener());
            }
            if (config.getBoolean("Modules.ActionControl.PickupListener")) {
                registerEvent(new PickupListener());
            }
            if (config.getBoolean("Modules.ActionControl.ThrowListener")) {
                registerEvent(new ThrowListener());
            }
            if (config.getBoolean("Modules.ActionControl.UseListener")) {
                registerEvent(new UseListener());
            }
        }

        if (config.getBoolean("Modules.BuildControl")) {
            if (config.getBoolean("Modules.BuildControl.BreakListener")) {
                registerEvent(new BreakListener());
            }
            if (config.getBoolean("Modules.BuildControl.PlaceListener")) {
                registerEvent(new PlaceListener());
            }
        }

        if (config.getBoolean("Modules.ContainerControl")) {
            if (config.getBoolean("Modules.ContainerControl.ContainerListener")) {
                registerEvent(new ContainerListener());
            }
        }

        if (config.getBoolean("Modules.DamageControl")) {
            if (config.getBoolean("Modules.DamageControl.DamageDealByEntityListener")) {
                registerEvent(new DamageDealByEntityListener());
            }
            if (config.getBoolean("Modules.DamageControl.DamageTakeByEntityListener")) {
                registerEvent(new DamageTakeByEntityListener());
            }
            if (config.getBoolean("Modules.DamageControl.DamageTakeListener")) {
                registerEvent(new DamageTakeListener());
            }
        }

        if (config.getBoolean("Modules.EntityBehaviour")) {
            if (config.getBoolean("Modules.EntityBehaviour.InteractListener")) {
                registerEvent(new InteractListener());
            }
            if (config.getBoolean("Modules.EntityBehaviour.RideListener")) {
                registerEvent(new RideListener());
            }

            if (config.getBoolean("Modules.EntityBehaviour.TameListener")) {
                registerEvent(new TameListener());
            }

            if (config.getBoolean("Modules.EntityBehaviour.TargetListener")) {
                registerEvent(new TargetListener());
            }
        }

        if (config.getBoolean("Modules.PlayerBehaviour")) {
            if (config.getBoolean("Modules.PlayerBehaviour.HungerListener")) {
                registerEvent(new HungerListener());
            }
            if (config.getBoolean("Modules.PlayerBehaviour.LoginListener")) {
                if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
                    registerEvent(new LoginListener());
                }
            }
        }

        if (config.getBoolean("Modules.PlayerMovement")) {
            if (config.getBoolean("Modules.PlayerMovement.GlideListener")) {
                registerEvent(new GlideListener());
            }
            if (config.getBoolean("Modules.PlayerMovement.SneakListener")) {
                registerEvent(new SneakListener());
            }
            if (config.getBoolean("Modules.PlayerMovement.SprintListener")) {
                registerEvent(new SprintListener());
            }
            if (config.getBoolean("Modules.PlayerMovement.SwimListener")) {
                registerEvent(new SwimListener());
            }
        }
    }
}
