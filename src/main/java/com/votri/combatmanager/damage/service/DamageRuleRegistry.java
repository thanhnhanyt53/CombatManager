package com.votri.combatmanager.damage.service;

import com.votri.combatmanager.damage.api.DamageRule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class DamageRuleRegistry {

    private final List<DamageRule> rules = new ArrayList<>();

    public synchronized void register(DamageRule rule) {
        Objects.requireNonNull(rule);

        rules.removeIf(existing ->
                existing.getId().equalsIgnoreCase(rule.getId())
        );

        rules.add(rule);

        rules.sort(
                Comparator.comparingInt(DamageRule::getPriority)
        );
    }

    public synchronized void unregister(String id) {
        rules.removeIf(rule ->
                rule.getId().equalsIgnoreCase(id)
        );
    }

    public synchronized List<DamageRule> getRules() {
        return List.copyOf(rules);
    }

    public synchronized void clear() {
        rules.clear();
    }
}