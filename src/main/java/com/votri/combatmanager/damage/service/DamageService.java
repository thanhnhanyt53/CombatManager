package com.votri.combatmanager.damage.service;

import com.votri.combatmanager.damage.api.DamageContext;
import com.votri.combatmanager.damage.api.DamageResult;

import org.bukkit.entity.LivingEntity;

public final class DamageService {

    private final DamageCalculationService calculationService;

    public DamageService(
            DamageCalculationService calculationService
    ) {
        this.calculationService = calculationService;
    }

    public DamageResult calculate(
            DamageContext context
    ) {
        if (!(context.getTarget() instanceof LivingEntity target)) {
            return new DamageResult(
                    context,
                    new com.votri.combatmanager.damage.api
                            .DamageCalculationResult(
                                    context.getOriginalDamage()
                            ),
                    false,
                    false
            );
        }

        var calculation =
                calculationService.calculate(context);

        if (calculation.isCancelled()) {
            return new DamageResult(
                    context,
                    calculation,
                    false,
                    false
            );
        }

        double damage = calculation.getDamage();

        boolean lethal =
                calculation.isExecute()
                        || damage >= target.getHealth();

        return new DamageResult(
                context,
                calculation,
                true,
                lethal
        );
    }
}