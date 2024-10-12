package com.paramada.marycum2024.util;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.networking.packets.payloads.MoneyDataPayLoad;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class MoneyManager {
    public static void spendMoney(ServerPlayerEntity player, int price) {
        var newMoneyPayload = new MoneyDataPayLoad(price, NetworkManager.SPEND_MONEY_ID);
        ClientPlayNetworking.send(newMoneyPayload);
    }
    public static void earnMoney(ServerPlayerEntity player, int amount) {
        var newMoneyPayload = new MoneyDataPayLoad(amount, NetworkManager.SPEND_MONEY_ID);
        ClientPlayNetworking.send(newMoneyPayload);
    }
    public static void syncMoney(ServerPlayerEntity player) {
        var amount = PlayerEntityBridge.getMoney(player);
        var newMoneyPayload = new MoneyDataPayLoad(amount, NetworkManager.SPEND_MONEY_ID);
        ServerPlayNetworking.send(player, newMoneyPayload);
    }
    public static void requestMoneySync() {
        var newMoneyPayload = new MoneyDataPayLoad(NetworkManager.SPEND_MONEY_ID);
        ClientPlayNetworking.send(newMoneyPayload);
    }
}
