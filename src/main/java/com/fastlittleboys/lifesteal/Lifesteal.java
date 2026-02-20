package com.fastlittleboys.lifesteal;

import com.fastlittleboys.lifesteal.command.ModCommands;
import com.fastlittleboys.lifesteal.component.ModComponents;
import com.fastlittleboys.lifesteal.event.ModEvents;
import com.fastlittleboys.lifesteal.event.ServerInstance;
import com.fastlittleboys.lifesteal.item.ModItems;
import com.fastlittleboys.lifesteal.level.ModSaveData;
import com.fastlittleboys.lifesteal.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ItemLore;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Lifesteal implements ModInitializer {
	public static final String MOD_ID = "lifesteal";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final int MIN_HEALTH = 2;
	private static final int MAX_HEALTH = 20 * 2;
	private static final int LOW_HEALTH = 7 * 2;

	public static ItemLore createLore(MutableComponent component) {
		return new ItemLore(List.of(component.withStyle(ChatFormatting.WHITE)));
	}

	/**
	 * @return {@code true} when the new max health was applied successfully; {@code false} otherwise.
	 */
	public static boolean tryModifyMaxHealth(@NonNull Player player, int delta) {
		var maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
		assert maxHealth != null;

		// Because max_health is small (2-40) and always an integer value, floating point arithmetic will never introduce imprecision.
		var newMaxHealth = maxHealth.getBaseValue() + delta;
		if (newMaxHealth >= MIN_HEALTH && newMaxHealth <= MAX_HEALTH) {
			if (player instanceof ServerPlayer) {
				var heartData = getSaveData();
                if (newMaxHealth < LOW_HEALTH) heartData.startCraftingCooldown(player.getUUID());
                else heartData.stopCraftingCooldown(player.getUUID());
				maxHealth.setBaseValue(newMaxHealth);
			}
			return true;
		}
		return false;
	}

	public static ModSaveData getSaveData() {
		return ServerInstance.get().overworld().getDataStorage().computeIfAbsent(ModSaveData.TYPE);
	}

	public static void playGlobalSound(SoundEvent soundEvent, SoundSource soundSource) {
		for (var connectedPlayer : ServerInstance.get().getPlayerList().getPlayers())
			connectedPlayer.level().playSound(null, connectedPlayer.blockPosition(), soundEvent, soundSource);
	}

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModEvents.initialize();
		ModComponents.initialize();
		ModCommands.initialize();
		ModSounds.initialize();
	}
}