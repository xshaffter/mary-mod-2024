package com.paramada.marycum2024.mixins;

import com.github.exopandora.shouldersurfing.api.client.ShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.model.Perspective;
import com.mojang.authlib.GameProfile;
import com.paramada.marycum2024.util.animator.IExampleAnimatedPlayer;
import com.paramada.marycum2024.util.inventory.SpecialSlotManager;
import com.paramada.marycum2024.util.souls.ISoulsPlayerCamera;
import com.paramada.marycum2024.util.souls.ISoulsPlayerSelector;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements IExampleAnimatedPlayer, ISoulsPlayerCamera, ISoulsPlayerSelector {

    @Shadow
    public abstract boolean isCreative();

    @Shadow
    public abstract boolean isSpectator();

    @Unique
    public SpecialSlotManager itemSelectorManager = new SpecialSlotManager(23, 27);
    @Unique
    public SpecialSlotManager offHandSelectorManager = new SpecialSlotManager(10, 14);

    @Unique
    private LivingEntity lockedTarget = null;

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

        if (lockedTarget != null && lockedTarget.isDead()) {
            lockedTarget = null;
        }

        if (!shoulderSurfing.isShoulderSurfing() && !this.isCreative() && !this.isSpectator()) {
            shoulderSurfing.changePerspective(Perspective.SHOULDER_SURFING);
        }
    }

    @Override
    public ModifierLayer<IAnimation> maryCum2024$getModAnimation() {
        return modAnimationContainer;
    }

    @Override
    public LivingEntity maryCum2024$getLockedTarget() {
        return lockedTarget;
    }

    @Override
    public boolean maryCum2024$hasLockedTarget() {
        return lockedTarget != null;
    }

    @Override
    public void maryCum2024$setLockedTarget(Entity target) {
        if (target == null) {
            lockedTarget = null;
        } else if (target instanceof LivingEntity living) {
            lockedTarget = living;
        }
    }

    @Override
    public SpecialSlotManager maryCum2024$getItemSelector() {
        return itemSelectorManager;
    }

    @Override
    public SpecialSlotManager maryCum2024$getoffHandSelector() {
        return offHandSelectorManager;
    }
}
