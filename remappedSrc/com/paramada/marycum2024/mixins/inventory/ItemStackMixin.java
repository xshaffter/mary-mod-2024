package com.paramada.marycum2024.mixins.inventory;

import com.paramada.marycum2024.items.custom.IMaryItem;
import com.paramada.marycum2024.networking.NetworkManager;
import io.netty.util.internal.ObjectUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements FabricItemStack {
    @Shadow
    public abstract Item getItem();

    @Shadow
    private @Nullable NbtCompound nbt;

    @Shadow
    private int count;


    @Shadow public abstract NbtCompound getOrCreateNbt();

    @Inject(method = "getNbt", at = @At("HEAD"))
    private void defaultNBT(CallbackInfoReturnable<NbtCompound> cir) {
        if (this.getItem() instanceof IMaryItem) {
            this.getOrCreateNbt();
            if (this.nbt != null) {
                this.nbt.putBoolean("NoDrop", true);
                if (!this.nbt.contains("durability")) {
                    this.nbt.putInt("durability", 1);
                }
            }
        }
    }


    @Inject(method = "copy", at = @At("HEAD"))
    private void onCopy(CallbackInfoReturnable<ItemStack> cir) {
        var network = MinecraftClient.getInstance().getNetworkHandler();
        if (network != null && network.getPlayerList().size() > 0) {
            ClientPlayNetworking.send(NetworkManager.REQUEST_MONEY_ID, PacketByteBufs.create());
        }
    }

    @Inject(method = "writeNbt", at = @At("HEAD"), cancellable = true)
    public void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        Identifier identifier = Registries.ITEM.getId(this.getItem());
        nbt.putString("id", Objects.requireNonNullElse(identifier.toString(), "minecraft:air"));
        nbt.putInt("Count", this.count);
        if (this.nbt != null) {
            nbt.put("tag", this.nbt.copy());
        }

        cir.setReturnValue(nbt);
    }
}
