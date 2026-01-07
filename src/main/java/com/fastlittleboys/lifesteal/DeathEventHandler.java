package com.fastlittleboys.lifesteal;

import com.fastlittleboys.lifesteal.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class DeathEventHandler {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(DeathEventHandler::onDeath);
    }

    private static void onDeath(LivingEntity entity, DamageSource damageSource) {
        if (!(entity instanceof ServerPlayer player)) return;

        if (!Lifesteal.tryModifyMaxHealth(player, -2)) {
            // TODO: Add deathban here
        }
        if (damageSource.getEntity() instanceof ServerPlayer attacker && Lifesteal.tryModifyMaxHealth(attacker, 2)) return;
        player.spawnAtLocation(player.level(), ModItems.HEART); // TODO: Add lore to heart of who died and how
    }
}