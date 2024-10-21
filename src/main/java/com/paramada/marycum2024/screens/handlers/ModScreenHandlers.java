package com.paramada.marycum2024.screens.handlers;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<EfigyBlockScreenHandler> EFIGY_BLOCK_SCREEN_HANDLER;
    public static ScreenHandlerType<ParticularContainerScreenHandler> PARTICULAR_SCREEN_HANDLER;

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(MaryMod2024.MOD_ID, name), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void registerScreenHandlersForServer() {
        EFIGY_BLOCK_SCREEN_HANDLER = register("efigy_block_screen_handler", EfigyBlockScreenHandler::new);
        PARTICULAR_SCREEN_HANDLER = register("particular_screen_handler", ParticularContainerScreenHandler::new);
    }
}
