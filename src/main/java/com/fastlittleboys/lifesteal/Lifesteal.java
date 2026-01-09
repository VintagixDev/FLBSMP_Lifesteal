package com.fastlittleboys.lifesteal;

import com.fastlittleboys.lifesteal.command.CommandInitializer;
import com.fastlittleboys.lifesteal.event.ModEvents;
import com.fastlittleboys.lifesteal.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Lifesteal implements ModInitializer {
	public static final String MOD_ID = "lifesteal";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * @return {@code true} when the new max health was applied successfully; {@code false} otherwise.
	 */
	public static boolean tryModifyMaxHealth(@NonNull Player player, int delta) {
		var maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
		Objects.requireNonNull(maxHealth);

		// Because max_health is small (2-40) and always an integer value, floating point arithmetic will never introduce imprecision.
		var newMaxHealth = maxHealth.getBaseValue() + delta;
		if (newMaxHealth >= 2 && newMaxHealth <= 40) {
			maxHealth.setBaseValue(newMaxHealth);
			return true;
		}
		return false;
	}

	public static PlayerHeartData getPlayerHeartData(MinecraftServer server) {
		return server.overworld().getDataStorage().computeIfAbsent(PlayerHeartData.TYPE);
	}

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModEvents.initialize();
		CommandInitializer.initialize();
	}
}