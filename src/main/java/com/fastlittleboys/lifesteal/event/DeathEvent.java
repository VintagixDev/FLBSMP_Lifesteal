package com.fastlittleboys.lifesteal.event;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.item.ModItems;
import com.fastlittleboys.lifesteal.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DeathEvent {
    static void onDeath(LivingEntity entity, DamageSource damageSource) {
        if (!(entity instanceof ServerPlayer player)) return;

        if (!Lifesteal.tryModifyMaxHealth(player, -2)) {
            Lifesteal.getPlayerHeartData().banPlayer(player.getUUID());
            player.connection.disconnect(Component.translatable("disconnect.lifesteal.banned"));
            ServerInstance.get().getPlayerList().broadcastSystemMessage(
                Component.translatable("chat.lifesteal.ban", player.getDisplayName()).withStyle(ChatFormatting.RED), false);
            for (var connectedPlayer : ServerInstance.get().getPlayerList().getPlayers())
                connectedPlayer.level().playSound(null, connectedPlayer.blockPosition(), ModSounds.BAN, SoundSource.PLAYERS);
        }
        if (damageSource.getEntity() instanceof ServerPlayer attacker &&
            !player.getUUID().equals(attacker.getUUID()) && Lifesteal.tryModifyMaxHealth(attacker, 2)) return;

        var itemStack = new ItemStack(ModItems.HEART);
        itemStack.set(DataComponents.LORE, Lifesteal.createLore((MutableComponent)damageSource.getLocalizedDeathMessage(player)));
        player.spawnAtLocation(player.level(), itemStack);
    }
}