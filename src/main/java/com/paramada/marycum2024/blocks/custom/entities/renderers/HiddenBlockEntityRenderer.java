package com.paramada.marycum2024.blocks.custom.entities.renderers;

import com.paramada.marycum2024.blocks.custom.entities.EfigyBlockEntity;
import com.paramada.marycum2024.blocks.custom.entities.HiddenBlockEntity;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class HiddenBlockEntityRenderer implements BlockEntityRenderer<HiddenBlockEntity> {

    public HiddenBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(HiddenBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var renderStack = entity.getItem();
        var itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        var world = Objects.requireNonNull(entity.getWorld());
        var player = MinecraftClient.getInstance().player;

        var downPos = entity.getPos().down();

        matrices.push();
        if (world.isClient) {
            if (PlayerEntityBridge.hasTDAH(player)) {
                var blockStack = new ItemStack(world.getBlockState(downPos).getBlock().asItem());
                matrices.translate(0.5, 0.5, 0.5);
                matrices.scale(1.6f, 1.6f, 1.6f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(30));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
                itemRenderer.renderItem(blockStack, ModelTransformationMode.GUI, getLightLevel(world, entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                matrices.pop();
                return;
            } else if (player.isCreative()) {
                var blockStack = new ItemStack(world.getBlockState(downPos).getBlock().asItem());
                matrices.translate(0.5, 0.5, 0.5);
                matrices.scale(0.5f, 0.5f, 0.5f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(30));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
                itemRenderer.renderItem(blockStack, ModelTransformationMode.GUI, getLightLevel(world, entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                matrices.pop();
                matrices.push();
            }
        }
        double sinoidalHeight = 0.5 + Math.sin((double) (world.getTime()) / 8) / 20;


        matrices.translate(0.5, sinoidalHeight, 0.5);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));


        itemRenderer.renderItem(renderStack, ModelTransformationMode.GUI, getLightLevel(world, entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        var blockLight = world.getLightLevel(LightType.BLOCK, pos);
        var skyLight = world.getLightLevel(LightType.SKY, pos);

        return LightmapTextureManager.pack(blockLight, skyLight);
    }
}
