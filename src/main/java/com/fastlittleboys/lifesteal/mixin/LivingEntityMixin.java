package com.fastlittleboys.lifesteal.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    private static final int VERY_LOW_HEALTH = 3 * 2;

    @Shadow
    public abstract @Nullable AttributeInstance getAttribute(Holder<Attribute> holder);

    // IDEA doesn't realize this code runs inside LivingEntity so it assumes this instanceof Player is always false.
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "checkTotemDeathProtection", at = @At("HEAD"), cancellable = true)
    private void lifesteal_totemRule(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof Player && getAttribute(Attributes.MAX_HEALTH).getBaseValue() > VERY_LOW_HEALTH) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}