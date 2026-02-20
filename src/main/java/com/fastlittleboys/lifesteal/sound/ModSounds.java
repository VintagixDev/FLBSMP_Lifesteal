package com.fastlittleboys.lifesteal.sound;

import com.fastlittleboys.lifesteal.Lifesteal;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final SoundEvent BAN = register("ban");
    public static final SoundEvent REVIVE = register("revive");

    public static void initialize() {
        Lifesteal.LOGGER.info("Sounds registered.");
    }

    private static SoundEvent register(String name) {
        var identifier = Identifier.fromNamespaceAndPath(Lifesteal.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
    }
}