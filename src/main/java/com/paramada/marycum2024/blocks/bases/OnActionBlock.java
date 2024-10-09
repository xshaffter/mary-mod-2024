package com.paramada.marycum2024.blocks.bases;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class OnActionBlock extends RotableBlock {
    private final BlockActionHandler onAction;

    public OnActionBlock(Settings settings, final BlockActionHandler onAction) {
        super(settings);
        this.onAction = onAction;
    }

    public OnActionBlock(Settings settings, VoxelShape shape, final BlockActionHandler onAction) {
        super(settings, shape);
        this.onAction = onAction;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return onAction.handle(state, world, pos, player, hand, hit);
    }
}
