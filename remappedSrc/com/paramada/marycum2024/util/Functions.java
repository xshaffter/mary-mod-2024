package com.paramada.marycum2024.util;

import Z;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class Functions {
    public static boolean playerInBounds(ServerPlayerEntity player, Boundaries bounds) {

        var playerPos = player.getPos();
        // -107 -21 244
        // -99 -21 254
        var inX = playerPos.x >= bounds.point1.x && playerPos.x <= bounds.point2.x;
        var inY = playerPos.y >= bounds.point1.y && playerPos.y <= bounds.point2.y;
        var inZ = playerPos.z >= bounds.point1.z && playerPos.z <= bounds.point2.z;
        return inX && inY && inZ;
    }
}
