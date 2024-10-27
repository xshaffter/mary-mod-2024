package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.items.ItemManager;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SyncUpgradeS2CPacket {
    public static <T extends FabricPacket> void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        var player = client.player;
        if (player == null) {
            return;
        }
        var stack = player.getInventory().main.stream().filter(itemStack -> itemStack.isOf(ItemManager.ESTUS)).findFirst();

        if (stack.isPresent()) {
            var itemstack = stack.get();
            var nbt = itemstack.getOrCreateNbt();

            var durabiility = buf.readInt();
            nbt.putInt("upgrade", durabiility);
        }
    }
}
