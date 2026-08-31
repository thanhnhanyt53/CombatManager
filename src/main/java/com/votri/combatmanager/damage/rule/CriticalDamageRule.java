package com.votri.combatmanager.damage.rule;

import com.votri.combatmanager.damage.api.DamageContext;
import com.votri.combatmanager.damage.api.DamageRule;
import com.votri.combatmanager.damage.api.DamageCalculationResult;
import com.votri.combatmanager.damage.model.CriticalProfile;

import org.bukkit.entity.LivingEntity;

import java.util.concurrent.ThreadLocalRandom;

public final class CriticalDamageRule implements DamageRule {

    private final CriticalProfile playerProfile;
    private final CriticalProfile mobProfile;

    public CriticalDamageRule(
            CriticalProfile playerProfile,
            CriticalProfile mobProfile
    ) {
        this.playerProfile = playerProfile;
        this.mobProfile = mobProfile;
    }

    @Override
    public String getId() {
        return "critical";
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public boolean applies(DamageContext context) {

        if (!(context.getTarget() instanceof LivingEntity)) {
            return false;
        }

        return playerProfile != null
                || mobProfile != null;
    }

    @Override
    public DamageCalculationResult apply(
            DamageContext context,
            DamageCalculationResult current
    ) {
        if (!(context.getTarget() instanceof LivingEntity target)) {
            return current;
        }

        double healthPercent =
                (target.getHealth()
                        / target.getMaxHealth()) * 100.0D;

        CriticalProfile profile;

        if (target instanceof org.bukkit.entity.Player) {
            profile = playerProfile;
        } else {
            profile = mobProfile;
        }

        if (profile == null) {
            return current;
        }

        if (healthPercent >
                profile.getHealthThresholdPercent()) {
            return current;
        }

        double roll =
                ThreadLocalRandom.current().nextDouble(0.0D, 100.0D);

        if (roll >= profile.getChancePercent()) {
            return current;
        }

        current.setCritical(true);
        current.setCriticalId(profile.getId());

        if (profile.getMultiplier() == 0.0D) {
            current.setExecute(true);
            current.setDamage(target.getHealth());
        } else {
            current.multiply(profile.getMultiplier());
        }

        return current;
    }
}