package com.votri.combatmanager.combat.event;

import com.votri.combatmanager.combat.CombatEndReason;
import com.votri.combatmanager.combat.CombatSession;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CombatEndEvent
        extends Event
        implements Cancellable {

    private static final HandlerList HANDLER_LIST =
            new HandlerList();

    private final CombatSession session;
    private final CombatEndReason reason;

    private boolean cancelled;

    public CombatEndEvent(
            CombatSession session,
            CombatEndReason reason
    ) {
        this.session = session;
        this.reason = reason;
    }

    public CombatSession getSession() {
        return session;
    }

    public CombatEndReason getReason() {
        return reason;
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