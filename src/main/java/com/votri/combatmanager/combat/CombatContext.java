package com.votri.combatmanager.combat;

import org.bukkit.entity.LivingEntity;

import java.util.Objects;

public final class CombatContext {

    private final LivingEntity attacker;
    private final LivingEntity target;

    private final long timestamp;

    public CombatContext(
            LivingEntity attacker,
            LivingEntity target
    ) {
        this.attacker = Objects.requireNonNull(attacker, "attacker");
        this.target = Objects.requireNonNull(target, "target");
        this.timestamp = System.currentTimeMillis();
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public long getTimestamp() {
        return timestamp;
    }
}