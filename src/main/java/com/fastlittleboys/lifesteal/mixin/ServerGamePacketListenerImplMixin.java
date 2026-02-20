package com.fastlittleboys.lifesteal.mixin;

import com.fastlittleboys.lifesteal.Lifesteal;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleClientCommand", at = @At("HEAD"), cancellable = true)
    private void lifesteal_disableBannedRespawn(ServerboundClientCommandPacket serverboundClientCommandPacket, CallbackInfo ci) {
        if (serverboundClientCommandPacket.getAction() == ServerboundClientCommandPacket.Action.PERFORM_RESPAWN &&
            Lifesteal.getSaveData().isPlayerBanned(player.getUUID())) ci.cancel();
    }
}