package com.fastlittleboys.lifesteal.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;

public class ModEvents {
    public static void initialize() {
        ServerLivingEntityEvents.AFTER_DEATH.register(DeathEvent::onDeath);
        ServerLoginConnectionEvents.QUERY_START.register(ConnectEvent::onConnect);
    }
}