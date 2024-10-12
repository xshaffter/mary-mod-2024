package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.networking.packets.payloads.MoneyDataPayLoad;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public class RequestMoneyC2SPacket {
    public static void receive(MoneyDataPayLoad customPayload, ServerPlayNetworking.Context context) {
        var money = PlayerEntityBridge.getMoney(context.player());
        var newMoneyPayload = new MoneyDataPayLoad(money, NetworkManager.SYNC_MONEY_ID);
        ServerPlayNetworking.send(context.player(), newMoneyPayload);
    }
}
