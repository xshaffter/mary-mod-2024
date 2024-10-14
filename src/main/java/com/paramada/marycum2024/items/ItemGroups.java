package com.paramada.marycum2024.items;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.blocks.BlockManager;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static ItemGroup MARY_MOD_BLOCKS_GROUP;
    public static ItemGroup MARY_MOD_ARMORS_GROUP;
    public static ItemGroup MARY_MOD_MISC_GROUP;
    public static ItemGroup MARY_MOD_ACCESORIES_GROUP;
    public static ItemGroup MARY_MOD_HEALING_GROUP;

    public static void registerItemGroups() {
        MARY_MOD_BLOCKS_GROUP = Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(MaryMod2024.MOD_ID, "mary_mod_blocks"),
                FabricItemGroup.builder().icon(() -> new ItemStack(BlockManager.SHULKER_BLOCK))
                        .displayName(Text.translatable("itemGroup.mary-mod-2024.mary_mod_blocks"))
                        .entries(((displayContext, entries) -> {
                            entries.add(BlockManager.EFIGY);
                            entries.add(BlockManager.SHULKER_BLOCK);
                        })).build()
        );
        MARY_MOD_ARMORS_GROUP = Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(MaryMod2024.MOD_ID, "mary_mod_armors"),
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemManager.MARY_COIN))
                        .displayName(Text.translatable("itemGroup.mary-mod-2024.mary_mod_armors"))
                        .entries(((displayContext, entries) -> {
                        })).build()
        );
        MARY_MOD_MISC_GROUP = Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(MaryMod2024.MOD_ID, "mary_mod_misc"),
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemManager.MARY_COIN))
                        .displayName(Text.translatable("itemGroup.mary-mod-2024.mary_mod_misc"))
                        .entries(((displayContext, entries) -> {
                            entries.add(ItemManager.MARY_COIN);
                            entries.add(ItemManager.BEAGLE_SPAWN_EGG);

                            entries.add(ItemManager.PINK_RIBBON);

                        })).build()
        );
        MARY_MOD_ACCESORIES_GROUP = Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(MaryMod2024.MOD_ID, "mary_mod_accesories"),
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemManager.GARLIC_NECKLACE))
                        .displayName(Text.translatable("itemGroup.mary-mod-2024.mary_mod_accesories"))
                        .entries(((displayContext, entries) -> {
                            entries.add(ItemManager.GARLIC_NECKLACE);
                            entries.add(ItemManager.BLACK_RIBBON_TRINKET);
                            entries.add(ItemManager.BLUE_RIBBON_TRINKET);
                            entries.add(ItemManager.CYAN_RIBBON_TRINKET);
                            entries.add(ItemManager.GREEN_RIBBON_TRINKET);
                            entries.add(ItemManager.PINK_RIBBON_TRINKET);
                            entries.add(ItemManager.RED_RIBBON_TRINKET);
                        })).build()
        );
        MARY_MOD_HEALING_GROUP = Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(MaryMod2024.MOD_ID, "mary_mod_healing"),
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemManager.BANDAGE))
                        .displayName(Text.translatable("itemGroup.mary-mod-2024.mary_mod_healing"))
                        .entries(((displayContext, entries) -> {

                            entries.add(ItemManager.BANDAGE);

                            entries.add(ItemManager.ESTUS_SHARD);
                            entries.add(ItemManager.MEDIKA_POTION);
                            entries.add(ItemManager.ESTUS_1);
                            entries.add(ItemManager.ESTUS_2);
                            entries.add(ItemManager.ESTUS_3);
                            entries.add(ItemManager.ESTUS_4);
                            entries.add(ItemManager.ESTUS_5);
                            entries.add(ItemManager.ESTUS_6);
                            entries.add(ItemManager.ESTUS_7);
                            entries.add(ItemManager.ESTUS_8);
                            entries.add(ItemManager.ESTUS_9);
                        })).build()
        );
    }
}
