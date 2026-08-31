package com.votri.combatmanager.combat;

import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public interface CombatParticipant {

    UUID getUniqueId();

    LivingEntity getEntity();

    CombatParticipantType getType();

    boolean isOnline();

    boolean isAlive();
}