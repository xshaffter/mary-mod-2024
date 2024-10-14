package com.paramada.marycum2024.blocks.custom;

import com.mojang.serialization.MapCodec;
import com.paramada.marycum2024.blocks.BlockManager;
import com.paramada.marycum2024.blocks.bases.RotableBlockWithEntity;
import com.paramada.marycum2024.blocks.custom.entities.BlockEntityManager;
import com.paramada.marycum2024.blocks.custom.entities.EfigyBlockEntity;
import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.items.custom.RibbonItem;
import com.paramada.marycum2024.util.BlockPosUtil;
import com.paramada.marycum2024.util.LivingEntityBridge;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public class EfigyBlock extends RotableBlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<EfigyBlock> CODEC = createCodec(EfigyBlock::new);
    public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;

    public EfigyBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY)
                        .strength(-1.0F, 3600000.0F).nonOpaque(),
                VoxelShapes.fullCube());
    }

    public EfigyBlock(Settings settings) {
        this();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var stackInHand = player.getStackInHand(hand);
        var blockEntity = getEfigyBlockEntity(world, pos);
        if (blockEntity == null) {
            return ActionResult.FAIL;
        }

        var ribbonItem = blockEntity.getItem();


        if (ribbonItem.isEmpty()) {
            world.playSound(player, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1, 1);
            blockEntity.setItem(new ItemStack(ItemManager.PINK_RIBBON));

            return ActionResult.CONSUME;
        }

        if (world.isClient) {
            LivingEntityBridge.getPersistentData(player).put("effigy", BlockPosUtil.toNbt(blockEntity.getPos()));
            PlayerEntityBridge.startRestAnim(player);

            return ActionResult.PASS;
        }

        LivingEntityBridge.getPersistentData(player).put("effigy", BlockPosUtil.toNbt(blockEntity.getPos()));

        Objects.requireNonNull(player.getServer()).getTickManager().setFrozen(true);

        NamedScreenHandlerFactory screenHandlerFactory = (NamedScreenHandlerFactory) getBlockEntity(world, pos);
        player.openHandledScreen(screenHandlerFactory);

        return ActionResult.CONSUME;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos, state.with(HALF, BlockHalf.BOTTOM));
        world.setBlockState(pos.up(), state.with(HALF, BlockHalf.TOP));
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.tryBreakBlock(world, pos.up(), player);
        this.tryBreakBlock(world, pos.down(), player);
        return super.onBreak(world, pos, state, player);
    }

    private void tryBreakBlock(World world, BlockPos pos, PlayerEntity player) {
        var newState = world.getBlockState(pos);
        if (newState != null && newState.isOf(this)) {
            world.breakBlock(pos, false);
            world.removeBlockEntity(pos);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(HALF, BlockHalf.BOTTOM);
    }

    public static EfigyBlockEntity getEfigyBlockEntity(World world, BlockPos pos) {
        return (EfigyBlockEntity) getBlockEntity(world, pos);
    }

    public static BlockEntity getBlockEntity(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.isOf(BlockManager.EFIGY)) {
            return null;
        }

        if (state.get(HALF) == BlockHalf.BOTTOM) {
            return world.getBlockEntity(pos);
        }

        return world.getBlockEntity(pos.down(1));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.get(HALF) == BlockHalf.TOP) {
            return null;
        }

        return new EfigyBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF);
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, BlockEntityManager.EFIGY_ENTITY, EfigyBlockEntity::tick);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
