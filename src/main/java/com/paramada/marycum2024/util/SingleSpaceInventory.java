package com.paramada.marycum2024.util;

import com.paramada.marycum2024.blocks.bases.SingleSpaceBlockEntity;
import com.paramada.marycum2024.blocks.custom.entities.EfigyBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class SingleSpaceInventory<T extends SingleSpaceBlockEntity> implements Inventory {

    private final T blockEntity;

    public SingleSpaceInventory(T blockEntity) {
        this.blockEntity = blockEntity;
    }
    public int size() {
        return 1;
    }

    public boolean isEmpty() {
        return !blockEntity.hasItem();
    }

    public ItemStack getStack(int slot) {
        return slot == 0 ? blockEntity.getItem() : ItemStack.EMPTY;
    }

    public ItemStack removeStack(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    public ItemStack removeStack(int slot) {
        return ItemStack.EMPTY;
    }

    public void setStack(int slot, ItemStack stack) {
    }

    public int getMaxCountPerStack() {
        return 1;
    }

    public void markDirty() {
        blockEntity.markDirty();
    }

    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(blockEntity, player) && blockEntity.hasItem();
    }

    public boolean isValid(int slot, ItemStack stack) {
        return false;
    }

    public void clear() {
    }
}
