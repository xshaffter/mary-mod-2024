package com.paramada.marycum2024.client;

import com.paramada.marycum2024.blocks.custom.entities.BlockEntityManager;
import com.paramada.marycum2024.blocks.custom.entities.renderers.EfigyBlockEntityRenderer;
import com.paramada.marycum2024.entities.ModEntities;
import com.paramada.marycum2024.entities.client.BeagleModel;
import com.paramada.marycum2024.entities.client.BeagleRenderer;
import com.paramada.marycum2024.entities.client.ModModelLayers;
import com.paramada.marycum2024.events.KeyboardHandler;
import com.paramada.marycum2024.networking.NetworkManager;
import io.netty.util.internal.ObjectUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.apache.commons.lang3.ObjectUtils;

@Environment(EnvType.CLIENT)
public class MaryMod2024Client implements ClientModInitializer {

    public static final String NPC_TEAM = "ALLY_NPC";
    public static final String PLAYER_TEAM = "PLAYER";
    public static final String MOB_TEAM = "MOB_NPC";

    @Override
    public void onInitializeClient() {
        NetworkManager.registerS2CPackets();
        KeyboardHandler.register();


        EntityRendererRegistry.register(ModEntities.BEAGLE, BeagleRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BEAGLE, BeagleModel::getTexturedModelData);

        BlockEntityRendererFactories.register(BlockEntityManager.EFIGY_ENTITY, EfigyBlockEntityRenderer::new);
    }

    public static String getModVersion() {
        return "0.1.0";
    }
}
/*

nada de chistes fisicos
nada de odio a novela
se permite el shansi
se permite cherry
jojoshua ya no modela, considerar a lethal
esperancita peruana prohibida
streams de cocina permitidos
danger tentativo ** TERRY
contador de muertes
reganiar a galahad es meme bueno

*/
