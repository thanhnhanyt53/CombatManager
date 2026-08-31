package com.votri.combatmanager.combat;

public final class CombatManagerProvider {

    private static volatile CombatService service;

    private CombatManagerProvider() {
    }

    public static void register(
            CombatService combatService
    ) {

        if (combatService == null) {
            throw new IllegalArgumentException(
                    "combatService cannot be null"
            );
        }

        if (service != null) {
            throw new IllegalStateException(
                    "CombatService is already registered"
            );
        }

        service = combatService;
    }

    public static CombatService get() {

        CombatService current = service;

        if (current == null) {
            throw new IllegalStateException(
                    "CombatManager has not been initialized"
            );
        }

        return current;
    }

    public static boolean isAvailable() {
        return service != null;
    }

    public static void unregister() {
        service = null;
    }
}