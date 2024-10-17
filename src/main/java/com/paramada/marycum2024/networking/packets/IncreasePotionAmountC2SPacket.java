package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.networking.NetworkManager;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class IncreasePotionAmountC2SPacket {
    public static <T extends FabricPacket> void receive(MinecraftServer server, ServerPlayerEntity player,
                                                        ServerPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        var stack = player.getInventory().main.stream().filter(itemStack -> itemStack.isOf(ItemManager.ESTUS)).findFirst();

        if (stack.isPresent()) {
            var itemstack = stack.get();
            var nbt = itemstack.getOrCreateNbt();
            var lastEnhance = nbt.getInt("enhance");
            nbt.putInt("enhance", lastEnhance + 1);

            var newBuf = PacketByteBufs.create();
            newBuf.writeInt(lastEnhance + 1);
            ServerPlayNetworking.send(player, NetworkManager.SYNC_DURABILITY_ID, newBuf);
        }
    }
}
