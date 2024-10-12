package com.paramada.marycum2024.mixins.inventory;

import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.items.custom.MaryCoinItem;
import com.paramada.marycum2024.networking.NetworkManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleInventory.class)
public abstract class SimpleInventoryMixin implements Inventory {

    @Shadow
    @Final
    public DefaultedList<ItemStack> stacks;

    @Inject(at = @At("HEAD"), method = "transfer", cancellable = true)
    private void onTransfer(ItemStack source, ItemStack target, CallbackInfo ci) {
        if (source.isOf(ItemManager.MARY_COIN)) {
            int i = target.getMaxCount();
            int j = Math.min(source.getCount(), i - target.getCount());
            if (j > 0) {
                target.increment(j);
                source.decrement(j);
                this.markDirty();
            }
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "setStack", cancellable = true)
    public void setStack(int slot, ItemStack stack, CallbackInfo ci) {
        this.stacks.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
        }

        this.markDirty();
        ci.cancel();
    }
}
