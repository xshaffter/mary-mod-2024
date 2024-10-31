package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.GlfwUtil;
import net.minecraft.client.util.SmoothUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MouseMixin {


    @Shadow
    private double cursorDeltaY;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private SmoothUtil cursorYSmoother;

    @Inject(at = @At("HEAD"), method = "updateMouse", cancellable = true)
    public void maryblog2024$updateMouse(CallbackInfo ci) {
        var soulsPlayer = PlayerEntityBridge.getCurrentSoulsPlayer();
        if (soulsPlayer != null && soulsPlayer.hasLockedTarget()) {
            double sensibility = this.client.options.getMouseSensitivity().getValue() * 0.6F + 0.2F;
            double qubedSens = sensibility * sensibility * sensibility;
            double sensFactor = qubedSens * 8.0;
            this.cursorYSmoother.clear();

            double l = this.cursorDeltaY * sensFactor;
            this.cursorDeltaY = 0.0;


            soulsPlayer.player.changeLookDirection(0, l);
            ci.cancel();
        }
    }
}
