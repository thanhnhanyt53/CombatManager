package com.votri.combatmanager.damage.model;

public final class CriticalProfile {

    private final String id;

    private final double healthThresholdPercent;
    private final double chancePercent;
    private final double multiplier;

    public CriticalProfile(
            String id,
            double healthThresholdPercent,
            double chancePercent,
            double multiplier
    ) {
        if (healthThresholdPercent < 0.0D
                || healthThresholdPercent > 100.0D) {
            throw new IllegalArgumentException(
                    "Health threshold must be between 0 and 100."
            );
        }

        if (chancePercent < 0.0D
                || chancePercent > 100.0D) {
            throw new IllegalArgumentException(
                    "Critical chance must be between 0 and 100."
            );
        }

        if (multiplier < 0.0D) {
            throw new IllegalArgumentException(
                    "Critical multiplier cannot be negative."
            );
        }

        this.id = id;
        this.healthThresholdPercent = healthThresholdPercent;
        this.chancePercent = chancePercent;
        this.multiplier = multiplier;
    }

    public String getId() {
        return id;
    }

    public double getHealthThresholdPercent() {
        return healthThresholdPercent;
    }

    public double getChancePercent() {
        return chancePercent;
    }

    public double getMultiplier() {
        return multiplier;
    }
}