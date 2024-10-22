package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.events.CustomExplosion;
import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.util.CooldownManager;
import com.paramada.marycum2024.util.LivingEntityBridge;
import net.fabricmc.api.EnvType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Unique
    private final CooldownManager<CustomExplosion> explosionContainer = new CooldownManager<>();

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    protected void onTick(CallbackInfo ci) {
        if (!this.explosionContainer.isAlive()) {
            if (this.getAttackCooldownProgress(0.5f) > 0.9f) {
                explosionContainer.revive();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "canFoodHeal", cancellable = true)
    private void onFoodHeal(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(ModEffects.ZOMBIEFICATION) || this.hasStatusEffect(ModEffects.VAMPIRISM)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "attack")
    private void overrideCooldownBehavior(Entity target, CallbackInfo ci) {
        var player = (PlayerEntity) (LivingEntity) this;
        var trinketComponent = LivingEntityBridge.getTrinketComponent(player);
        if (trinketComponent.isEquipped(ItemManager.MICROPHONE_TRINKET)) {
            var world = this.getWorld();
            explosionContainer.put(new CustomExplosion(world, player, target, 5));
        }
    }

    @Inject(at = @At("RETURN"), method = "attack")
    private void atEndOfAttack(Entity target, CallbackInfo ci) {
        if (explosionContainer.hasValue()) {
            explosionContainer.perform();
        }
    }
}
