package com.paramada.marycum2024.mixins.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory {

    @Shadow
    public int selectedSlot;

    @Shadow
    @Final
    public PlayerEntity player;

    @Shadow @Final public DefaultedList<ItemStack> main;

    @Shadow @Final public DefaultedList<ItemStack> armor;

    @Shadow @Final public DefaultedList<ItemStack> offHand;

    @Inject(at = @At("HEAD"), method = "dropSelectedItem", cancellable = true)
    private void onDrop(boolean entireStack, CallbackInfoReturnable<ItemStack> cir) {
        if (this.player.isCreative()) {
            return;
        }
        var stack = this.getStack(this.selectedSlot);
        var nbt = stack.getNbt();
        if (nbt != null && nbt.getBoolean("NoDrop")) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

    @Inject(at = @At("HEAD"), method = "readNbt", cancellable = true)
    public void readNbt(NbtList nbtList, CallbackInfo ci) {
        this.main.clear();
        this.armor.clear();
        this.offHand.clear();

        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
            itemStack.setCount(nbtCompound.getInt("Count"));
            if (!itemStack.isEmpty()) {
                if (j >= 0 && j < this.main.size()) {
                    this.main.set(j, itemStack);
                } else if (j >= 100 && j < this.armor.size() + 100) {
                    this.armor.set(j - 100, itemStack);
                } else if (j >= 150 && j < this.offHand.size() + 150) {
                    this.offHand.set(j - 150, itemStack);
                }
            }
        }
        ci.cancel();
    }
}
