package com.fastlittleboys.lifesteal.mixin;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.component.ModComponents;
import com.fastlittleboys.lifesteal.event.ServerInstance;
import com.fastlittleboys.lifesteal.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {
    @Unique
    private static final long COOLDOWN = 5000; // 24 * 60 * 60 * 1000; // TODO: Move to a config file.

    @ModifyVariable(method = "slotChangedCraftingGrid", at = @At("STORE"), ordinal = 1, require = 1)
    private static ItemStack lifesteal_checkCraftingCooldown(ItemStack value,
            AbstractContainerMenu abstractContainerMenu, ServerLevel serverLevel, Player player) {
        if (!value.is(ModItems.HEART)) return value;
        if (Lifesteal.getPlayerHeartData(ServerInstance.get()).hasCraftingCooldownExpired(player.getUUID(), COOLDOWN)) {
            value.set(ModComponents.CRAFTEE, player.getUUID());
            value.set(DataComponents.LORE, new ItemLore(List.of(
                Component.translatable("item.lifesteal.heart.crafted", player.getName()).withStyle(ChatFormatting.WHITE))));
            return value;
        }
        return ItemStack.EMPTY;
    }
}