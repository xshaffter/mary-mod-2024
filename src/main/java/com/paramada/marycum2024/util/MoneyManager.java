package com.paramada.marycum2024.util;

import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.networking.NetworkManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class MoneyManager {
    public static void spendMoney(int price) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(price);
        ClientPlayNetworking.send(NetworkManager.SPEND_MONEY_ID, buffer);
    }

    public static int getMoney(PlayerEntity player) {
        var persistent = LivingEntityBridge.getPersistentData(player);
        return persistent.getInt("coins");
    }

    public static void earnMoney(int amount) {
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
