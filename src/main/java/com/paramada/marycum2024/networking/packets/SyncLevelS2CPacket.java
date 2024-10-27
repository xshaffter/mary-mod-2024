package com.paramada.marycum2024.networking.packets;

import com.google.common.collect.ImmutableMultimap;
import com.paramada.marycum2024.util.functionality.bridges.LivingEntityBridge;
import com.paramada.marycum2024.util.functionality.bridges.UpgradeManager;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SyncLevelS2CPacket {
    public static <T extends FabricPacket> void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        var player = client.player;
        var level = buf.readInt();
        if (player == null) {
            return;
        }
        LivingEntityBridge.getPersistentData(player).putInt("level", level);

        var attributes = UpgradeManager.getLevelModifiers(level);
        var playerAttrs = player.getAttributes();
        for (var attr : attributes.modifiers().entries()) {
            if (!playerAttrs.hasAttribute(attr.getKey())) {
                playerAttrs.addTemporaryModifiers(ImmutableMultimap.of(attr.getKey(), attr.getValue()));
            }
        }
    }
}
