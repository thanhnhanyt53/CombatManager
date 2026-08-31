package com.votri.combatmanager.damage.service;

import com.votri.combatmanager.damage.api.DamageCalculationResult;
import com.votri.combatmanager.damage.api.DamageContext;
import com.votri.combatmanager.damage.api.DamageRule;

public final class DamageCalculationService {

    private final DamageRuleRegistry registry;

    public DamageCalculationService(
            DamageRuleRegistry registry
    ) {
        this.registry = registry;
    }

    public DamageCalculationResult calculate(
            DamageContext context
    ) {
        DamageCalculationResult result =
                new DamageCalculationResult(
                        context.getOriginalDamage()
                );

        for (DamageRule rule : registry.getRules()) {

            if (!rule.applies(context)) {
                continue;
            }

            result = rule.apply(context, result);

            if (result.isCancelled()) {
                break;
            }
        }

        return result;
    }
}