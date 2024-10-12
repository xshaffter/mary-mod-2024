package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.util.LivingEntityBridge;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SyncMoneyS2CPacket {
    public static <T extends FabricPacket> void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        LivingEntityBridge.getPersistentData(client.player).putInt("coins", buf.readInt());
    }
}
