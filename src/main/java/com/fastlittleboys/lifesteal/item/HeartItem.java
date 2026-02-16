package com.fastlittleboys.lifesteal.item;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.component.ModComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class HeartItem extends Item {
    public HeartItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level world, @NonNull Player player, @NonNull InteractionHand hand) {
        if (components().has(ModComponents.CRAFTEE) && components().get(ModComponents.CRAFTEE) != player.getUUID()) {
            Lifesteal.showPlayerMessage(player, "item.lifesteal.heart.not_yours", true);
            return InteractionResult.FAIL;
        }

        if (Lifesteal.tryModifyMaxHealth(player, 2)) {
            player.getItemInHand(hand).consume(1, player);
            return InteractionResult.CONSUME;
        }

        Lifesteal.showPlayerMessage(player, "item.lifesteal.heart.max", true);
        return InteractionResult.FAIL;
    }

    @Override
    public void onCraftedBy(@NonNull ItemStack itemStack, @NonNull Player player) {
        if (!(player instanceof ServerPlayer)) return;

        Lifesteal.getPlayerHeartData().restartCraftingCooldown(player.getUUID());
    }
}