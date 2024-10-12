package com.paramada.marycum2024.networking.packets.payloads;

import net.minecraft.network.packet.CustomPayload;

public class PotionUpgradeDataPayload implements CustomPayload {
    private final int potionUpgrade;
    private final Id<PotionUpgradeDataPayload> syncUpgradeId;

    public PotionUpgradeDataPayload(int potionUpgrade, Id<PotionUpgradeDataPayload> syncUpgradeId) {
        this.potionUpgrade = potionUpgrade;
        this.syncUpgradeId = syncUpgradeId;
    }

    public PotionUpgradeDataPayload(Id<PotionUpgradeDataPayload> syncUpgradeId) {
        this.potionUpgrade = 0;
        this.syncUpgradeId = syncUpgradeId;
    }

    @Override
    public Id<PotionUpgradeDataPayload> getId() {
        return syncUpgradeId;
    }

    public int getPotionUpgrade() {
        return potionUpgrade;
    }
}
