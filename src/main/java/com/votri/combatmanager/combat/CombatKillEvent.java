package com.votri.combatmanager.combat.event;

import com.votri.combatmanager.combat.CombatSession;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CombatKillEvent
        extends Event {

    private static final HandlerList HANDLER_LIST =
            new HandlerList();

    private final CombatSession session;
    private final LivingEntity attacker;
    private final LivingEntity victim;

    public CombatKillEvent(
            CombatSession session,
            LivingEntity attacker,
            LivingEntity victim
    ) {
        this.session = session;
        this.attacker = attacker;
        this.victim = victim;
    }

    public CombatSession getSession() {
        return session;
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public LivingEntity getVictim() {
        return victim;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}