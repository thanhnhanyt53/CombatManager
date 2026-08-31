package com.votri.combatmanager.damage.model;

import com.votri.combatmanager.damage.api.DamageCalculationResult;

public final class DamageCalculation {

    private final DamageCalculationResult result;

    public DamageCalculation(DamageCalculationResult result) {
        this.result = result;
    }

    public DamageCalculationResult getResult() {
        return result;
    }
}