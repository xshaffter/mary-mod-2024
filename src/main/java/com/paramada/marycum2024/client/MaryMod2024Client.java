package com.paramada.marycum2024.client;

import com.paramada.marycum2024.blocks.custom.entities.BlockEntityManager;
import com.paramada.marycum2024.blocks.custom.entities.renderers.EfigyBlockEntityRenderer;
import com.paramada.marycum2024.entities.ModEntities;
import com.paramada.marycum2024.entities.client.BeagleModel;
import com.paramada.marycum2024.entities.client.BeagleRenderer;
import com.paramada.marycum2024.entities.client.ModModelLayers;
import com.paramada.marycum2024.events.KeyboardHandler;
import com.paramada.marycum2024.networking.NetworkManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class MaryMod2024Client implements ClientModInitializer {

    public static final String NPC_TEAM = "ALLY_NPC";
    public static final String PLAYER_TEAM = "PLAYER";
    public static final String MOB_TEAM = "MOB_NPC";

    @Override
    public void onInitializeClient() {
//        BlockRenderLayerMap.INSTANCE.putBlock(BlockManager.COMPUTER, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlockManager.CANDY_MACHINE, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlockManager.TOMB, RenderLayer.getCutout());
//
        //ScreenEvents.BEFORE_INIT.register(new ReplaceScreens());
//        HandledScreens.register(ModScreenHandlers.CANDY_MACHINE_SCREEN_HANDLER, CandyMachineScreen::new);
//        HandledScreens.register(ModScreenHandlers.ACTIONS_SCREEN_HANDLER, ActionsScreen::new);
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
