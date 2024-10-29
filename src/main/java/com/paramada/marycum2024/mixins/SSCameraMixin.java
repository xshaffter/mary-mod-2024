package com.paramada.marycum2024.mixins;

import com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.model.Perspective;
import com.github.exopandora.shouldersurfing.client.EntityHelper;
import com.github.exopandora.shouldersurfing.client.ShoulderSurfingCamera;
import com.github.exopandora.shouldersurfing.client.ShoulderSurfingImpl;
import com.paramada.marycum2024.math.Functions;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShoulderSurfingCamera.class)
public abstract class SSCameraMixin implements IShoulderSurfing {


    @Shadow(remap = false) private float yRot;

    @Shadow(remap = false) private float yRotOffset;

    @Shadow(remap = false) private float yRotOffsetO;

    @Inject(method = "turn", at = @At(
            value = "INVOKE",
            target = "Lcom/github/exopandora/shouldersurfing/config/Config$ClientConfig;isCameraDecoupled()Z",
            shift = At.Shift.AFTER
    ), remap = false)
    private void maryblog2024$turn(PlayerEntity player, double yRot, double xRot, CallbackInfoReturnable<Boolean> cir) {
        if (player.getWorld().isClient) {
            var client = MinecraftClient.getInstance();
            var soulsCamera = PlayerEntityBridge.asCameraComponent((ClientPlayerEntity) player);
            if (soulsCamera.maryCum2024$hasLockedTarget() && !client.isPaused()) {
                var target = soulsCamera.maryCum2024$getLockedTarget();
                var direction = target.getEyePos().subtract(player.getBlockPos().toCenterPos()).normalize();
                this.yRot = getNewDeltaYaw(1,this.yRot, direction);
            }
        }
    }

    @Inject(method = "setYRot", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void maryblog2024$setYaw(float yRot, CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        var player = client.player;
        var soulsCamera = PlayerEntityBridge.asCameraComponent(player);
        if (player != null && soulsCamera.maryCum2024$hasLockedTarget()) {
            float tickDelta = MinecraftClient.getInstance().getTickDelta();
            var target = soulsCamera.maryCum2024$getLockedTarget();
            var direction = target.getEyePos().subtract(player.getBlockPos().toCenterPos()).normalize();
            this.yRot = getNewDeltaYaw(tickDelta, this.yRot, direction);
            this.yRotOffset = 0.0F;
            this.yRotOffsetO = 0.0F;
            ci.cancel();
        }
    }

    @Unique
    private float getNewDeltaYaw(float tickDelta, float initialYaw, Vec3d directionVec) {
        float angle = (float) Functions.toDegrees(Math.atan2(-directionVec.x, directionVec.z));
        float adjustedPrevYaw = MathHelper.subtractAngles(angle, initialYaw);
        return MathHelper.wrapDegrees(MathHelper.lerpAngleDegrees(tickDelta, adjustedPrevYaw, angle));
    }
}
