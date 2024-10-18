package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.LivingEntityBridge;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import com.paramada.marycum2024.util.UpgradeManager;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class LevelUpC2SPacket {
    public static <T extends FabricPacket> void receive(MinecraftServer server, ServerPlayerEntity player,
                                                        ServerPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        var newBuf = PacketByteBufs.create();
        var currentLevel = LivingEntityBridge.getPersistentData(player).getInt("level");
        var newLevel = currentLevel + 1;

        var oldModifiers = UpgradeManager.getLevelModifiers(currentLevel);
        var newModifiers = UpgradeManager.getLevelModifiers(newLevel);

        player.getAttributes().removeModifiers(oldModifiers.modifiers());
        player.getAttributes().addTemporaryModifiers(newModifiers.modifiers());

        LivingEntityBridge.getPersistentData(player).putInt("level", newLevel);
        newBuf.writeInt(newLevel);
        ServerPlayNetworking.send(player, NetworkManager.SYNC_LEVEL_ID, newBuf);
    }
}
