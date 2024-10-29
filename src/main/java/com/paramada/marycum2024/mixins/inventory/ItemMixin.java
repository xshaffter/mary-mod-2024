package com.paramada.marycum2024.mixins.inventory;

import com.paramada.marycum2024.util.functionality.bridges.LivingEntityBridge;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(at = @At("HEAD"), method = "getItemBarStep", cancellable = true)
    public void getItemBarStep(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Math.round(13.0F - (float)stack.getDamage() * 13.0F / (float)stack.getMaxDamage()));
    }

    @Inject(at = @At("HEAD"), method = "getItemBarColor", cancellable = true)
    public void getItemBarColor(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        float f = Math.max(0.0F, ((float)stack.getMaxDamage() - (float)stack.getDamage()) / (float)stack.getMaxDamage());
        cir.setReturnValue(MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F));
    }
}
