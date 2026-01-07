package com.fastlittleboys.lifesteal.item;

import com.fastlittleboys.lifesteal.Lifesteal;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
        var maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            Lifesteal.LOGGER.error("Player {} has no max_health attribute!", player.getName().getString());
            return InteractionResult.FAIL;
        }

        var newMaxHealth = maxHealth.getBaseValue() + 2;
        if (newMaxHealth <= 40) {
            if (!world.isClientSide())
                Lifesteal.LOGGER.info("Player {} consumed a heart and is now at {} hearts.", player.getName().getString(), newMaxHealth / 2);
            maxHealth.setBaseValue(newMaxHealth);
            player.getItemInHand(hand).consume(1, player);
        }
        player.displayClientMessage(Component.literal("§c§lYou already have the maximum amount of hearts."), true);
        return InteractionResult.CONSUME;
    }
}