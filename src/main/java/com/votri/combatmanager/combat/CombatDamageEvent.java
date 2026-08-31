package com.votri.combatmanager.combat.event;

import com.votri.combatmanager.combat.CombatContext;
import com.votri.combatmanager.combat.CombatSession;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CombatDamageEvent
        extends Event
        implements Cancellable {

    private static final HandlerList HANDLER_LIST =
            new HandlerList();

    private final CombatContext context;
    private final CombatSession session;

    private double damage;

    private boolean cancelled;

    public CombatDamageEvent(
            CombatContext context,
            CombatSession session,
            double damage
    ) {
        this.context = context;
        this.session = session;
        this.damage = damage;
    }

    public CombatContext getContext() {
        return context;
    }

    public CombatSession getSession() {
        return session;
    }

    public LivingEntity getAttacker() {
        return context.getAttacker();
    }

    public LivingEntity getTarget() {
        return context.getTarget();
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {

        if (damage < 0.0D) {
            throw new IllegalArgumentException(
                    "damage cannot be negative"
            );
        }

        this.damage = damage;
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