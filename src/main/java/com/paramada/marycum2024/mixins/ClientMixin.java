package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class ClientMixin {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void hasOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        var cameraComponent = PlayerEntityBridge.asCameraComponent(this.player);
        if (cameraComponent != null) {
            if (cameraComponent.maryCum2024$getLockedTarget() == entity) {
                cir.setReturnValue(true);
            }
        }
    }
}
