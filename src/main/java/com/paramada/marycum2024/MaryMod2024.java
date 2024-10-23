package com.paramada.marycum2024;

import com.paramada.marycum2024.attributes.ModEntityAttributes;
import com.paramada.marycum2024.blocks.BlockManager;
import com.paramada.marycum2024.blocks.custom.entities.BlockEntityManager;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.entities.ModEntities;
import com.paramada.marycum2024.entities.custom.BeagleEntity;
import com.paramada.marycum2024.events.AdvancementManager;
import com.paramada.marycum2024.events.SoundManager;
import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.screens.handlers.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaryMod2024 implements ModInitializer {

    public static final String MOD_ID = "mary-mod-2024";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final int TICKS_PER_SECOND = 20;

    @Override
    public void onInitialize() {
        ModEntityAttributes.registerAttributes();
        ModScreenHandlers.registerScreenHandlersForServer();
        ModEffects.registerEffects();
        BlockEntityManager.registerEntities();
        BlockManager.registerModBlocks();
        ItemManager.registerModItems();
        AdvancementManager.registerCriterions();
        NetworkManager.registerC2SPackets();
        SoundManager.registerSounds();

        FabricDefaultAttributeRegistry.register(ModEntities.BEAGLE, BeagleEntity.createBeagleAttributes());
    }
}
