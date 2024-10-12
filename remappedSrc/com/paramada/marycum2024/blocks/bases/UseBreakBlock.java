package com.paramada.marycum2024.blocks.bases;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public abstract class UseBreakBlock extends OnActionBlock {
    public UseBreakBlock(Settings settings) {
        super(settings, (BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) -> {
            if (world.isClient) {
                return ActionResult.PASS;
            }
            world.breakBlock(pos, true);
            return ActionResult.SUCCESS;
        });
    }
    public UseBreakBlock(Settings settings, VoxelShape shape) {
        super(settings, shape, (BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) -> {
            if (world.isClient) {
                return ActionResult.PASS;
            }
            world.breakBlock(pos, true);
            return ActionResult.SUCCESS;
        });
    }
}
