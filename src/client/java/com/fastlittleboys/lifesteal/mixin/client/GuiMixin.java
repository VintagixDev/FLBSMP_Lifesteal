package com.fastlittleboys.lifesteal.mixin.client;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @ModifyArgs(method = "renderHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui$HeartType;getSprite(" +
        "ZZZ)Lnet/minecraft/resources/Identifier;"))
    private void lifesteal_fakeHardcoreHearts(Args args) {
        args.set(0, true);
    }
}