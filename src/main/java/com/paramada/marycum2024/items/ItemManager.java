package com.paramada.marycum2024.items;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.entities.ModEntities;
import com.paramada.marycum2024.items.custom.*;
import com.paramada.marycum2024.items.trinkets.GarlicNecklace;
import com.paramada.marycum2024.items.trinkets.bases.RibbonTrinket;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;


public class ItemManager {
    public static final Item MARY_COIN = new MaryCoinItem();
    public static final Item PINK_RIBBON = new RibbonItem(Rarity.COMMON);
    public static final Item BANDAGE = new Bandage();
    public static final Item ESTUS = new ReusablePotion();
    public static final Item ESTUS_SHARD = new Item(new FabricItemSettings().rarity(Rarity.RARE).maxCount(8).fireproof());
    public static final Item BEAGLE_SPAWN_EGG = registerItem("beagle_spawn_egg", new SpawnEggItem(ModEntities.BEAGLE, 0xFF9c7144, 0xFF2a1a0d, new FabricItemSettings()));
    public static final Item MEDIKA_POTION = new MedikaPotion();

    // Trinkets
    public static final Item GARLIC_NECKLACE = new GarlicNecklace();
    public static final Item PINK_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(

    ));
    public static final Item BLUE_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(

    ));
    public static final Item BLACK_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(

    ));
    public static final Item CYAN_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(

    ));
    public static final Item RED_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(

    ));
    public static final Item GREEN_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(

    ));


    @SuppressWarnings("SameParameterValue")
    private static Item registerItem(final String name, final Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MaryMod2024.MOD_ID, name), item);
    }

    public static void registerModItems() {
        registerItem("mary_coin_item", MARY_COIN);
        registerItem("bandage", BANDAGE);
        registerItem("estus", ESTUS);
        registerItem("estus_shard", ESTUS_SHARD);
        registerItem("pink_ribbon", PINK_RIBBON);
        registerItem("pink_ribbon_trinket", PINK_RIBBON_TRINKET);
        registerItem("blue_ribbon_trinket", BLUE_RIBBON_TRINKET);
        registerItem("black_ribbon_trinket", BLACK_RIBBON_TRINKET);
        registerItem("cyan_ribbon_trinket", CYAN_RIBBON_TRINKET);
        registerItem("red_ribbon_trinket", RED_RIBBON_TRINKET);
        registerItem("green_ribbon_trinket", GREEN_RIBBON_TRINKET);
        registerItem("medika_potion", MEDIKA_POTION);
        registerItem("garlic_necklace", GARLIC_NECKLACE);

        ItemGroups.registerItemGroups();
    }
}