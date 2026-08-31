package com.votri.combatmanager.combat;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface CombatSession {

    UUID getId();

    CombatState getState();

    Set<UUID> getParticipants();

    boolean contains(UUID uniqueId);

    UUID getPrimaryAttacker();

    UUID getLastAttacker();

    Instant getStartedAt();

    Instant getLastDamageAt();

    Duration getDuration();

    Duration getRemainingTime();

    boolean isExpired();

    void refresh();

    void end(CombatEndReason reason);

    CombatEndReason getEndReason();
}