package com.votri.combatmanager.damage.rule;

import com.votri.combatmanager.damage.api.DamageContext;
import com.votri.combatmanager.damage.api.DamageRule;
import com.votri.combatmanager.damage.api.DamageCalculationResult;

public final class ExecuteDamageRule implements DamageRule {

    @Override
    public String getId() {
        return "execute";
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    public boolean applies(DamageContext context) {
        return context.getTarget()
                instanceof org.bukkit.entity.LivingEntity;
    }

    @Override
    public DamageCalculationResult apply(
            DamageContext context,
            DamageCalculationResult current
    ) {
        if (!current.isExecute()) {
            return current;
        }

        var target =
                (org.bukkit.entity.LivingEntity)
                        context.getTarget();

        current.setDamage(target.getHealth());

        return current;
    }
}