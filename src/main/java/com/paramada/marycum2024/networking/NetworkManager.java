package com.paramada.marycum2024.networking;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.networking.packets.*;
import com.paramada.marycum2024.networking.packets.payloads.MoneyDataPayLoad;
import com.paramada.marycum2024.networking.packets.payloads.PotionUpgradeDataPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import static net.minecraft.network.packet.CustomPayload.Id;

import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;

public class NetworkManager {
    public static final Id<MoneyDataPayLoad> SYNC_MONEY_ID = CustomPayload.id(String.join(":", MaryMod2024.MOD_ID, "sync_money"));
    public static final Id<MoneyDataPayLoad> EARN_MONEY_ID = CustomPayload.id(String.join(":", MaryMod2024.MOD_ID, "earn_money"));
    public static final Id<MoneyDataPayLoad> SPEND_MONEY_ID = CustomPayload.id(String.join(":", MaryMod2024.MOD_ID, "spend_money"));
    public static final Id<MoneyDataPayLoad> REQUEST_MONEY_ID = CustomPayload.id(String.join(":", MaryMod2024.MOD_ID, "request_money"));
    public static final Id<PotionUpgradeDataPayload> SYNC_UPGRADE_ID = CustomPayload.id(String.join(":", MaryMod2024.MOD_ID, "sync_upgrade"));
    public static final Id<PotionUpgradeDataPayload> REQUEST_UPGRADE_ID = CustomPayload.id(String.join(":", MaryMod2024.MOD_ID, "sync_upgrade"));

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(EARN_MONEY_ID, EarnMoneyC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SPEND_MONEY_ID, SpendMoneyC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_MONEY_ID, RequestMoneyC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_UPGRADE_ID, RequestUpgradeC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_MONEY_ID, SyncMoneyS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SYNC_UPGRADE_ID, SyncUpgradeS2CPacket::receive);
    }
}