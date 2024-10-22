package com.paramada.marycum2024.mixins;

import com.github.exopandora.shouldersurfing.api.client.ShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.model.Perspective;
import com.mojang.authlib.GameProfile;
import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.IExampleAnimatedPlayer;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements IExampleAnimatedPlayer {

    @Shadow
    public abstract boolean isCreative();

    @Shadow
    public abstract boolean isSpectator();

    @Unique
    private final ModifierLayer<IAnimation> modAnimationContainer = new ModifierLayer<>();

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(ClientWorld world, GameProfile profile, CallbackInfo ci) {
        var player = (AbstractClientPlayerEntity) (Object) this;
        var animation = PlayerAnimationAccess.getPlayerAnimLayer(player);
        animation.addAnimLayer(1000, modAnimationContainer);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void enforceShoulderSurfing(CallbackInfo ci) {
        var shoulderSurfing = ShoulderSurfing.getInstance();
        if (shoulderSurfing == null) {
            return;
        }
        if (!shoulderSurfing.isShoulderSurfing() && !this.isCreative() && !this.isSpectator()) {
            shoulderSurfing.changePerspective(Perspective.SHOULDER_SURFING);
        }
    }

    @Override
    public ModifierLayer<IAnimation> maryCum2024$getModAnimation() {
        return modAnimationContainer;
    }
}
