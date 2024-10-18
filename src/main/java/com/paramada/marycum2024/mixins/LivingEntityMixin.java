package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.apache.commons.lang3.ObjectUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IEntityDataSaver {

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    public abstract void heal(float amount);

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow
    public abstract boolean isAlive();

    @Shadow
    public abstract boolean isUndead();

    @Shadow
    public abstract float getMaxHealth();

    @Unique
    private NbtCompound persistentData;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    protected void onTick(CallbackInfo ci) {
        //noinspection ConstantValue
        if (!(((Entity) this) instanceof ServerPlayerEntity player)) {
            return;
        }
    }

    @Inject(at = @At("HEAD"), method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", cancellable = true)
    private void dontTargetZombified(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (this.isUndead() && target.hasStatusEffect(ModEffects.ZOMBIEFICATION)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "damage")
    private void onGetDamage(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        var source = damageSource.getSource();
        if (source != null && !this.isUndead() && this.isAttackable() && source instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.VAMPIRISM)) {
                StatusEffectInstance effect = player.getStatusEffect(ModEffects.VAMPIRISM);
                if (effect != null) {
                    int amplifier = effect.getAmplifier();
                    player.heal(amount / (4f / (amplifier + 1)));
                }
            }
        }
    }


    @Inject(at = @At("HEAD"), method = "onKilledBy")
    private void onGetKilled(LivingEntity source, CallbackInfo ci) {
        if (source instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.ZOMBIEFICATION)) {
                StatusEffectInstance effect = player.getStatusEffect(ModEffects.ZOMBIEFICATION);
                if (effect != null) {
                    int amplifier = effect.getAmplifier();
                    player.heal(this.getMaxHealth() / (4f / (amplifier + 1)));
                }
            }
        }
        if (((Entity) this) instanceof PlayerEntity player) {
            player.getEnderChestInventory().clear();
        }
    }


    @Override
    public NbtCompound maryCum2024$getPersistentData() {
        if (persistentData == null) {
            persistentData = new NbtCompound();
        }
        if (!persistentData.contains("upgrade")) {
            persistentData.putInt("upgrade", 0);
        }
        if (!persistentData.contains("level")) {
            persistentData.putInt("level", 1);
        }
        return persistentData;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    protected void writeNbt(NbtCompound nbt, CallbackInfo info) {
        if (persistentData != null) {
            nbt.put("%s.data".formatted(MaryMod2024.MOD_ID), persistentData);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    protected void injectedReadNBT(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("%s.data".formatted(MaryMod2024.MOD_ID), NbtElement.COMPOUND_TYPE)) {
            persistentData = nbt.getCompound("%s.data".formatted(MaryMod2024.MOD_ID));
        }
    }

}