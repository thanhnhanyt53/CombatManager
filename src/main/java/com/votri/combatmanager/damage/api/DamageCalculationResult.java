package com.votri.combatmanager.damage.api;

public final class DamageCalculationResult {

    private final double originalDamage;
    private double damage;

    private boolean critical;
    private boolean execute;
    private boolean cancelled;

    private String criticalId;
    private String executeId;

    public DamageCalculationResult(double originalDamage) {
        this.originalDamage = Math.max(0.0D, originalDamage);
        this.damage = this.originalDamage;
    }

    public double getOriginalDamage() {
        return originalDamage;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = Math.max(0.0D, damage);
    }

    public void multiply(double multiplier) {
        if (multiplier < 0.0D) {
            throw new IllegalArgumentException(
                    "Damage multiplier cannot be negative."
            );
        }

        this.damage *= multiplier;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getCriticalId() {
        return criticalId;
    }

    public void setCriticalId(String criticalId) {
        this.criticalId = criticalId;
    }

    public String getExecuteId() {
        return executeId;
    }

    public void setExecuteId(String executeId) {
        this.executeId = executeId;
    }
}