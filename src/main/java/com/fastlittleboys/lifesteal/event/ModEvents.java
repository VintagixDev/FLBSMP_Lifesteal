package com.fastlittleboys.lifesteal.event;

import com.fastlittleboys.lifesteal.Lifesteal;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;

public class ModEvents {
    public static void initialize() {
        ServerLivingEntityEvents.AFTER_DEATH.register(DeathEvent::onDeath);
        ServerLoginConnectionEvents.QUERY_START.register(ConnectEvent::onConnect);
        ServerLifecycleEvents.SERVER_STARTED.register(ServerInstance::set);

        Lifesteal.LOGGER.info("Events registered.");
    }
}