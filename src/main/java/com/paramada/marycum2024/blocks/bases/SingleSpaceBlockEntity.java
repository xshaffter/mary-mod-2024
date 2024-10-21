package com.paramada.marycum2024.blocks.bases;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class SingleSpaceBlockEntity extends BlockEntity {
    protected ItemStack item = ItemStack.EMPTY;

    public SingleSpaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public boolean hasItem() {
        return !this.item.isEmpty();
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
        this.markDirty();
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("Item", 10)) {
            this.item = ItemStack.fromNbt(nbt.getCompound("Item"));
        } else {
            this.item = ItemStack.EMPTY;
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.getItem().isEmpty()) {
            nbt.put("Item", this.getItem().writeNbt(new NbtCompound()));
        }

    }
}
