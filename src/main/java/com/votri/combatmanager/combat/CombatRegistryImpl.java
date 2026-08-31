package com.votri.combatmanager.combat.registry;

import com.votri.combatmanager.combat.CombatSession;
import com.votri.combatmanager.combat.CombatState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class CombatRegistryImpl
        implements CombatRegistry {

    private final Map<UUID, CombatSession> sessions =
            new ConcurrentHashMap<>();

    @Override
    public void register(CombatSession session) {

        if (session == null) {
            throw new IllegalArgumentException(
                    "session cannot be null"
            );
        }

        sessions.put(session.getId(), session);
    }

    @Override
    public void unregister(UUID sessionId) {

        if (sessionId != null) {
            sessions.remove(sessionId);
        }
    }

    @Override
    public Optional<CombatSession> get(UUID sessionId) {

        if (sessionId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                sessions.get(sessionId)
        );
    }

    @Override
    public Optional<CombatSession> getByParticipant(
            UUID uniqueId
    ) {

        if (uniqueId == null) {
            return Optional.empty();
        }

        return sessions.values()
                .stream()
                .filter(session ->
                        session.getState()
                                == CombatState.ACTIVE)
                .filter(session ->
                        session.contains(uniqueId))
                .findFirst();
    }

    @Override
    public Collection<CombatSession> getAll() {
        return new ArrayList<>(sessions.values());
    }

    @Override
    public boolean contains(UUID uniqueId) {
        return getByParticipant(uniqueId).isPresent();
    }

    @Override
    public void clear() {
        sessions.clear();
    }
}