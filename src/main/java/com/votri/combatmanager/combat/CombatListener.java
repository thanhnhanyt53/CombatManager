package com.votri.combatmanager.combat.listener;

import com.votri.combatmanager.combat.CombatEndReason;
import com.votri.combatmanager.combat.CombatService;
import com.votri.combatmanager.combat.CombatSession;
import com.votri.combatmanager.combat.CombatContext;
import com.votri.combatmanager.combat.event.CombatDamageEvent;
import com.votri.combatmanager.combat.event.CombatKillEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;

public final class CombatListener
        implements Listener {

    private final CombatService combatService;

    public CombatListener(
            CombatService combatService
    ) {
        this.combatService = combatService;
    }

    @EventHandler(
            priority = EventPriority.HIGH,
            ignoreCancelled = true
    )
    public void onEntityDamage(
            EntityDamageByEntityEvent event
    ) {

        if (!(event.getEntity()
                instanceof LivingEntity target)) {
            return;
        }

        LivingEntity attacker =
                resolveAttacker(event.getDamager());

        if (attacker == null) {
            return;
        }

        if (attacker.equals(target)) {
            return;
        }

        combatService.recordDamage(
                attacker,
                target
        );

        Optional<CombatSession> session =
                combatService.getCombat(
                        target.getUniqueId()
                );

        if (session.isEmpty()) {
            return;
        }

        CombatContext context =
                new CombatContext(
                        attacker,
                        target
                );

        CombatDamageEvent combatEvent =
                new CombatDamageEvent(
                        context,
                        session.get(),
                        event.getDamage()
                );

        org.bukkit.Bukkit
                .getPluginManager()
                .callEvent(combatEvent);

        if (combatEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setDamage(
                combatEvent.getDamage()
        );
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onEntityDeath(
            EntityDeathEvent event
    ) {

        LivingEntity victim =
                event.getEntity();

        Optional<CombatSession> session =
                combatService.getCombat(
                        victim.getUniqueId()
                );

        if (session.isEmpty()) {
            return;
        }

        CombatSession combat =
                session.get();

        LivingEntity attacker = null;

        if (combat.getLastAttacker() != null) {

            Entity entity =
                    victim.getServer()
                            .getEntity(
                                    combat.getLastAttacker()
                            );

            if (entity instanceof LivingEntity living) {
                attacker = living;
            }
        }

        if (attacker != null) {

            org.bukkit.Bukkit
                    .getPluginManager()
                    .callEvent(
                            new CombatKillEvent(
                                    combat,
                                    attacker,
                                    victim
                            )
                    );
        }

        combatService.endCombat(
                combat,
                CombatEndReason.DEATH
        );
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onPlayerDeath(
            PlayerDeathEvent event
    ) {

        combatService.endCombat(
                event.getPlayer().getUniqueId(),
                CombatEndReason.DEATH
        );
    }

    private LivingEntity resolveAttacker(
            Entity damager
    ) {

        if (damager instanceof LivingEntity living) {
            return living;
        }

        if (damager instanceof Projectile projectile) {

            if (projectile.getShooter()
                    instanceof LivingEntity living) {

                return living;
            }
        }

        return null;
    }
}