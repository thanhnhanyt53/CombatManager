package com.votri.combatmanager.combat.event;

import com.votri.combatmanager.combat.CombatSession;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CombatStartEvent
        extends Event
        implements Cancellable {

    private static final HandlerList HANDLER_LIST =
            new HandlerList();

    private final CombatSession session;
    private final LivingEntity attacker;
    private final LivingEntity target;

    private boolean cancelled;

    public CombatStartEvent(
            CombatSession session,
            LivingEntity attacker,
            LivingEntity target
    ) {
        this.session = session;
        this.attacker = attacker;
        this.target = target;
    }

    public CombatSession getSession() {
        return session;
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public LivingEntity getTarget() {
        return target;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}