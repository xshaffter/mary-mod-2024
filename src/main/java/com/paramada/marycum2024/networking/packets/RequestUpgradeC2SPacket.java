package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.networking.packets.payloads.PotionUpgradeDataPayload;
import com.paramada.marycum2024.util.LivingEntityBridge;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public class RequestUpgradeC2SPacket {
    public static void receive(PotionUpgradeDataPayload customPayload, ServerPlayNetworking.Context context) {
        var upgrade = LivingEntityBridge.getPersistentData(context.player()).getInt("upgrade");
        var newPayload = new PotionUpgradeDataPayload(upgrade, NetworkManager.SYNC_UPGRADE_ID);
        ServerPlayNetworking.send(context.player(), newPayload);
    }
}
