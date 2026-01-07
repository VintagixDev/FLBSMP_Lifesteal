package com.fastlittleboys.lifesteal.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;

public class CommandInitializer {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    Commands.literal("withdraw")
                            // /withdraw
                            .executes(context ->
                                    WithdrawCommand.executeWithdrawCommand(context, 1)
                            )
                            // /withdraw <amount>
                            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                    .executes(context ->
                                            WithdrawCommand.executeWithdrawCommand(
                                                    context,
                                                    IntegerArgumentType.getInteger(context, "amount")
                                            )
                                    )
                            )
            );
        });
    }
}
