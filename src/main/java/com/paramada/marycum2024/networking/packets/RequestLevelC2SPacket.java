package com.paramada.marycum2024.networking.packets;

import com.google.common.collect.ImmutableMultimap;
import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.functionality.bridges.LivingEntityBridge;
import com.paramada.marycum2024.util.functionality.bridges.UpgradeManager;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class RequestLevelC2SPacket {
    public static <T extends FabricPacket> void receive(MinecraftServer server, ServerPlayerEntity player,
                                                        ServerPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        var newBuf = PacketByteBufs.create();
        var level = LivingEntityBridge.getPersistentData(player).getInt("level");
        newBuf.writeInt(level);

        var attributes = UpgradeManager.getLevelModifiers(level);

        var playerAttrs = player.getAttributes();
        for (var attr : attributes.modifiers().entries()) {
            if (!playerAttrs.hasAttribute(attr.getKey())) {
                playerAttrs.addTemporaryModifiers(ImmutableMultimap.of(attr.getKey(), attr.getValue()));
            }
        }

        ServerPlayNetworking.send(player, NetworkManager.SYNC_LEVEL_ID, newBuf);
    }
}
