package com.fastlittleboys.lifesteal.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

public class HurtOnePlayerExplosionDamageCalculator extends ExplosionDamageCalculator {
    private final Player player;
    private final ExplosionDamageCalculator originalDamageCalculator;

    public HurtOnePlayerExplosionDamageCalculator(Player player, ExplosionDamageCalculator originalDamageCalculator) {
        this.player = player;
        this.originalDamageCalculator = originalDamageCalculator;
    }

    @Override
    public Optional<Float> getBlockExplosionResistance(
        Explosion explosion, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, FluidState fluidState
    ) {
        return originalDamageCalculator == null ? super.getBlockExplosionResistance(explosion, blockGetter, blockPos, blockState, fluidState)
            : originalDamageCalculator.getBlockExplosionResistance(explosion, blockGetter, blockPos, blockState, fluidState);
    }

    @Override
    public boolean shouldBlockExplode(Explosion explosion, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, float f) {
        return originalDamageCalculator == null ? super.shouldBlockExplode(explosion, blockGetter, blockPos, blockState, f)
            : originalDamageCalculator.shouldBlockExplode(explosion, blockGetter, blockPos, blockState, f);
    }

    @Override
    public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
        if (entity instanceof Player) {
            return entity == player;
        }
        return originalDamageCalculator == null ? super.shouldDamageEntity(explosion, entity)
            : originalDamageCalculator.shouldDamageEntity(explosion, entity);
    }

    @Override
    public float getKnockbackMultiplier(Entity entity) {
        return originalDamageCalculator == null ? super.getKnockbackMultiplier(entity)
            : originalDamageCalculator.getKnockbackMultiplier(entity);
    }

    @Override
    public float getEntityDamageAmount(Explosion explosion, Entity entity, float f) {
        return originalDamageCalculator == null ? super.getEntityDamageAmount(explosion, entity, f)
            : originalDamageCalculator.getEntityDamageAmount(explosion, entity, f);
    }
}