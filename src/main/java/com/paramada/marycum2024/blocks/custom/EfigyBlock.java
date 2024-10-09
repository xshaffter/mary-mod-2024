package com.paramada.marycum2024.blocks.custom;

import com.paramada.marycum2024.blocks.bases.RotableBlockWithEntity;
import com.paramada.marycum2024.blocks.custom.entities.BlockEntityManager;
import com.paramada.marycum2024.blocks.custom.entities.EfigyBlockEntity;
import com.paramada.marycum2024.items.custom.RibbonItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Colors;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EfigyBlock extends RotableBlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;

    public EfigyBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY)
                        .strength(-1.0F, 3600000.0F).nonOpaque(),
                VoxelShapes.fullCube());
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var stackInHand = player.getStackInHand(hand);
        var blockEntity = this.getBlockEntity(world, pos);
        assert blockEntity != null;
        var ribbonItem = blockEntity.getItems().get(0);
        if (stackInHand.getItem() instanceof RibbonItem) {
            if (!ribbonItem.isOf(Items.AIR)) {
                return ActionResult.FAIL;
            }

            world.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1, 1);

            blockEntity.setStack(0, new ItemStack(stackInHand.getItem()));
            stackInHand.decrement(1);

            return ActionResult.CONSUME;
        } else if (!world.isClient) {
            if (ribbonItem.isOf(Items.AIR)) {
                world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1, 1);
                player.sendMessage(Text.translatable("chat.mary-mod-2024.ribbon_needed").setStyle(Style.EMPTY.withColor(0xFFC7C700)));
                return ActionResult.PASS;
            }
            if (ribbonItem.getItem() instanceof RibbonItem ribbon) {
                player.clearStatusEffects();
                player.setHealth(player.getMaxHealth());
                player.setAbsorptionAmount(0);
                player.addStatusEffect(new StatusEffectInstance(ribbon.getPotionUpgrade(), StatusEffectInstance.INFINITE, 0, false, false));
            }

        }

        return ActionResult.CONSUME;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos, state.with(HALF, BlockHalf.BOTTOM));
        world.setBlockState(pos.up(), state.with(HALF, BlockHalf.TOP));
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.tryBreakBlock(world, pos.up(), player);
        this.tryBreakBlock(world, pos.down(), player);
        super.onBreak(world, pos, state, player);
    }

    private void tryBreakBlock(World world, BlockPos pos, PlayerEntity player) {
        var newState = world.getBlockState(pos);
        if (newState != null && newState.isOf(this)) {
            world.breakBlock(pos, false);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(HALF, BlockHalf.BOTTOM);
    }

    private EfigyBlockEntity getBlockEntity(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.isOf(this)) {
            return null;
        }
        if (state.get(HALF) == BlockHalf.BOTTOM) {
            return (EfigyBlockEntity) world.getBlockEntity(pos);
        }
        return (EfigyBlockEntity) world.getBlockEntity(pos.down(1));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EfigyBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            EfigyBlockEntity blockEntity = getBlockEntity(world, pos);
            if (blockEntity != null) {
                ItemScatterer.spawn(world, pos, blockEntity);
            }
            world.updateComparators(pos, this);
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
        return checkType(type, BlockEntityManager.EFIGY_ENTITY, EfigyBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
