package com.paramada.marycum2024.screens.handlers;

import com.paramada.marycum2024.events.SoundManager;
import com.paramada.marycum2024.items.custom.containers.ParticularContainerItem;
import com.paramada.marycum2024.items.custom.containers.entities.ParticularContainerEntityManager;
import com.paramada.marycum2024.screens.ParticularContainerScreen;
import com.paramada.marycum2024.screens.components.PredicateSlot;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Predicate;

import static com.paramada.marycum2024.screens.ParticularContainerScreen.PLAYER_INV_HEIGHT;
import static com.paramada.marycum2024.screens.ParticularContainerScreen.SUPERIOR_HEIGHT;

public class ParticularContainerScreenHandler extends ScreenHandler {
    private final PlayerInventory playerInventory;
    public static final int SLOT_SIZE = 18;
    private final ParticularContainerEntityManager inventory;
    private final int columns;
    private final int rows;
    private final ItemStack stack;


    public ParticularContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(syncId, playerInventory, ((ParticularContainerEntityManager) inventory).getStack(), ((ParticularContainerEntityManager) inventory), itemStack -> true);

    }

    public ParticularContainerScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack stack, ParticularContainerEntityManager inventory, Predicate<ItemStack> predicate) {
        super(ModScreenHandlers.PARTICULAR_SCREEN_HANDLER, syncId);
        this.stack = stack;
        this.rows = inventory.getRows();
        this.columns = inventory.getColumns();
        this.inventory = inventory;
        this.playerInventory = playerInventory;

        checkSize(inventory, rows * columns);
        inventory.onOpen(playerInventory.player);

        int guiCalculatedContainerHeight = rows * ParticularContainerScreenHandler.SLOT_SIZE;

        int guiSlotsWidth = columns * ParticularContainerScreenHandler.SLOT_SIZE;

        int width = ParticularContainerScreen.INVENTORY_WIDTH;

        int guiStartSlotX = (width - guiSlotsWidth) / 2 + 1;
        int guiStartSlotY = SUPERIOR_HEIGHT + 1;
        int PLAYER_INV_VGAP = 7;
        int HOTBAR_VGAP = 4;

        //Add container slots
        for (int invRow = 0; invRow < this.rows; invRow++) {
            for (int invCol = 0; invCol < columns; invCol++) {
                this.addSlot(new PredicateSlot(inventory, invCol + invRow * columns, guiStartSlotX + invCol * SLOT_SIZE, guiStartSlotY + invRow * SLOT_SIZE, predicate));
            }
        }

        //Add player main inventory
        for (int plInvRow = 0; plInvRow < 3; plInvRow++) {
            for (int plInvCol = 0; plInvCol < 9; plInvCol++) {
                this.addSlot(new Slot(playerInventory, plInvCol + plInvRow * 9 + 9, 8 + plInvCol * SLOT_SIZE, PLAYER_INV_VGAP + guiStartSlotY + guiCalculatedContainerHeight + plInvRow * SLOT_SIZE));
            }
        }

        //Add player hotbar inventory
        for (int hotbarIndex = 0; hotbarIndex < 9; hotbarIndex++) {
            this.addSlot(new Slot(playerInventory, hotbarIndex, 8 + hotbarIndex * SLOT_SIZE, PLAYER_INV_VGAP + guiStartSlotY + HOTBAR_VGAP + guiCalculatedContainerHeight + SLOT_SIZE * 3));
        }
    }

    public ParticularContainerScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, (Inventory) ((ParticularContainerItem) inventory.player.getMainHandStack().getItem()).getItemEntity(inventory.player.getMainHandStack()));
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < this.rows * this.columns) {
                if (!this.insertItem(itemStack2, this.rows * this.columns, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.rows * this.columns, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }

        return itemStack;
    }


    @Override
    public void onClosed(PlayerEntity player) {
        player.playSound(SoundManager.ZIPPER_CLOSE, SoundCategory.PLAYERS, 0.2f, 1);
        inventory.writeNbt(this.stack.getNbt());
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
