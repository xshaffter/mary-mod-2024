package com.paramada.marycum2024.blocks;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.blocks.custom.EfigyBlock;
import com.paramada.marycum2024.blocks.custom.HiddenBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

public class BlockManager {

    public static final Block SHULKER_BLOCK = new Block(AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(4).hardness(4));
    public static final Block EFIGY = new EfigyBlock();
    public static final Block HIDDEN_BLOCK = new HiddenBlock();

    private static void registerBlock(final String name, final Block block, final BlockItem blockItem) {
        Registry.register(Registries.BLOCK, new Identifier(MaryMod2024.MOD_ID, name), block);
        registerBlockItem(name, blockItem);
    }

    private static void registerBlockItem(final String name, final BlockItem blockItem) {
        Registry.register(Registries.ITEM, new Identifier(MaryMod2024.MOD_ID, name), blockItem);
    }

    private static void registerBlockAuto(final String name, final Block block) {
        registerBlock(name, block, new BlockItem(block, new FabricItemSettings().fireproof().maxCount(64)));
    }

    public static void registerModBlocks() {
        registerBlockAuto("shulker_block", SHULKER_BLOCK);
        registerBlockAuto("efigy", EFIGY);
        registerBlockAuto("hidden_block", HIDDEN_BLOCK);
    }
}
