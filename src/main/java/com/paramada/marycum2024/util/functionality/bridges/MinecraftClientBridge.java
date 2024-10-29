package com.paramada.marycum2024.util.functionality.bridges;

import com.github.exopandora.shouldersurfing.api.client.ShoulderSurfing;
import com.github.exopandora.shouldersurfing.client.ShoulderSurfingImpl;
import com.paramada.marycum2024.math.Rect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;

import java.util.List;
import java.util.function.Predicate;

public final class MinecraftClientBridge {
    private static final Predicate<LivingEntity> ENTITY_PREDICATE = entity -> entity.isAlive() && entity.isAttackable();
    public static List<LivingEntity> getPossibleTargets(MinecraftClient client) {
        var shoulderSurfing = (ShoulderSurfingImpl) ShoulderSurfing.getInstance();
        var camera = shoulderSurfing.getCamera();
        var player = client.player;
        var world = client.world;

        if (camera != null && player != null && world != null) {
            var maxDistance = 10d;

            final TargetPredicate ENEMY_CONDITION = TargetPredicate.createAttackable().setBaseMaxDistance(maxDistance).setPredicate(ENTITY_PREDICATE);
            List<LivingEntity> entities = player.getWorld()
                    .getTargets(LivingEntity.class, ENEMY_CONDITION, player, player.getBoundingBox().expand(maxDistance)).stream().filter(player::canSee).toList();


            return entities;

        }
        return List.of();
    }

    public static LivingEntity getClossestTarget(List<LivingEntity> targets) {
        var client = MinecraftClient.getInstance();
        var player = client.player;
        LivingEntity result = targets.isEmpty() ? null : targets.get(0);
        var shoulderSurfing = (ShoulderSurfingImpl) ShoulderSurfing.getInstance();
        var camera = shoulderSurfing.getCamera();
        if (camera != null && player != null) {
            var pitch = camera.getXRot(); // vertical facing
            var yaw = camera.getYRot(); // horizontal facing
            var eyeCenter = player.getPos();
            var lookingAt = new Rect(eyeCenter, yaw, pitch);

            for (LivingEntity target : targets) {
                if (result == null || target.squaredDistanceTo(player) < result.squaredDistanceTo(player) && lookingAt.distanceToVector(target.getPos()) < lookingAt.distanceToVector(result.getPos())) {
                    result = target;
                }
            }
        }

        return result;
    }
}
