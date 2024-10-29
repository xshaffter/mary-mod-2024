package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.util.souls.ISoulsPlayerCamera;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final
    MinecraftClient client;

    @Inject(method = "updateTargetedEntity", at = @At("HEAD"), cancellable = true)
    private void overrideCrosshairTarget(float tickDelta, CallbackInfo ci) {
        if(this.client.player != null) {
            var camera = ((ISoulsPlayerCamera) this.client.player);
            if (camera.maryCum2024$hasLockedTarget()) {
                client.crosshairTarget = new EntityHitResult(camera.maryCum2024$getLockedTarget());
                client.targetedEntity = camera.maryCum2024$getLockedTarget();
                ci.cancel();
            }
            if (!client.player.isSpectator() && !client.player.isCreative()) {
                client.crosshairTarget = BlockHitResult.createMissed(client.player.getPos(), client.player.getHorizontalFacing(), client.player.getBlockPos());
                ci.cancel();
            }
        }
    }
}
