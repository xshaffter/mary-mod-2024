package com.paramada.marycum2024.networking;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.networking.packets.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class NetworkManager {
    public static final Identifier SYNC_MONEY_ID = new Identifier(MaryMod2024.MOD_ID, "sync_money");
    public static final Identifier EARN_MONEY_ID = new Identifier(MaryMod2024.MOD_ID, "earn_money");
    public static final Identifier SPEND_MONEY_ID = new Identifier(MaryMod2024.MOD_ID, "spend_money");
    public static final Identifier REQUEST_MONEY_ID = new Identifier(MaryMod2024.MOD_ID, "request_money");
    public static final Identifier SYNC_UPGRADE_ID = new Identifier(MaryMod2024.MOD_ID, "sync_upgrade");
    public static final Identifier REQUEST_UPGRADE_ID = new Identifier(MaryMod2024.MOD_ID, "sync_upgrade");

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