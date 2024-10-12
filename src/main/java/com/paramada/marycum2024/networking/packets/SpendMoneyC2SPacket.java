package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.networking.packets.payloads.MoneyDataPayLoad;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public class SpendMoneyC2SPacket {
    public static void receive(MoneyDataPayLoad customPayload, ServerPlayNetworking.Context context) {
        PlayerEntityBridge.spendMoney(context.player(), customPayload.getMoneyAmount());
    }
}
