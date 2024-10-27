package com.paramada.marycum2024.blocks.custom;

import com.mojang.serialization.MapCodec;
import com.paramada.marycum2024.blocks.custom.entities.BlockEntityManager;
import com.paramada.marycum2024.blocks.custom.entities.HiddenBlockEntity;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HiddenBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<HiddenBlock> CODEC = createCodec(HiddenBlock::new);

    public HiddenBlock() {
        super(Settings
                .create()
                .nonOpaque()
                .dynamicBounds()
                .strength(-1.0F, 3600000.0F)
                .allowsSpawning(Blocks::never)
        );
    }

    public HiddenBlock(Settings settings) {
        this();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (PlayerEntityBridge.hasTDAH(player)) {
            return ActionResult.PASS;
        }

        var blockEntity = getHiddenBlockEntity(world, pos);
        if (blockEntity.isPresent()) {
            var entity = blockEntity.get();
            if (!entity.hasItem()) {
                if (player.isCreative()) {
                    var playerStack = player.getStackInHand(hand);
                    entity.setItem(playerStack.copy());
                }
            } else {
                var itemStack = entity.getItem();
                ItemEntity itemEntity = new ItemEntity(
                        world, (double)pos.getX() + 0.5, pos.getY() + 1, (double)pos.getZ() + 0.5, itemStack
                );
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
                entity.setItem(ItemStack.EMPTY);
            }
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    public static Optional<HiddenBlockEntity> getHiddenBlockEntity(World world, BlockPos pos) {
        return world.getBlockEntity(pos, BlockEntityManager.HIDDEN_ENTITY);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos pos, ShapeContext context) {
        var client = MinecraftClient.getInstance();
        var player = client.player;

        if (player == null || client.world == null) {
            return VoxelShapes.fullCube();
        }

        if (player.isCreative() || player.isSpectator()){
            return VoxelShapes.fullCube();
        }

        if (PlayerEntityBridge.hasTDAH(player)) {
            return VoxelShapes.fullCube();
        }

        var blockEntity = getHiddenBlockEntity(client.world, pos);

        if (blockEntity.isPresent()) {
            if (blockEntity.get().hasItem()) {
                return VoxelShapes.fullCube();
            }
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (player.hasStatusEffect(ModEffects.NEUROTYPICAL)) {
            return super.onBreak(world, pos, state, player);
        }

        return state;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HiddenBlockEntity(pos, state);
    }
}
