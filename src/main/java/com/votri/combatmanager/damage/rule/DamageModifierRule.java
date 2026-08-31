package com.votri.combatmanager.damage.rule;

import com.votri.combatmanager.damage.api.DamageContext;
import com.votri.combatmanager.damage.api.DamageModifier;
import com.votri.combatmanager.damage.api.DamageRule;
import com.votri.combatmanager.damage.api.DamageCalculationResult;

import java.util.Objects;

public final class DamageModifierRule
        implements DamageRule {

    private final String id;
    private final int priority;
    private final DamageModifier modifier;

    public DamageModifierRule(
            String id,
            int priority,
            DamageModifier modifier
    ) {
        this.id = Objects.requireNonNull(id);
        this.priority = priority;
        this.modifier = Objects.requireNonNull(modifier);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean applies(DamageContext context) {
        return true;
    }

    @Override
    public DamageCalculationResult apply(
            DamageContext context,
            DamageCalculationResult current
    ) {
        current.setDamage(
                modifier.modify(
                        context,
                        current.getDamage()
                )
        );

        return current;
    }
}