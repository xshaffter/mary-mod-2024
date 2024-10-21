package com.paramada.marycum2024.blocks.custom.entities;

import com.paramada.marycum2024.blocks.bases.SingleSpaceBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class HiddenBlockEntity extends SingleSpaceBlockEntity {
    public HiddenBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityManager.HIDDEN_ENTITY, pos, state);
    }
}
