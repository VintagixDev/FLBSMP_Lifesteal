package com.fastlittleboys.lifesteal.component;

import com.fastlittleboys.lifesteal.Lifesteal;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import java.util.UUID;

public class ModComponents {
    public static final DataComponentType<UUID> CRAFTEE = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(Lifesteal.MOD_ID, "craftee"),
        DataComponentType.<UUID>builder().persistent(UUIDUtil.CODEC).build());

    public static final DataComponentType<UUID> REVIVE_UUID = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(Lifesteal.MOD_ID, "revive_uuid"),
        DataComponentType.<UUID>builder().persistent(UUIDUtil.CODEC).build());

    public static void initialize() {
        Lifesteal.LOGGER.info("Components registered.");
    }
}