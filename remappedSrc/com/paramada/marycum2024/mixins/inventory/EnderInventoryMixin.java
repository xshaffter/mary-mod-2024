package com.paramada.marycum2024.mixins.inventory;

import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderChestInventory.class)
public abstract class EnderInventoryMixin implements Inventory {

    @Inject(at = @At("HEAD"), method = "readNbtList", cancellable = true)
    public void readNbtList(NbtList nbtList, CallbackInfo ci) {
        for (int i = 0; i < this.size(); i++) {
            this.setStack(i, ItemStack.EMPTY);
        }

        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int slot = nbtCompound.getByte("Slot") & 255;
            if (slot >= 0 && slot < this.size()) {
                var stack = ItemStack.fromNbt(nbtCompound);
                System.out.println(nbtCompound);
                stack.setCount(nbtCompound.getInt("Count"));
                this.setStack(slot, stack);
            }
        }

        ci.cancel();
    }
}
