package com.paramada.marycum2024.screens.handlers;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.BlockPosUtil;
import com.paramada.marycum2024.util.LivingEntityBridge;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class EfigyBlockScreenHandler extends ScreenHandler {


    private final PlayerInventory inventory;
    public final int SYNC_ID;
    private final BlockPos pos;

    public EfigyBlockScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, LivingEntityBridge.getPersistentData(inventory.player).getCompound("effigy"));
    }

    public EfigyBlockScreenHandler(int syncId, PlayerInventory inventory, NbtCompound effigyPos) {
        super(ModScreenHandlers.EFIGY_BLOCK_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.SYNC_ID = syncId;
        this.pos = BlockPosUtil.fromNbt(effigyPos);
    }

    public void rest() {
        var buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        ClientPlayNetworking.send(NetworkManager.REST_AT_EFIGY_ID, buf);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
