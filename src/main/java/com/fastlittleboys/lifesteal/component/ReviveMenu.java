package com.fastlittleboys.lifesteal.component;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.event.ServerInstance;
import com.fastlittleboys.lifesteal.item.ModItems;
import com.fastlittleboys.lifesteal.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;

public class ReviveMenu extends ChestMenu {
    private static final int SLOT_COUNT = 9 * 6;
    private final InteractionHand hand;

    public ReviveMenu(int id, Inventory inventory, InteractionHand hand) {
        super(MenuType.GENERIC_9x6, id, inventory, new SimpleContainer(SLOT_COUNT), 6);
        this.hand = hand;

        // TODO: rewrite to allow multiple pages if more than 54 players are banned?
        var i = 0;
        for (var uuid : Lifesteal.getSaveData().getBannedPlayers()) {
            var playerHead = new ItemStack(Items.PLAYER_HEAD);
            var player = ServerInstance.get().services().profileResolver().fetchById(uuid);
            playerHead.set(DataComponents.PROFILE, player.map(ResolvableProfile::createResolved)
                .orElseGet(() -> ResolvableProfile.createUnresolved(uuid)));
            playerHead.set(ModComponents.REVIVE_UUID, uuid);
            getSlot(i++).set(playerHead);
        }
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, ContainerInput containerInput, Player player) {
        if (slotIndex < 0 || slotIndex >= SLOT_COUNT) return;

        if (player.getItemInHand(hand).is(ModItems.REVIVE)) {
            var components = getSlot(slotIndex).getItem().getComponents();
            if (!components.has(ModComponents.REVIVE_UUID)) return;

            var heartData = Lifesteal.getSaveData();
            var uuid = components.get(ModComponents.REVIVE_UUID);
            if (!heartData.isPlayerBanned(uuid)) return;

            player.getItemInHand(hand).shrink(1);
            heartData.unbanPlayer(uuid);
            Lifesteal.playGlobalSound(ModSounds.REVIVE, SoundSource.PLAYERS);
        }
        else player.sendOverlayMessage(Component.translatable("item.lifesteal.revive.notfound").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        ((ServerPlayer)player).closeContainer();
    }
}