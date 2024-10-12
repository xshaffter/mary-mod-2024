package com.paramada.marycum2024.networking.packets.payloads;

import net.minecraft.network.packet.CustomPayload;

public class MoneyDataPayLoad implements CustomPayload {
    private final int moneyAmount;
    private final Id<MoneyDataPayLoad> syncMoneyId;

    public MoneyDataPayLoad(int moneyAmount, Id<MoneyDataPayLoad> syncMoneyId) {
        this.moneyAmount = moneyAmount;
        this.syncMoneyId = syncMoneyId;
    }

    public MoneyDataPayLoad(Id<MoneyDataPayLoad> syncMoneyId) {
        this.moneyAmount = 0;
        this.syncMoneyId = syncMoneyId;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return syncMoneyId;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }
}
