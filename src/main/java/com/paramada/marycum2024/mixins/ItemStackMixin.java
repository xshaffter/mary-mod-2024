package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.items.custom.ReusablePotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @Shadow private @Nullable NbtCompound nbt;

    @Shadow public abstract boolean isOf(Item item);

    @Inject(method = "getNbt", at = @At("HEAD"))
    private void defaultNBT(CallbackInfoReturnable<NbtCompound> cir) {
        if (this.getItem() instanceof ReusablePotion || this.isOf(ItemManager.ESTUS_SHARD) || this.isOf(ItemManager.HEALING_FRUIT)) {
            if (this.nbt != null) {
                this.nbt.putBoolean("NoDrop", true);
                if (!this.nbt.contains("durability")) {
                    this.nbt.putInt("durability", 1);
                }
            }
        }
    }
}
