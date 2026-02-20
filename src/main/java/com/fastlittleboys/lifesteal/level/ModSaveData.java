package com.fastlittleboys.lifesteal.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.*;

public class ModSaveData extends SavedData {
    public static final Codec<ModSaveData> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            UUIDUtil.CODEC_SET
                .optionalFieldOf("bannedPlayers", Set.of())
                .forGetter(playerHeartData -> playerHeartData.bannedPlayers),
            Codec.unboundedMap(UUIDUtil.STRING_CODEC, Codec.LONG)
                .optionalFieldOf("craftingCooldowns", Map.of())
                .forGetter(playerHeartData -> playerHeartData.craftingCooldowns),
            Codec.BOOL
                .optionalFieldOf("endOpen", false)
                .forGetter(playerHeartData -> playerHeartData.endOpen)
        ).apply(instance, ModSaveData::new));
    @SuppressWarnings("DataFlowIssue")
    public static final SavedDataType<ModSaveData> TYPE = new SavedDataType<>(
        "player_heart_data", ModSaveData::new, CODEC, null);
    private final Set<UUID> bannedPlayers;
    private final Map<UUID, Long> craftingCooldowns;
    private boolean endOpen;

    public ModSaveData() {
        this(Set.of(), Map.of(), false);
    }

    public ModSaveData(Set<UUID> bannedPlayers, Map<UUID, Long> craftingCooldowns, boolean endOpen) {
        this.bannedPlayers = new HashSet<>(bannedPlayers);
        this.craftingCooldowns = new HashMap<>(craftingCooldowns);
        this.endOpen = endOpen;
    }

    public Set<UUID> getBannedPlayers() {
        return Collections.unmodifiableSet(bannedPlayers);
    }

    public boolean isPlayerBanned(UUID player) {
        return bannedPlayers.contains(player);
    }

    public void banPlayer(UUID player) {
        if (bannedPlayers.add(player)) setDirty();
    }

    public void unbanPlayer(UUID player) {
        if (bannedPlayers.remove(player)) setDirty();
    }

    public boolean hasCraftingCooldownExpired(UUID player, long cooldown) {
        var start = craftingCooldowns.get(player);
        return start != null && System.currentTimeMillis() >= start + cooldown;
    }

    public void stopCraftingCooldown(UUID player) {
        if (craftingCooldowns.remove(player) != null) setDirty();
    }

    public void startCraftingCooldown(UUID player) {
        if (craftingCooldowns.putIfAbsent(player, System.currentTimeMillis()) == null) setDirty();
    }

    public void restartCraftingCooldown(UUID player) {
        craftingCooldowns.put(player, System.currentTimeMillis());
        setDirty();
    }

    public boolean isEndOpen() {
        return endOpen;
    }

    public void setEndOpen(boolean value) {
        endOpen = value;
        setDirty();
    }
}