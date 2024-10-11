package com.paramada.marycum2024.mixins.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public interface InventoryMixin {

    @Inject(at = @At("HEAD"), method = "getMaxCountPerStack", cancellable = true)
    default void getMaxCount(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(999);
    }
}
