package com.fastlittleboys.lifesteal.mixin;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.event.ServerInstance;
import com.fastlittleboys.lifesteal.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {
    @Shadow
    public abstract Slot getSlot(int i);

    // IDEA doesn't realize this code runs inside AbstractContainerMenu so it assumes this instanceof CraftingMenu is always false.
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "doClick", at = @At("HEAD"))
    private void lifesteal_restartCraftingCooldown(int i, int j, ClickType clickType, Player player, CallbackInfo ci) {
        // Slot 0 is the output of a crafting table.
        if ((Object)this instanceof CraftingMenu && i == 0 && getSlot(i).getItem().is(ModItems.HEART)) {
            Lifesteal.getPlayerHeartData(ServerInstance.get()).restartCraftingCooldown(player.getUUID());
        }

    }
}