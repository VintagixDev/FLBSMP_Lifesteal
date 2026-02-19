package com.fastlittleboys.lifesteal.command;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.item.ModItems;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;


public class WithdrawCommand {
    private static final SimpleCommandExceptionType ERROR_CONSOLE = new SimpleCommandExceptionType(Component.translatable("commands.lifesteal.withdraw.console"));
    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(Component.translatable("commands.lifesteal.withdraw.failed"));

    public static int executeWithdrawCommand(CommandContext<CommandSourceStack> context, int amount) throws CommandSyntaxException {
        if(!(context.getSource().getEntity() instanceof ServerPlayer player)) throw ERROR_CONSOLE.create();
        if(!Lifesteal.tryModifyMaxHealth(player, amount * -2)) throw ERROR_FAILED.create();

        ItemStack itemStack = new ItemStack(ModItems.HEART, amount);
        itemStack.set(DataComponents.LORE, Lifesteal.createLore(Component.translatable("item.lifesteal.heart.lore.withdrawn", player.getName())));
        var itemEntity = player.drop(itemStack, false);
        if (itemEntity != null) {
            itemEntity.setNoPickUpDelay();
            itemEntity.setTarget(player.getUUID());
        }

        return 1;
    }
}

