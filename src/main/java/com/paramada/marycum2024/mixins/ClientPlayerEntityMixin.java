package com.paramada.marycum2024.mixins;

import com.github.exopandora.shouldersurfing.api.client.ShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.model.Perspective;
import com.mojang.authlib.GameProfile;
import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.IExampleAnimatedPlayer;
import com.paramada.marycum2024.util.ISoulsPlayerCamera;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity implements IExampleAnimatedPlayer, ISoulsPlayerCamera {

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
            float newDelta = getNewDelta(directionVec);
            this.setYaw(newDelta);

        }
    }

    @Unique
    private float getNewDelta(Vec3d directionVec) {
        double angle = Math.atan2(-directionVec.x, directionVec.z) * 180 / Math.PI;

        float adjustedPrevYaw = this.prevYaw;
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
            var networkHandler = MinecraftClient.getInstance().getNetworkHandler();
            if (networkHandler != null) {
                var buf = PacketByteBufs.create();
                buf.writeInt(lockedTarget.getId());
                ClientPlayNetworking.send(NetworkManager.REMOVE_LOCK_TARGET_ID, buf);
            }
            lockedTarget = null;
        } else if (target instanceof LivingEntity living) {
            var networkHandler = MinecraftClient.getInstance().getNetworkHandler();
            if (networkHandler != null) {
                lockedTarget = living;
                var buf = PacketByteBufs.create();
                buf.writeInt(lockedTarget.getId());
                ClientPlayNetworking.send(NetworkManager.LOCK_TARGET_ID, buf);
            }
        }
    }
}
