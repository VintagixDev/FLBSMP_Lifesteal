package com.fastlittleboys.lifesteal;

import com.fastlittleboys.lifesteal.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;

public class DeathEventHandler {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(DeathEventHandler::onDeath);
    }

    private static void onDeath(LivingEntity entity, DamageSource damageSource) {
        if (!(entity instanceof ServerPlayer player)) return;

        if (!Lifesteal.tryModifyMaxHealth(player, -2)) {
            // TODO: Add deathban here
        }
        if (damageSource.getEntity() instanceof ServerPlayer attacker &&
            !player.getUUID().equals(attacker.getUUID()) && Lifesteal.tryModifyMaxHealth(attacker, 2)) return;

        var itemStack = new ItemStack(ModItems.HEART);
        itemStack.set(DataComponents.LORE, new ItemLore(List.of(
            ((MutableComponent)damageSource.getLocalizedDeathMessage(player)).withStyle(ChatFormatting.WHITE))));
        player.spawnAtLocation(player.level(), itemStack);
    }
}