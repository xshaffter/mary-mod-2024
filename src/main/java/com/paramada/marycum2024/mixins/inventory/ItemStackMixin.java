package com.paramada.marycum2024.mixins.inventory;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
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

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements FabricItemStack {
    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract NbtCompound getOrCreateNbt();

    @Shadow
    public abstract boolean isOf(Item item);

    @Shadow
    private @Nullable Entity holder;

    @Shadow private int count;

    @Shadow private @Nullable NbtCompound nbt;

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
