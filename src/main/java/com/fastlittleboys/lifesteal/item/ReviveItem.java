package com.fastlittleboys.lifesteal.item;

import com.fastlittleboys.lifesteal.component.ReviveMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;


public class ReviveItem extends Item {
    public ReviveItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level world, @NonNull Player player, @NonNull InteractionHand hand) {
        if(!world.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ReviveMenu.openReviveMenu(serverPlayer);
        }


        return InteractionResult.SUCCESS;
    }



}