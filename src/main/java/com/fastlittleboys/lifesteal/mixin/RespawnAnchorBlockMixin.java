package com.fastlittleboys.lifesteal.mixin;

import com.fastlittleboys.lifesteal.level.HurtOnePlayerExplosionDamageCalculator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(RespawnAnchorBlock.class)
public abstract class RespawnAnchorBlockMixin {
    @Unique
    private static final ThreadLocal<Player> lastPlayer = new ThreadLocal<>();

    @Inject(method = "useWithoutItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/RespawnAnchorBlock;explode(" +
        "Lnet/minecraft/world/level/block/state/BlockState;" +
        "Lnet/minecraft/server/level/ServerLevel;" +
        "Lnet/minecraft/core/BlockPos;)V"))
    private void lifesteal_cpvpRulePre(
        BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir
    ) {
        lastPlayer.set(player);
    }

    @ModifyArgs(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;explode(" +
        "Lnet/minecraft/world/entity/Entity;" +
        "Lnet/minecraft/world/damagesource/DamageSource;" +
        "Lnet/minecraft/world/level/ExplosionDamageCalculator;" +
        "Lnet/minecraft/world/phys/Vec3;" +
        "FZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"))
    private void lifesteal_cpvpRule(Args args) {
        var explosionCalculator = new HurtOnePlayerExplosionDamageCalculator(lastPlayer.get(), args.get(2));
        args.set(2, explosionCalculator);
        lastPlayer.remove();
    }
}