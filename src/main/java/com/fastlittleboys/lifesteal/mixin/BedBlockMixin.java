package com.fastlittleboys.lifesteal.mixin;

import com.fastlittleboys.lifesteal.level.HurtOnePlayerExplosionDamageCalculator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BedBlock.class)
public abstract class BedBlockMixin {
    @ModifyArgs(method = "useWithoutItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(" +
        "Lnet/minecraft/world/entity/Entity;" +
        "Lnet/minecraft/world/damagesource/DamageSource;" +
        "Lnet/minecraft/world/level/ExplosionDamageCalculator;" +
        "Lnet/minecraft/world/phys/Vec3;" +
        "FZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"))
    private void lifesteal_cpvpRule(Args args, BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        var explosionCalculator = new HurtOnePlayerExplosionDamageCalculator(player, args.get(2));
        args.set(2, explosionCalculator);
    }
}