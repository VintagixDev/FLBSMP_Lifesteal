package com.fastlittleboys.lifesteal;

import com.fastlittleboys.lifesteal.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class DeathEventHandler {

    public static void register(){
        ServerLivingEntityEvents.AFTER_DEATH.register(DeathEventHandler::onDeath);

    }

    private static void onDeath(LivingEntity entity, DamageSource dmgSource){
        if(!(entity instanceof ServerPlayer)) return;
        var player = (ServerPlayer)entity;
        var maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        var maxHealth = maxHealthAttribute.getBaseValue();
        var level = player.level();

        // Removing heart from player
        if(maxHealth >= 4){
            maxHealthAttribute.setBaseValue(maxHealth-2);
        }

        // Adding heart to attacker if it's a player
        if(dmgSource.getEntity() instanceof ServerPlayer attacker){
            var atkMaxHealthAttribute = attacker.getAttribute(Attributes.MAX_HEALTH);
            atkMaxHealthAttribute.setBaseValue(atkMaxHealthAttribute.getValue() + 2);
            return;
        }

        // Dropping a Heart
        ItemStack heart = new ItemStack(ModItems.HEART);
        level.addFreshEntity(new ItemEntity(
                level,
                player.getX(), player.getY(), player.getZ(),
                heart
        ));

    }
}
