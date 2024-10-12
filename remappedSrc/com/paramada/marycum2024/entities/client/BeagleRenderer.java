package com.paramada.marycum2024.entities.client;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.entities.custom.BeagleEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BeagleRenderer extends MobEntityRenderer<BeagleEntity, BeagleModel<BeagleEntity>> {
    private static final Identifier TEXTURE = new Identifier(MaryMod2024.MOD_ID, "textures/entity/beagle.png");
    public BeagleRenderer(EntityRendererFactory.Context context) {
        super(context, new BeagleModel<>(context.getPart(ModModelLayers.BEAGLE)), 0.6f);
    }

    @Override
    public Identifier getTexture(BeagleEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BeagleEntity mobEntity, float x, float y, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }
        super.render(mobEntity, x, y, matrixStack, vertexConsumerProvider, i);
    }
}
