package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.items.trinkets.bases.RibbonTrinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends LivingEntity {


    protected ServerPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "canFoodHeal", cancellable = true)
    private void onFoodHeal(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(ModEffects.ZOMBIEFICATION) || this.hasStatusEffect(ModEffects.VAMPIRISM)) {
            cir.setReturnValue(false);
        }
    }

}
