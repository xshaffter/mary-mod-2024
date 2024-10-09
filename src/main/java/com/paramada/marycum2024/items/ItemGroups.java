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

import static com.paramada.marycum2024.items.ItemManager.MARY_COIN;

public class ItemGroups {
    public static ItemGroup MARY_MOD_GROUP;


    public static void registerItemGroups() {
        MARY_MOD_GROUP = Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(MaryMod2024.MOD_ID, "mary_mod"),
                FabricItemGroup.builder().icon(() -> new ItemStack(MARY_COIN))
                        .displayName(Text.translatable("itemGroup.mary-mod-2024.mary_mod"))
                        .entries(((displayContext, entries) -> {
                            entries.add(ItemManager.MARY_COIN);
                            entries.add(ItemManager.BEAGLE_SPAWN_EGG);

                            //blocks
                            entries.add(BlockManager.EFIGY);
                            entries.add(BlockManager.SHULKER_BLOCK);

                            //healing
                            entries.add(ItemManager.BANDAGE);
                            entries.add(ItemManager.HEALING_FRUIT);

                            //estus
                            entries.add(ItemManager.ESTUS_SHARD);
                            entries.add(ItemManager.ESTUS_1);
                            entries.add(ItemManager.ESTUS_2);
                            entries.add(ItemManager.ESTUS_3);
                            entries.add(ItemManager.ESTUS_4);
                            entries.add(ItemManager.ESTUS_5);
                            entries.add(ItemManager.ESTUS_6);
                            entries.add(ItemManager.ESTUS_7);
                            entries.add(ItemManager.ESTUS_8);
                            entries.add(ItemManager.ESTUS_9);

                            //ribbons
                            entries.add(ItemManager.BLACK_RIBBON);
                            entries.add(ItemManager.BLUE_RIBBON);
                            entries.add(ItemManager.CYAN_RIBBON);
                            entries.add(ItemManager.GREEN_RIBBON);
                            entries.add(ItemManager.PINK_RIBBON);
                            entries.add(ItemManager.RED_RIBBON);
                        })).build()
        );
    }
}
