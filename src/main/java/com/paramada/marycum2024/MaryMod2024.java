package com.paramada.marycum2024;

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
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaryMod2024 implements ModInitializer {

    public static final String MOD_ID = "mary-mod-2024";
    public static final Block GRACE_BLOCK = BlockManager.EFIGY;
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();
    public static final int TICKS_PER_SECOND = 20;

    @Override
    public void onInitialize() {
        ModScreenHandlers.registerScreenHandlersForServer();
        ModEffects.registerEffects();
        ItemManager.registerModItems();
        BlockEntityManager.registerEntities();
        BlockManager.registerModBlocks();
        AdvancementManager.registerCriterions();
        NetworkManager.registerC2SPackets();
        SoundManager.registerSounds();

        FabricDefaultAttributeRegistry.register(ModEntities.BEAGLE, BeagleEntity.createBeagleAttributes());

        ClientPlayConnectionEvents.JOIN.register((a, b, client) -> {
            ClientPlayNetworking.send(NetworkManager.REQUEST_UPGRADE_ID, PacketByteBufs.create());
            ClientPlayNetworking.send(NetworkManager.REQUEST_MONEY_ID, PacketByteBufs.create());
        });
    }
}
