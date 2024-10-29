package com.paramada.marycum2024.mixins;

import com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.model.Perspective;
import com.github.exopandora.shouldersurfing.client.EntityHelper;
import com.github.exopandora.shouldersurfing.client.ShoulderSurfingCamera;
import com.github.exopandora.shouldersurfing.client.ShoulderSurfingImpl;
import com.github.exopandora.shouldersurfing.math.Vec2f;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShoulderSurfingImpl.class)
public abstract class ShoulderSurfingMixin implements IShoulderSurfing {

    @Shadow(remap = false)
    private float playerXRotO;

    @Shadow(remap = false)
    private float playerYRotO;

    @Shadow(remap = false)
    private boolean updatePlayerRotations;

    @Shadow(remap = false) public abstract ShoulderSurfingCamera getCamera();

    @Shadow(remap = false) public abstract void changePerspective(Perspective perspective);

    @Inject(method = "lookAtTarget", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void maryblog2024$lookAtTarget(ClientPlayerEntity player, MinecraftClient minecraft, CallbackInfo ci) {
        var soulsCamera = PlayerEntityBridge.asCameraComponent(player);
        if (soulsCamera.maryCum2024$hasLockedTarget()) {
            var target = soulsCamera.maryCum2024$getLockedTarget();

            this.playerXRotO = player.getPitch();
            this.playerYRotO = player.getYaw();
            this.updatePlayerRotations = true;

            EntityHelper.lookAtTarget(player, target.getEyePos());
            ci.cancel();
        }
    }
    @Inject(method = "tick", at = @At(value = "RETURN"), remap = false)
    private void maryblog2024$tick(CallbackInfo ci) {
        var player = MinecraftClient.getInstance().player;
        var soulsCamera = PlayerEntityBridge.asCameraComponent(player);
        if (soulsCamera != null && soulsCamera.maryCum2024$hasLockedTarget()) {
            var target = soulsCamera.maryCum2024$getLockedTarget();

            this.playerXRotO = player.getPitch();
            this.playerYRotO = player.getYaw();
            this.updatePlayerRotations = true;

            EntityHelper.lookAtTarget(player, target.getEyePos());
        }
    }
}
