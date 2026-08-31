package com.votri.combatmanager.damage.api;

public final class DamageResult {

    private final DamageContext context;
    private final DamageCalculationResult calculation;

    private final boolean applied;
    private final boolean lethal;

    public DamageResult(
            DamageContext context,
            DamageCalculationResult calculation,
            boolean applied,
            boolean lethal
    ) {
        this.context = context;
        this.calculation = calculation;
        this.applied = applied;
        this.lethal = lethal;
    }

    public DamageContext getContext() {
        return context;
    }

    public double getOriginalDamage() {
        return calculation.getOriginalDamage();
    }

    public double getFinalDamage() {
        return calculation.getDamage();
    }

    public boolean isCritical() {
        return calculation.isCritical();
    }

    public boolean isExecute() {
        return calculation.isExecute();
    }

    public boolean isApplied() {
        return applied;
    }

    public boolean isLethal() {
        return lethal;
    }

    public String getCriticalId() {
        return calculation.getCriticalId();
    }

    public String getExecuteId() {
        return calculation.getExecuteId();
    }
}