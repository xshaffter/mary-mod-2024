package com.paramada.marycum2024.blocks.bases;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class UsableTextBlock extends OnActionBlock {
    public UsableTextBlock(Settings settings, Text text) {
        this(settings, text, VoxelShapes.cuboid(0, 0, 0, 1, 1, 1));
    }

    public UsableTextBlock(Settings settings, Text text, VoxelShape shape) {
        super(settings, shape, (state, world, pos, player, hand, hit) -> {
            if (!world.isClient) {
                return ActionResult.PASS;
            }
            player.sendMessage(text);
            return ActionResult.SUCCESS;
        });
    }

    public UsableTextBlock(BlockSoundGroup sound, Text text, VoxelShape shape) {
        this(FabricBlockSettings.create()
                .nonOpaque()
                .sounds(sound)
                .strength(4f)
                .hardness(1f)
                .collidable(false), text, shape);
    }

    public UsableTextBlock(BlockSoundGroup sound, Text text) {
        this(FabricBlockSettings.create()
                        .nonOpaque()
                        .sounds(sound)
                        .strength(4f)
                        .hardness(1f)
                        .collidable(false), text,
                VoxelShapes.cuboid(0f, 0f, 0f, 1f, 1f, 1f)
        );
    }

    public UsableTextBlock(Text text, Settings settings) {
        this(settings, text, VoxelShapes.cuboid(0f, 0f, 0f, 1f, 1f, 1f));
    }
}
