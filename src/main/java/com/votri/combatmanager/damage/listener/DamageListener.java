package com.votri.combatmanager.damage.listener;

import com.votri.combatmanager.damage.api.DamageContext;
import com.votri.combatmanager.damage.api.DamageResult;
import com.votri.combatmanager.damage.api.DamageSourceType;
import com.votri.combatmanager.damage.event.CombatDamageEvent;
import com.votri.combatmanager.damage.event.CriticalDamageEvent;
import com.votri.combatmanager.damage.event.LethalDamageEvent;
import com.votri.combatmanager.damage.service.DamageService;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;

public final class DamageListener implements Listener {

    private final DamageService damageService;

    public DamageListener(
            DamageService damageService
    ) {
        this.damageService = damageService;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onDamage(EntityDamageEvent event) {

        Entity target = event.getEntity();

        Entity attacker = null;

        boolean projectile = false;

        DamageSourceType sourceType =
                resolveSourceType(event);

        if (event instanceof EntityDamageByEntityEvent byEntity) {

            attacker = byEntity.getDamager();

            if (attacker instanceof org.bukkit.entity.Projectile) {

                projectile = true;

                ProjectileSource source =
                        ((org.bukkit.entity.Projectile)
                                attacker).getShooter();

                if (source instanceof Entity entity) {
                    attacker = entity;
                }
            }
        }

        DamageContext context =
                DamageContext.builder(target)
                        .attacker(attacker)
                        .originalDamage(event.getDamage())
                        .cause(event.getCause())
                        .sourceType(sourceType)
                        .playerAttack(attacker instanceof Player)
                        .projectileAttack(projectile)
                        .originalEvent(event)
                        .build();

        DamageResult result =
                damageService.calculate(context);

        if (!result.isApplied()) {
            return;
        }

        if (result.isCritical()) {

            CombatDamageEvent damageEvent =
                    new CombatDamageEvent(
                            context,
                            result
                    );

            event.getHandlers();

            org.bukkit.Bukkit
                    .getPluginManager()
                    .callEvent(damageEvent);

            org.bukkit.Bukkit
                    .getPluginManager()
                    .callEvent(
                            new CriticalDamageEvent(
                                    context,
                                    result
                            )
                    );
        }

        /*
         * IMPORTANT:
         *
         * The engine only calculates damage.
         * We now replace the event's raw damage.
         */
        event.setDamage(result.getFinalDamage());

        if (result.isLethal()) {

            org.bukkit.Bukkit
                    .getPluginManager()
                    .callEvent(
                            new LethalDamageEvent(
                                    context,
                                    result
                            )
                    );
        }
    }

    private DamageSourceType resolveSourceType(
            EntityDamageEvent event
    ) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent byEntity =
                    (EntityDamageByEntityEvent) event;

            Entity damager = byEntity.getDamager();

            if (damager instanceof Player) {
                return DamageSourceType.PLAYER;
            }

            if (damager instanceof org.bukkit.entity.Projectile) {
                return DamageSourceType.PROJECTILE;
            }

            return DamageSourceType.ENTITY;
        }

        return switch (event.getCause()) {

            case BLOCK_EXPLOSION,
                 ENTITY_EXPLOSION ->
                    DamageSourceType.EXPLOSION;

            case FALL ->
                    DamageSourceType.FALL;

            case FIRE ->
                    DamageSourceType.FIRE;

            case FIRE_TICK ->
                    DamageSourceType.FIRE_TICK;

            case VOID ->
                    DamageSourceType.VOID;

            case MAGIC ->
                    DamageSourceType.MAGIC;

            case POISON ->
                    DamageSourceType.POISON;

            case WITHER ->
                    DamageSourceType.WITHER;

            case DROWNING ->
                    DamageSourceType.DROWNING;

            case STARVATION ->
                    DamageSourceType.STARVATION;

            case CUSTOM ->
                    DamageSourceType.CUSTOM;

            default ->
                    DamageSourceType.UNKNOWN;
        };
    }
}