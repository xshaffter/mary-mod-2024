package com.paramada.marycum2024.blocks.entities;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.blocks.BlockManager;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class BlockEntityManager {
    public static BlockEntityType<EfigyBlockEntity> EFIGY_ENTITY;

    public static void registerEntities() {
        EFIGY_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MaryMod2024.MOD_ID, "efigy_entity"),
                FabricBlockEntityTypeBuilder.create(EfigyBlockEntity::new, BlockManager.EFIGY).build(null));
    }
}
