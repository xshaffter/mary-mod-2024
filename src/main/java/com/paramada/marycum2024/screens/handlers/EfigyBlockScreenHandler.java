package com.paramada.marycum2024.screens.handlers;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.BlockPosUtil;
import com.paramada.marycum2024.util.LivingEntityBridge;
import com.paramada.marycum2024.util.MoneyManager;
import com.paramada.marycum2024.util.UpgradeManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

    public void upgradePotion() {
        var player = inventory.player;
        var world = player.getWorld();
        var price = UpgradeManager.getNextUpgradeCost(player);
        if (price > 0 && MoneyManager.getMoney(player) >= price) {
            MoneyManager.spendMoney(price);
            ClientPlayNetworking.send(NetworkManager.INCREASE_UPGRADE_ID, PacketByteBufs.create());
            world.playSound(player, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1, 1);
        }
    }

    public void increasePotion() {
        var player = inventory.player;
        var world = player.getWorld();
        var price = UpgradeManager.getNextDurabilityCost(player);
        if (price > 0 && MoneyManager.getMoney(player) >= price) {
            MoneyManager.spendMoney(price);
            ClientPlayNetworking.send(NetworkManager.INCREASE_POTION_AMOUNT_ID, PacketByteBufs.create());
            world.playSound(player, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1, 1);
        }
    }
}
