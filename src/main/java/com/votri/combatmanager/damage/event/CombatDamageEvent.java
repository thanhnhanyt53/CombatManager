package com.votri.combatmanager.damage.event;

import com.votri.combatmanager.damage.api.DamageContext;
import com.votri.combatmanager.damage.api.DamageResult;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CombatDamageEvent extends Event {

    private static final HandlerList HANDLERS =
            new HandlerList();

    private final DamageContext context;
    private final DamageResult result;

    public CombatDamageEvent(
            DamageContext context,
            DamageResult result
    ) {
        this.context = context;
        this.result = result;
    }

    public DamageContext getContext() {
        return context;
    }

    public DamageResult getResult() {
        return result;
    }

    public boolean isCritical() {
        return result.isCritical();
    }

    public boolean isLethal() {
        return result.isLethal();
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}