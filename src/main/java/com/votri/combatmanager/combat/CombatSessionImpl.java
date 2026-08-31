package com.votri.combatmanager.combat;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

final class CombatSessionImpl implements CombatSession {

    private final UUID id;

    private final Set<UUID> participants =
            new LinkedHashSet<>();

    private final long durationMillis;

    private final Instant startedAt;

    private Instant lastDamageAt;

    private UUID primaryAttacker;

    private UUID lastAttacker;

    private CombatState state = CombatState.ACTIVE;

    private CombatEndReason endReason;

    CombatSessionImpl(
            UUID id,
            UUID attacker,
            UUID target,
            long durationMillis
    ) {
        this.id = id;
        this.durationMillis = durationMillis;

        this.startedAt = Instant.now();
        this.lastDamageAt = this.startedAt;

        this.primaryAttacker = attacker;
        this.lastAttacker = attacker;

        participants.add(attacker);
        participants.add(target);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public CombatState getState() {
        return state;
    }

    @Override
    public Set<UUID> getParticipants() {
        return Collections.unmodifiableSet(participants);
    }

    @Override
    public boolean contains(UUID uniqueId) {
        return participants.contains(uniqueId);
    }

    @Override
    public UUID getPrimaryAttacker() {
        return primaryAttacker;
    }

    @Override
    public UUID getLastAttacker() {
        return lastAttacker;
    }

    @Override
    public Instant getStartedAt() {
        return startedAt;
    }

    @Override
    public Instant getLastDamageAt() {
        return lastDamageAt;
    }

    @Override
    public Duration getDuration() {
        return Duration.between(
                startedAt,
                Instant.now()
        );
    }

    @Override
    public Duration getRemainingTime() {

        if (state != CombatState.ACTIVE) {
            return Duration.ZERO;
        }

        long elapsed =
                System.currentTimeMillis()
                        - lastDamageAt.toEpochMilli();

        long remaining =
                durationMillis - elapsed;

        return Duration.ofMillis(
                Math.max(0L, remaining)
        );
    }

    @Override
    public boolean isExpired() {
        return getRemainingTime().isZero();
    }

    @Override
    public void refresh() {
        if (state == CombatState.ACTIVE) {
            lastDamageAt = Instant.now();
        }
    }

    void registerAttacker(UUID attacker) {

        if (attacker == null) {
            return;
        }

        participants.add(attacker);
        lastAttacker = attacker;

        if (primaryAttacker == null) {
            primaryAttacker = attacker;
        }
    }

    void registerParticipant(UUID participant) {

        if (participant != null) {
            participants.add(participant);
        }
    }

    @Override
    public void end(CombatEndReason reason) {

        if (state == CombatState.ENDED) {
            return;
        }

        state = CombatState.ENDING;
        endReason = reason;

        state = CombatState.ENDED;
    }

    @Override
    public CombatEndReason getEndReason() {
        return endReason;
    }
}