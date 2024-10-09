package com.paramada.marycum2024.blocks.custom;

import com.paramada.marycum2024.blocks.bases.RotableBlockWithEntity;
import com.paramada.marycum2024.blocks.entities.BlockEntityManager;
import com.paramada.marycum2024.blocks.entities.EfigyBlockEntity;
import com.paramada.marycum2024.items.custom.RibbonItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EfigyBlock extends RotableBlockWithEntity implements BlockEntityProvider {
    public EfigyBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY)
                        .strength(-1.0F, 3600000.0F).nonOpaque(),
                VoxelShapes.cuboid(0, 0, 0, 1, 2, 1));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var stackInHand = player.getStackInHand(hand);
        if (stackInHand.getItem() instanceof RibbonItem) {
            var blockEntity = (EfigyBlockEntity) world.getBlockEntity(pos);
            assert blockEntity != null;
            if (!blockEntity.getItems().get(0).isOf(Items.AIR)) {
                return ActionResult.FAIL;
            }

            world.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1, 1);

            blockEntity.setStack(0, new ItemStack(stackInHand.getItem()));
            stackInHand.decrement(1);

            return ActionResult.CONSUME;
        }
        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EfigyBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof EfigyBlockEntity) {
                ItemScatterer.spawn(world, pos, (EfigyBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockEntityManager.EFIGY_ENTITY, EfigyBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
