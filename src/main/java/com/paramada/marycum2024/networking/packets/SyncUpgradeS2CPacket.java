package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.networking.packets.payloads.PotionUpgradeDataPayload;
import com.paramada.marycum2024.util.LivingEntityBridge;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SyncUpgradeS2CPacket {
    public static void receive(PotionUpgradeDataPayload potionUpgradeDataPayload, ClientPlayNetworking.Context context) {
        LivingEntityBridge.getPersistentData(context.player()).putInt("upgrade", potionUpgradeDataPayload.getPotionUpgrade());
    }
}
