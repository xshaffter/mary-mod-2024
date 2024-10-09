package com.paramada.marycum2024.blocks.custom.entities;

import com.paramada.marycum2024.items.custom.RibbonItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EfigyBlockEntity extends BlockEntity implements ImplementedInventory {
    public static final int INVENTORY_SIZE = 1;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

    public EfigyBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityManager.EFIGY_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public ItemStack getRenderStack() {
        return this.inventory.get(0);
    }

    @Override
    public void markDirty() {
        assert world != null;
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, EfigyBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if (hasRibbon(entity)) {
            if (world.getTime() % 80L == 0L) {
                double d = 10 + 10;
                var ribbon = (RibbonItem) entity.inventory.get(0).getItem();

                Box box = new Box(blockPos).expand(d).stretch(0.0, world.getHeight(), 0.0);
                List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
                for (PlayerEntity playerEntity : list) {
                    for (StatusEffectInstance effect : ribbon.getEffects()) {
                        playerEntity.addStatusEffect(new StatusEffectInstance(effect));
                    }

                }

            }

        }

    }

    private static boolean hasRibbon(EfigyBlockEntity entity) {
        return entity.getStack(0).getItem() instanceof RibbonItem;
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
}
