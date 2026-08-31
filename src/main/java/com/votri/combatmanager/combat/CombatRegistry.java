package com.votri.combatmanager.combat.registry;

import com.votri.combatmanager.combat.CombatSession;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CombatRegistry {

    void register(CombatSession session);

    void unregister(UUID sessionId);

    Optional<CombatSession> get(UUID sessionId);

    Optional<CombatSession> getByParticipant(UUID uniqueId);

    Collection<CombatSession> getAll();

    boolean contains(UUID uniqueId);

    void clear();
}