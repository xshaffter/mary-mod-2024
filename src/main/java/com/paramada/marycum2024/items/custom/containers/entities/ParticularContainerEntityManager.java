package com.paramada.marycum2024.items.custom.containers.entities;

import com.paramada.marycum2024.blocks.custom.entities.ImplementedInventory;
import com.paramada.marycum2024.screens.handlers.ParticularContainerScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class ParticularContainerEntityManager implements NamedScreenHandlerFactory, ImplementedInventory {

    private final ItemStack stack;
    private final int rows;
    private final int columns;
    private final Predicate<ItemStack> predicate;
    private final DefaultedList<ItemStack> inventory;
    public ParticularContainerEntityManager(ItemStack stack, int rows, int columns, Predicate<ItemStack> predicate) {
        this.stack = stack;
        this.rows = rows;
        this.columns = columns;
        this.predicate = predicate;
        var nbt = stack.getOrCreateNbt();
        this.inventory = DefaultedList.ofSize(rows * columns, ItemStack.EMPTY);

        readNbt(nbt);

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return stack.getName();
    }

    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);

    }

    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ParticularContainerScreenHandler(syncId, playerInventory, stack, this, predicate);
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public ItemStack getStack() {
        return stack;
    }
}
