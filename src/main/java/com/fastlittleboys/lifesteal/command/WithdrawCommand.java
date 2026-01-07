package com.fastlittleboys.lifesteal.command;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.item.ModItems;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;


public class WithdrawCommand {

    public static int executeWithdrawCommand(CommandContext<CommandSourceStack> context, int amount) throws CommandSyntaxException {

        amount *= 2;
        var sender = context.getSource().getEntity();
        if(!(sender instanceof ServerPlayer)){
            context.getSource().sendFailure(Component.literal("Error: Console cannot execute this command."));
            return 0;
        }
        var player = (Player)sender;

        if(amount >= player.getAttribute(Attributes.MAX_HEALTH).getBaseValue()) {
            context.getSource().sendFailure(
                Component.literal("Error: Cannot get under 1 heart. "));
            return 0;
        }


        ItemStack heart = new ItemStack(ModItems.HEART);
        heart.set(DataComponents.LORE, new ItemLore(List.of(
            (Component.literal(player.getName().getString() + "'s Withdrawn heart"))
            .withStyle(ChatFormatting.WHITE))));

        /* TODO when inventory contains heart, check if inventory's heart+amount > 64
            or else extra withdrawn hearts vanishes from existence :(
        * */

        if(player.getInventory().getFreeSlot() == -1 && !player.getInventory().contains(heart)){
            context.getSource().sendFailure(Component.literal("Error: Inventory full."));
            return 0;
        }

        if(!Lifesteal.tryModifyMaxHealth(player, -amount)){
            context.getSource().sendFailure(Component.literal("Error: Not enough hearts."));
            return 0;
        }

        heart.setCount(amount/2);
        player.getInventory().add(heart);



        return 1;
    }
}

