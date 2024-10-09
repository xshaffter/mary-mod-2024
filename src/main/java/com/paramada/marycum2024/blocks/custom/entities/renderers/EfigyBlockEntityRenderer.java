package com.paramada.marycum2024.blocks.custom.entities.renderers;

import com.paramada.marycum2024.blocks.custom.entities.EfigyBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class EfigyBlockEntityRenderer implements BlockEntityRenderer<EfigyBlockEntity> {

    public EfigyBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(EfigyBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var renderStack = entity.getRenderStack();
        var itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        var world = Objects.requireNonNull(entity.getWorld());
        matrices.push();

        double sinoidalHeight = 2 + Math.sin((double) (world.getTime()) /8) / 20;

        matrices.translate(0.5, sinoidalHeight, 0.5);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(5 * world.getTime()));

        itemRenderer.renderItem(renderStack, ModelTransformationMode.GUI, getLightLevel(world, entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();

    }

    private int getLightLevel(World world, BlockPos pos) {
        var blockLight = world.getLightLevel(LightType.BLOCK, pos);
        var skyLight = world.getLightLevel(LightType.SKY, pos);

        return LightmapTextureManager.pack(blockLight, skyLight);
    }
}
