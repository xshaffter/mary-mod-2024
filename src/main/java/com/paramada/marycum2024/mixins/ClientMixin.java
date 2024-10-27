package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.util.functionality.IItemUsagePublisher;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class ClientMixin implements IItemUsagePublisher {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow protected abstract void doItemUse();

    @Shadow private int itemUseCooldown;

    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void hasOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        var cameraComponent = PlayerEntityBridge.asCameraComponent(this.player);
        if (cameraComponent != null) {
            if (cameraComponent.maryCum2024$getLockedTarget() == entity) {
                cir.setReturnValue(true);
            }
        }
    }

    @Override
    public void maryCum2024$doItemUse() {
        this.doItemUse();
    }

    @Override
    public int maryCum2024$getItemCooldown() {
        return this.itemUseCooldown;
    }
}
