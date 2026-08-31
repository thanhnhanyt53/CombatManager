package com.votri.combatmanager;

import com.votri.combatmanager.combat.CombatManagerProvider;
import com.votri.combatmanager.combat.CombatModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;

public final class CombatManagerPlugin
        extends JavaPlugin {

    private CombatModule combatModule;

    @Override
    public void onEnable() {

        combatModule =
                new CombatModule(
                        this,
                        Duration.ofSeconds(10)
                );

        combatModule.enable();

        CombatManagerProvider.register(
                combatModule.getCombatService()
        );

        getLogger().info(
                "Combat module enabled."
        );
    }

    @Override
    public void onDisable() {

        CombatManagerProvider.unregister();

        if (combatModule != null) {
            combatModule.disable();
        }
    }

    public CombatModule getCombatModule() {
        return combatModule;
    }
}