package com.paramada.marycum2024.blocks.custom.entities;

import com.paramada.marycum2024.blocks.bases.SingleSpaceBlockEntity;
import com.paramada.marycum2024.items.custom.RibbonItem;
import com.paramada.marycum2024.screens.handlers.EfigyBlockScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class EfigyBlockEntity extends SingleSpaceBlockEntity implements NamedScreenHandlerFactory {

    public EfigyBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityManager.EFIGY_ENTITY, pos, state);
        item = ItemStack.EMPTY;
    }

    public ItemStack getRenderStack() {
        return this.getItem();
    }

    @Override
    public void markDirty() {
        if (world == null) {
            return;
        }
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Override
    public boolean hasItem() {
        return this.getItem().getItem() instanceof RibbonItem;
    }

    public static boolean hasRibbon(EfigyBlockEntity entity) {
        return entity.getItem().getItem() instanceof RibbonItem;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("title.mary-mod-2024.efigy");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new EfigyBlockScreenHandler(syncId, playerInventory);
    }
}
