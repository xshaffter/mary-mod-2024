package com.paramada.marycum2024.mixins.inventory;

import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
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

@Environment(EnvType.CLIENT)
@Mixin(PlayerInventory.class)
public abstract class ClientPlayerInventoryMixin implements Inventory {

    @Shadow
    @Final
    public PlayerEntity player;

    @Shadow
    @Final
    public DefaultedList<ItemStack> main;

    @Environment(EnvType.CLIENT)
    @Inject(at = @At("HEAD"), method = "insertStack(Lnet/minecraft/item/ItemStack;)Z", cancellable = true)
    private void onPickup(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(ItemManager.MARY_COIN) && this.player.getWorld().isClient) {
            ClientPlayNetworking.send(NetworkManager.REQUEST_MONEY_ID, PacketByteBufs.create());
            this.player.getEnderChestInventory().addStack(stack);
            stack.setCount(0);
            cir.setReturnValue(true);
        }
    }
}
