package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class SpendMoneyC2SPacket {
    public static <T extends FabricPacket> void receive(MinecraftServer server, ServerPlayerEntity player,
                                                        ServerPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        var price = buf.readInt();
        var enderchest = player.getEnderChestInventory();
        enderchest.removeItem(ItemManager.MARY_COIN, price);
        PlayerEntityBridge.spendMoney(player, price);
    }
}
