package com.fastlittleboys.lifesteal.mixin;

import com.fastlittleboys.lifesteal.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.CrafterBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(CrafterBlock.class)
public abstract class CrafterBlockMixin {
    @Inject(method = "getPotentialResults", at = @At("RETURN"), cancellable = true)
    private static void lifesteal_disableCrafterCraftingHearts(
        ServerLevel serverLevel, CraftingInput craftingInput, CallbackInfoReturnable<Optional<RecipeHolder<CraftingRecipe>>> cir
    ) {
        if (cir.getReturnValue().isPresent() && cir.getReturnValue().get().value()
            .assemble(craftingInput, serverLevel.registryAccess()).is(ModItems.HEART)) cir.setReturnValue(Optional.empty());
    }
}