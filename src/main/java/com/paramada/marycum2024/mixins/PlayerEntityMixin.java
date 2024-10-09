package com.paramada.marycum2024.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class PlayerEntityMixin implements EntityLike {
    @Shadow
    public abstract boolean isOnGround();

    @Shadow
    public abstract boolean isTouchingWater();

    @Inject(method = "isSprinting", at = @At(value = "HEAD"), cancellable = true)
    private void noSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (this.isOnGround() || !isTouchingWater()) {
            cir.setReturnValue(false);
        }
    }
}
