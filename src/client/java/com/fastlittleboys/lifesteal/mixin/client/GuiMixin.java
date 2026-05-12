package com.fastlittleboys.lifesteal.mixin.client;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @ModifyVariable(method = "extractHeart", at = @At("HEAD"), name = "isHardcore", argsOnly = true)
    private boolean lifesteal_fakeHardcoreHearts(boolean isHardcore) {
        return true;
    }
}