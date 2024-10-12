package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.networking.packets.payloads.MoneyDataPayLoad;
import com.paramada.marycum2024.util.LivingEntityBridge;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SyncMoneyS2CPacket {
    public static void receive(MoneyDataPayLoad moneyDataPayLoad, ClientPlayNetworking.Context context) {
        LivingEntityBridge.getPersistentData(context.player()).putInt("coins", moneyDataPayLoad.getMoneyAmount());
    }
}
