package com.fastlittleboys.lifesteal.item;

import com.fastlittleboys.lifesteal.Lifesteal;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class HeartItem extends Item {
    public HeartItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level world, @NonNull Player player, @NonNull InteractionHand hand) {
        if (Lifesteal.tryModifyMaxHealth(player, 2)) {
            player.getItemInHand(hand).consume(1, player);
            return InteractionResult.CONSUME;
        }

        if (world.isClientSide()) player.displayClientMessage(Component.translatable("item.lifesteal.heart.max")
            .withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD), true);
        return InteractionResult.FAIL;
    }
}