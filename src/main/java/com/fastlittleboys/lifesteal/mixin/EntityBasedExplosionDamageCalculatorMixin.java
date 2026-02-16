package com.fastlittleboys.lifesteal.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityBasedExplosionDamageCalculator.class)
public abstract class EntityBasedExplosionDamageCalculatorMixin extends ExplosionDamageCalculator {
    @Unique
    private static final int DELAY = 5 * 20;

    @Shadow
    @Final
    private Entity source;

    @Override
    public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
        if (source instanceof MinecartTNT || source instanceof EndCrystal) {
            return source.tickCount >= DELAY || !(entity instanceof Player);
        }
        return true;
    }
}