package com.votri.combatmanager.damage.api;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public final class DamageContext {

    private final Entity attacker;
    private final Entity target;

    private final double originalDamage;

    private final EntityDamageEvent.DamageCause cause;

    private final DamageSourceType sourceType;

    private final boolean playerAttack;
    private final boolean projectileAttack;

    private final EntityDamageEvent originalEvent;

    private DamageContext(
            Entity attacker,
            Entity target,
            double originalDamage,
            EntityDamageEvent.DamageCause cause,
            DamageSourceType sourceType,
            boolean playerAttack,
            boolean projectileAttack,
            EntityDamageEvent originalEvent
    ) {
        this.attacker = attacker;
        this.target = Objects.requireNonNull(target);
        this.originalDamage = originalDamage;
        this.cause = Objects.requireNonNull(cause);
        this.sourceType = Objects.requireNonNull(sourceType);
        this.playerAttack = playerAttack;
        this.projectileAttack = projectileAttack;
        this.originalEvent = originalEvent;
    }

    public static Builder builder(Entity target) {
        return new Builder(target);
    }

    public Entity getAttacker() {
        return attacker;
    }

    public Entity getTarget() {
        return target;
    }

    public double getOriginalDamage() {
        return originalDamage;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }

    public DamageSourceType getSourceType() {
        return sourceType;
    }

    public boolean isPlayerAttack() {
        return playerAttack;
    }

    public boolean isProjectileAttack() {
        return projectileAttack;
    }

    public EntityDamageEvent getOriginalEvent() {
        return originalEvent;
    }

    public static final class Builder {

        private final Entity target;

        private Entity attacker;
        private double originalDamage;
        private EntityDamageEvent.DamageCause cause =
                EntityDamageEvent.DamageCause.CUSTOM;
        private DamageSourceType sourceType =
                DamageSourceType.UNKNOWN;

        private boolean playerAttack;
        private boolean projectileAttack;

        private EntityDamageEvent originalEvent;

        private Builder(Entity target) {
            this.target = Objects.requireNonNull(target);
        }

        public Builder attacker(Entity attacker) {
            this.attacker = attacker;
            return this;
        }

        public Builder originalDamage(double damage) {
            this.originalDamage = Math.max(0.0D, damage);
            return this;
        }

        public Builder cause(EntityDamageEvent.DamageCause cause) {
            this.cause = Objects.requireNonNull(cause);
            return this;
        }

        public Builder sourceType(DamageSourceType sourceType) {
            this.sourceType = Objects.requireNonNull(sourceType);
            return this;
        }

        public Builder playerAttack(boolean value) {
            this.playerAttack = value;
            return this;
        }

        public Builder projectileAttack(boolean value) {
            this.projectileAttack = value;
            return this;
        }

        public Builder originalEvent(EntityDamageEvent event) {
            this.originalEvent = event;
            return this;
        }

        public DamageContext build() {
            return new DamageContext(
                    attacker,
                    target,
                    originalDamage,
                    cause,
                    sourceType,
                    playerAttack,
                    projectileAttack,
                    originalEvent
            );
        }
    }
}