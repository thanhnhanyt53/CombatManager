package com.votri.combatmanager.combat;

import com.votri.combatmanager.combat.event.CombatEndEvent;
import com.votri.combatmanager.combat.event.CombatStartEvent;
import com.votri.combatmanager.combat.registry.CombatRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public final class CombatServiceImpl
        implements CombatService {

    private final CombatRegistry registry;

    private final long combatDurationMillis;

    public CombatServiceImpl(
            CombatRegistry registry,
            Duration combatDuration
    ) {

        if (registry == null) {
            throw new IllegalArgumentException(
                    "registry cannot be null"
            );
        }

        if (combatDuration == null
                || combatDuration.isNegative()
                || combatDuration.isZero()) {

            throw new IllegalArgumentException(
                    "combatDuration must be positive"
            );
        }

        this.registry = registry;

        this.combatDurationMillis =
                combatDuration.toMillis();
    }

    @Override
    public CombatSession startCombat(
            LivingEntity attacker,
            LivingEntity target
    ) {

        if (attacker == null || target == null) {
            throw new IllegalArgumentException(
                    "attacker and target cannot be null"
            );
        }

        if (attacker.equals(target)) {
            throw new IllegalArgumentException(
                    "attacker and target cannot be the same entity"
            );
        }

        Optional<CombatSession> existing =
                registry.getByParticipant(
                        target.getUniqueId()
                );

        if (existing.isPresent()) {

            CombatSession session = existing.get();

            if (session instanceof CombatSessionImpl impl) {

                impl.registerAttacker(
                        attacker.getUniqueId()
                );

                impl.registerParticipant(
                        target.getUniqueId()
                );

                impl.refresh();
            }

            return session;
        }

        CombatSessionImpl session =
                new CombatSessionImpl(
                        UUID.randomUUID(),
                        attacker.getUniqueId(),
                        target.getUniqueId(),
                        combatDurationMillis
                );

        CombatStartEvent event =
                new CombatStartEvent(
                        session,
                        attacker,
                        target
                );

        Bukkit.getPluginManager()
                .callEvent(event);

        if (event.isCancelled()) {
            return session;
        }

        registry.register(session);

        return session;
    }

    @Override
    public void recordDamage(
            LivingEntity attacker,
            LivingEntity target
    ) {

        if (attacker == null || target == null) {
            return;
        }

        Optional<CombatSession> existing =
                registry.getByParticipant(
                        target.getUniqueId()
                );

        if (existing.isPresent()) {

            CombatSession session = existing.get();

            if (session instanceof CombatSessionImpl impl) {

                impl.registerAttacker(
                        attacker.getUniqueId()
                );

                impl.refresh();
            }

            return;
        }

        startCombat(attacker, target);
    }

    @Override
    public void endCombat(
            UUID uniqueId,
            CombatEndReason reason
    ) {

        if (uniqueId == null) {
            return;
        }

        registry.getByParticipant(uniqueId)
                .ifPresent(session ->
                        endCombat(session, reason)
                );
    }

    @Override
    public void endCombat(
            CombatSession session,
            CombatEndReason reason
    ) {

        if (session == null) {
            return;
        }

        CombatEndEvent event =
                new CombatEndEvent(
                        session,
                        reason
                );

        Bukkit.getPluginManager()
                .callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        session.end(reason);

        registry.unregister(
                session.getId()
        );
    }

    @Override
    public Optional<CombatSession> getCombat(
            UUID uniqueId
    ) {

        return registry.getByParticipant(uniqueId);
    }

    @Override
    public boolean isInCombat(UUID uniqueId) {

        return registry.contains(uniqueId);
    }

    @Override
    public boolean isInCombat(
            LivingEntity entity
    ) {

        return entity != null
                && isInCombat(entity.getUniqueId());
    }

    @Override
    public Duration getRemainingTime(
            UUID uniqueId
    ) {

        return getCombat(uniqueId)
                .map(CombatSession::getRemainingTime)
                .orElse(Duration.ZERO);
    }

    @Override
    public Collection<CombatSession> getSessions() {
        return registry.getAll();
    }

    @Override
    public void cleanupExpired() {

        for (CombatSession session :
                registry.getAll()) {

            if (session.isExpired()) {

                endCombat(
                        session,
                        CombatEndReason.TIMEOUT
                );
            }
        }
    }

    @Override
    public void shutdown() {

        for (CombatSession session :
                registry.getAll()) {

            session.end(
                    CombatEndReason.SERVER_SHUTDOWN
            );
        }

        registry.clear();
    }
}