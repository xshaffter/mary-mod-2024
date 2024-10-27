package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.networking.NetworkManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class SwapMainHandC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        var slot = buf.readVarInt();
        var use = buf.readBoolean();
        var mainHandStack = player.getMainHandStack();
        var selectedItemStack = handler.player.getInventory().getStack(slot);
        if (use && player.getItemCooldownManager().isCoolingDown(selectedItemStack.getItem())) {
            return;
        }
        handler.player.setStackInHand(Hand.MAIN_HAND, selectedItemStack);
        handler.player.getInventory().setStack(slot, mainHandStack);
        if (use) {
            ServerPlayNetworking.send(player, NetworkManager.START_USE_ITEM_ID, PacketByteBufs.create());
        }
    }
}
