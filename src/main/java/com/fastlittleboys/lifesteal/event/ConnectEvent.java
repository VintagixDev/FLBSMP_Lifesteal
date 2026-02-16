package com.fastlittleboys.lifesteal.event;

import com.fastlittleboys.lifesteal.Lifesteal;
import com.fastlittleboys.lifesteal.mixin.ServerLoginPacketListenerImplAccessor;
import net.fabricmc.fabric.api.networking.v1.LoginPacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ConnectEvent {
    @SuppressWarnings("unused")
    static void onConnect(ServerLoginPacketListenerImpl handler, MinecraftServer server,
            LoginPacketSender sender, ServerLoginNetworking.LoginSynchronizer synchronizer) {
        var profile = ((ServerLoginPacketListenerImplAccessor)handler).getAuthenticatedProfile();
        Objects.requireNonNull(profile);

        synchronizer.waitFor(CompletableFuture.runAsync(() -> {
            if (Lifesteal.getPlayerHeartData().isPlayerBanned(profile.id())) {
                handler.disconnect(Component.translatable("disconnect.lifesteal.banned"));
            }
        }));
    }
}