package com.fastlittleboys.lifesteal.command;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class OpenEndCommand {
    public static int isOpen(CommandContext<CommandSourceStack> context) {
        var isEndOpen = Lifesteal.getSaveData().isEndOpen();
        context.getSource().sendSuccess(() -> Component.translatable("commands.lifesteal.openend.get", String.valueOf(isEndOpen)), true);
        return 0;
    }

    public static int openEnd(CommandContext<CommandSourceStack> context, boolean open) {
        Lifesteal.getSaveData().setEndOpen(open);
        context.getSource().sendSuccess(() -> Component.translatable("commands.lifesteal.openend.set", String.valueOf(open)), true);
        return 0;
    }
}