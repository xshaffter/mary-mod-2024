package com.paramada.marycum2024.mixins.inventory;

import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.items.custom.IMaryItem;
import com.paramada.marycum2024.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public abstract class ClientItemStackMixin implements FabricItemStack {
    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract NbtCompound getOrCreateNbt();

    @Shadow
    public abstract boolean isOf(Item item);

    @Shadow
    private @Nullable Entity holder;

    @Environment(EnvType.CLIENT)
    @Inject(method = "copy", at = @At("HEAD"))
    private void onCopy(CallbackInfoReturnable<ItemStack> cir) {
        if (this.holder == null) {
            return;
        }
        if (!this.holder.getWorld().isClient) {
            return;
        }
        var client = MinecraftClient.getInstance();
        var network = client.getNetworkHandler();
        if (network != null && !network.getPlayerList().isEmpty()) {
            ClientPlayNetworking.send(NetworkManager.REQUEST_MONEY_ID, PacketByteBufs.create());
        }
    }
}
