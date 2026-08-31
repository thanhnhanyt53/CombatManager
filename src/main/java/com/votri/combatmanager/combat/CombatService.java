package com.votri.combatmanager.combat;

import org.bukkit.entity.LivingEntity;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CombatService {

    CombatSession startCombat(
            LivingEntity attacker,
            LivingEntity target
    );

    void recordDamage(
            LivingEntity attacker,
            LivingEntity target
    );

    void endCombat(
            UUID uniqueId,
            CombatEndReason reason
    );

    void endCombat(
            CombatSession session,
            CombatEndReason reason
    );

    Optional<CombatSession> getCombat(
            UUID uniqueId
    );

    boolean isInCombat(
            UUID uniqueId
    );

    boolean isInCombat(
            LivingEntity entity
    );

    Duration getRemainingTime(
            UUID uniqueId
    );

    Collection<CombatSession> getSessions();

    void cleanupExpired();

    void shutdown();
}