package com.votri.combatmanager.damage.api;

@FunctionalInterface
public interface DamageModifier {

    double modify(
            DamageContext context,
            double currentDamage
    );
}