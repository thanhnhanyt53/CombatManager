package com.votri.combatmanager.damage.model;

public final class DamageProfile {

    private final String id;

    private boolean enabled;

    private double damageMultiplier;

    private CriticalProfile criticalProfile;

    public DamageProfile(
            String id,
            boolean enabled,
            double damageMultiplier,
            CriticalProfile criticalProfile
    ) {
        this.id = id;
        this.enabled = enabled;
        this.damageMultiplier = damageMultiplier;
        this.criticalProfile = criticalProfile;
    }

    public String getId() {
        return id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public CriticalProfile getCriticalProfile() {
        return criticalProfile;
    }

    public void setCriticalProfile(CriticalProfile criticalProfile) {
        this.criticalProfile = criticalProfile;
    }
}