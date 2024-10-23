package com.paramada.marycum2024.networking.packets;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class AimToTargetC2SPacket {
    public static <T extends FabricPacket> void receive(MinecraftServer server, ServerPlayerEntity player,
                                                        ServerPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {

        var targetPos = buf.readBlockPos().toCenterPos();
        var eyePos = player.getEyePos();

        double dX = targetPos.getX() - eyePos.getX();
        double dY = targetPos.getY() - eyePos.getY();
        double dZ = targetPos.getZ() - eyePos.getZ();

        double yaw = Math.atan2(dZ, dX);

        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        player.setPitch((float) pitch);
        player.setYaw((float) yaw);
    }
}
