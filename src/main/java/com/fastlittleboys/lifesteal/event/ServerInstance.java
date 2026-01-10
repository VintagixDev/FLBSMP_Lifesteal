package com.fastlittleboys.lifesteal.event;

import net.minecraft.server.MinecraftServer;

public class ServerInstance {
    private static MinecraftServer server;

    public static MinecraftServer get() {
        return server;
    }

    static void set(MinecraftServer server) {
        ServerInstance.server = server;
    }
}