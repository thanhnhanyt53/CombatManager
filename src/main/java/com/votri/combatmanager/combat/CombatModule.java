package com.votri.combatmanager.combat;

import com.votri.combatmanager.combat.listener.CombatListener;
import com.votri.combatmanager.combat.registry.CombatRegistry;
import com.votri.combatmanager.combat.registry.CombatRegistryImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.time.Duration;

public final class CombatModule {

    private final Plugin plugin;

    private final CombatRegistry registry;

    private final CombatServiceImpl combatService;

    private int cleanupTaskId = -1;

    public CombatModule(
            Plugin plugin,
            Duration combatDuration
    ) {

        if (plugin == null) {
            throw new IllegalArgumentException(
                    "plugin cannot be null"
            );
        }

        this.plugin = plugin;

        this.registry =
                new CombatRegistryImpl();

        this.combatService =
                new CombatServiceImpl(
                        registry,
                        combatDuration
                );
    }

    public void enable() {

        Bukkit.getPluginManager()
                .registerEvents(
                        new CombatListener(
                                combatService
                        ),
                        plugin
                );

        cleanupTaskId =
                Bukkit.getScheduler()
                        .scheduleSyncRepeatingTask(
                                plugin,
                                combatService::cleanupExpired,
                                20L,
                                20L
                        );
    }

    public void disable() {

        if (cleanupTaskId != -1) {

            Bukkit.getScheduler()
                    .cancelTask(
                            cleanupTaskId
                    );

            cleanupTaskId = -1;
        }

        combatService.shutdown();
    }

    public CombatService getCombatService() {
        return combatService;
    }

    public CombatRegistry getRegistry() {
        return registry;
    }
}