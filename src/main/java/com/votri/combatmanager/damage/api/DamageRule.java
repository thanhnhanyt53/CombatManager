package com.votri.combatmanager.damage.api;

public interface DamageRule {

    String getId();

    int getPriority();

    boolean applies(DamageContext context);

    DamageCalculationResult apply(
            DamageContext context,
            DamageCalculationResult current
    );
}