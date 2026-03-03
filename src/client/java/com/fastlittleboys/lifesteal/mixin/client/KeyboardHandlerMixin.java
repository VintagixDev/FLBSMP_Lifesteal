package com.fastlittleboys.lifesteal.mixin.client;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Redirect(method = "handleDebugKeys", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;allChanged()V"))
    private void lifesteal_disableF3ReloadAll(LevelRenderer instance) {}
}