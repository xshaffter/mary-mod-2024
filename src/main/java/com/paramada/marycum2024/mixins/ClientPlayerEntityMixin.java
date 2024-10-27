package com.paramada.marycum2024.mixins;

import com.github.exopandora.shouldersurfing.api.client.ShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.model.Perspective;
import com.mojang.authlib.GameProfile;
import com.paramada.marycum2024.util.animator.IExampleAnimatedPlayer;
import com.paramada.marycum2024.util.souls.ISoulsPlayerCamera;
import com.paramada.marycum2024.util.souls.ISoulsPlayerSelector;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity implements IExampleAnimatedPlayer, ISoulsPlayerCamera, ISoulsPlayerSelector {

    @Unique
    private LivingEntity lockedTarget = null;

    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

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

        if (lockedTarget != null && lockedTarget.isDead()) {
            lockedTarget = null;
        }

        if (!shoulderSurfing.isShoulderSurfing() && !this.isCreative() && !this.isSpectator()) {
            shoulderSurfing.changePerspective(Perspective.SHOULDER_SURFING);
        }
        if (shoulderSurfing.isShoulderSurfing() && maryCum2024$hasLockedTarget()) {

            var targetPos = lockedTarget.getBoundingBox().getCenter();
            var directionVec = targetPos.subtract(this.getPos()).normalize();
            float newYaw = getNewDeltaYaw(directionVec);
            float newPitch = getNewDeltaPitch(targetPos);
            this.setYaw(newYaw);
            this.setPitch(newPitch);
            var camera = shoulderSurfing.getCamera();
            camera.setYRot(newYaw);

        }
    }

    @Unique
    private float getNewDeltaYaw(Vec3d directionVec) {
        double angle = Math.atan2(-directionVec.x, directionVec.z) * 180 / Math.PI;

        float adjustedPrevYaw = this.getYaw();
        if (Math.abs(angle - adjustedPrevYaw) > 180) {
            if (adjustedPrevYaw > angle) {
                angle += 360;
            } else if (adjustedPrevYaw < angle) {
                angle -= 360;
            }
        }

        double newDelta = MathHelper.lerp(1, adjustedPrevYaw, angle);
        if (newDelta > 180) {
            newDelta -= 360;
        }
        if (newDelta < -180) {
            newDelta += 360;
        }
        return (float) newDelta;
    }
    @Unique
    private float getNewDeltaPitch(Vec3d directionVec) {
        double dX = this.getX() - directionVec.getX();
        double dY = this.getEyeY() - directionVec.getY();
        double dZ = this.getZ() - directionVec.getZ();
        return (float) Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY);
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
            var networkHandler = MinecraftClient.getInstance().getNetworkHandler();
            if (networkHandler != null) {
                lockedTarget = living;
            }
        }
    }
}
