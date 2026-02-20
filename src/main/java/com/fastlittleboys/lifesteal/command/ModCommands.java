package com.fastlittleboys.lifesteal.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;

public class ModCommands {
    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                Commands.literal("withdraw")
                    .executes(context -> WithdrawCommand.withdraw(context, 1))

                    .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                    .executes(context -> WithdrawCommand.withdraw(context, IntegerArgumentType.getInteger(context, "amount"))))
            );
            dispatcher.register(
                Commands.literal("openend")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))

                    .executes(OpenEndCommand::isOpen)

                    .then(Commands.argument("open", BoolArgumentType.bool())
                    .executes(context -> OpenEndCommand.openEnd(context, BoolArgumentType.getBool(context, "open"))))
            );
        });
    }
}