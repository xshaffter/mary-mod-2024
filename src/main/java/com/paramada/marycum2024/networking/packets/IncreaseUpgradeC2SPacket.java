package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.LivingEntityBridge;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class IncreaseUpgradeC2SPacket {
    public static <T extends FabricPacket> void receive(MinecraftServer server, ServerPlayerEntity player,
                                                        ServerPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        var upgrade = LivingEntityBridge.getPersistentData(player).getInt("upgrade");
        LivingEntityBridge.getPersistentData(player).putInt("upgrade", upgrade + 1);
        var newBuf = PacketByteBufs.create();
        newBuf.writeInt(upgrade + 1);
        ServerPlayNetworking.send(player, NetworkManager.SYNC_UPGRADE_ID, newBuf);
    }
}
