package com.paramada.marycum2024.util;

import com.paramada.marycum2024.networking.NetworkManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class MoneyManager {
    public static void spendMoney(ServerPlayerEntity player, int price) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(price);
        ClientPlayNetworking.send(NetworkManager.SPEND_MONEY_ID, buffer);
    }
    public static void earnMoney(ServerPlayerEntity player, int amount) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(amount);
        ClientPlayNetworking.send(NetworkManager.EARN_MONEY_ID, buffer);
    }
    public static void syncMoney(ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(PlayerEntityBridge.getMoney(player));
        ServerPlayNetworking.send(player, NetworkManager.SYNC_MONEY_ID, buffer);
    }
    public static void requestMoneySync() {
        var buf = PacketByteBufs.create();
        ClientPlayNetworking.send(NetworkManager.REQUEST_MONEY_ID, buf);
    }
}
