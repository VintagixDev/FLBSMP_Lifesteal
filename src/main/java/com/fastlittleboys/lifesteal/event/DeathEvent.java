package com.fastlittleboys.lifesteal.event;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DeathEvent {
    static void onDeath(LivingEntity entity, DamageSource damageSource) {
        if (!(entity instanceof ServerPlayer player)) return;

        if (!Lifesteal.tryModifyMaxHealth(player, -2)) {
            Lifesteal.getPlayerHeartData(ServerInstance.get()).banPlayer(player.getUUID());
            player.connection.disconnect(Component.translatable("disconnect.lifesteal.banned"));
        }
        if (damageSource.getEntity() instanceof ServerPlayer attacker &&
            !player.getUUID().equals(attacker.getUUID()) && Lifesteal.tryModifyMaxHealth(attacker, 2)) return;

        var itemStack = new ItemStack(ModItems.HEART);
        itemStack.set(DataComponents.LORE, Lifesteal.createLore((MutableComponent)damageSource.getLocalizedDeathMessage(player)));
        player.spawnAtLocation(player.level(), itemStack);
    }
}