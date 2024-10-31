package com.paramada.marycum2024.mixins;

import com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.model.Perspective;
import com.github.exopandora.shouldersurfing.client.EntityHelper;
import com.github.exopandora.shouldersurfing.client.ShoulderSurfingCamera;
import com.github.exopandora.shouldersurfing.client.ShoulderSurfingImpl;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ShoulderSurfingImpl.class)
public abstract class ShoulderSurfingMixin implements IShoulderSurfing {

    @Shadow(remap = false)
    public abstract ShoulderSurfingCamera getCamera();

    @Shadow(remap = false)
    public abstract void changePerspective(Perspective perspective);

    @Inject(method = "tick", at = @At(value = "RETURN"), remap = false)
    private void maryblog2024$tick(CallbackInfo ci) {
        var soulsPlayer = PlayerEntityBridge.getCurrentSoulsPlayer();
        if (soulsPlayer != null && soulsPlayer.hasLockedTarget()) {
            var target = soulsPlayer.getLockedTarget();
            EntityHelper.lookAtTarget((ClientPlayerEntity) soulsPlayer.player, target.getEyePos());
        }
    }
}
