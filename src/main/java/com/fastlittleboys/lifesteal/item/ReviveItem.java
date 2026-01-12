package com.fastlittleboys.lifesteal.item;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.event.ServerInstance;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.net.*;
import java.util.Set;
import java.util.UUID;

public class ReviveItem extends Item {
    public ReviveItem(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level world, @NonNull Player player, @NonNull InteractionHand hand) {
        if(!world.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                (syncId, inventory, p) -> {
                    ChestMenu reviveMenu = ChestMenu.threeRows(syncId, inventory);
                    Set<UUID> bannedPlayers = Lifesteal.getPlayerHeartData(ServerInstance.get()).getBannedPlayers();
                    var i = 0;
                    for(UUID uuid : bannedPlayers) {
                        ItemStack item = new ItemStack(Items.PLAYER_HEAD);

                        item.set(DataComponents.PROFILE, ResolvableProfile.createUnresolved(UUID.fromString("fb352471-a500-4247-8851-fced66ac41fc")));
                        item.set(DataComponents.CUSTOM_NAME, Component.literal(uuid.toString()));
                        reviveMenu.getSlot(i).set(item);
                        i++;
                    }
                    return reviveMenu;
                },
                    Component.literal("Revive Menu")

                    ));

        }


        return InteractionResult.SUCCESS;
    }



}