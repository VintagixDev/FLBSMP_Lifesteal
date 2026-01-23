package com.fastlittleboys.lifesteal.component;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.event.ServerInstance;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jspecify.annotations.NonNull;

import java.util.Set;
import java.util.UUID;

public class ReviveMenu extends ChestMenu {


    public ReviveMenu(MenuType<?> menuType, int i, Inventory inventory, Container container, int j) {
        super(menuType, i, inventory, container, j);
    }

    public static ReviveMenu openMenu(int syncId, Inventory inventory) {
        return new ReviveMenu(
            MenuType.GENERIC_9x3,
            syncId,
            inventory,
            new SimpleContainer(27), 3);
    }

    @Override
    public void clicked(int i, int j, @NonNull ClickType clickType, @NonNull Player player) {
        if(getSlot(i).index <= 26){
            ItemStack item = getSlot(i).getItem();
            Lifesteal.LOGGER.info(item.get(ModComponents.REVIVE_UUID).toString());

            return;
        }
        super.clicked(i, j, clickType, player);

    }

    public static void openReviveMenu(ServerPlayer serverPlayer){
        serverPlayer.openMenu(new SimpleMenuProvider(
                (syncId, inventory, p) -> {
                    ReviveMenu reviveMenu = ReviveMenu.openMenu(syncId, inventory);
                    Set<UUID> bannedPlayers = Lifesteal.getPlayerHeartData(ServerInstance.get()).getBannedPlayers();
                    var i = 0;
                    for(UUID uuid : bannedPlayers) {
                        ItemStack item = new ItemStack(Items.PLAYER_HEAD);
                        var str_uuid = uuid.toString();

                        item.set(DataComponents.PROFILE, ResolvableProfile.createUnresolved(UUID.fromString(str_uuid)));
                        item.set(DataComponents.CUSTOM_NAME, Component.literal(str_uuid));
                        item.set(ModComponents.REVIVE_UUID, UUID.fromString(str_uuid));
                        reviveMenu.getSlot(i).set(item);
                        i++;
                    }
                    return reviveMenu;
                },
                Component.literal("Revive Menu").withStyle(ChatFormatting.GREEN)

        ));
    }





}
