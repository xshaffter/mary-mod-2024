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


public class ItemManager {
    public static final Item MARY_COIN = new MaryCoinItem();
    public static final Item PINK_RIBBON = new RibbonItem(Rarity.COMMON);
    public static final Item BANDAGE = new Bandage();
    public static final Item ESTUS_1 = new ReusablePotion(1);
    public static final Item ESTUS_2 = new ReusablePotion(2);
    public static final Item ESTUS_3 = new ReusablePotion(3);
    public static final Item ESTUS_4 = new ReusablePotion(4);
    public static final Item ESTUS_5 = new ReusablePotion(5);
    public static final Item ESTUS_6 = new ReusablePotion(6);
    public static final Item ESTUS_7 = new ReusablePotion(7);
    public static final Item ESTUS_8 = new ReusablePotion(8);
    public static final Item ESTUS_9 = new ReusablePotion(9);
    public static final Item ESTUS_SHARD = new Item(new FabricItemSettings().rarity(Rarity.RARE).maxCount(8).fireproof());
    public static final Item BEAGLE_SPAWN_EGG = registerItem("beagle_spawn_egg", new SpawnEggItem(ModEntities.BEAGLE, 0xFF9c7144, 0xFF2a1a0d, new FabricItemSettings()));
    public static final Item MEDIKA_POTION = new MedikaPotion();


    // Trinkets
    public static final Item GARLIC_NECKLACE = new GarlicNecklace();
    public static final Item PINK_RIBBON_TRINKET = new RibbonTrinket();
    public static final Item BLUE_RIBBON_TRINKET = new RibbonTrinket();
    public static final Item BLACK_RIBBON_TRINKET = new RibbonTrinket();
    public static final Item CYAN_RIBBON_TRINKET = new RibbonTrinket();
    public static final Item RED_RIBBON_TRINKET = new RibbonTrinket();
    public static final Item GREEN_RIBBON_TRINKET = new RibbonTrinket();
    //TODO: microfono (aumento de daño a distancia y el cuerpo a cuerpo hace daño en area)
    //TODO: headset (WIP)
    //TODO: gafas (NV)
    //TODO: hoodie (reduce CD de ataque)
    //TODO: los moños pasaran a ser trinkets y haran los mismos efectos


    @SuppressWarnings("SameParameterValue")
    private static Item registerItem(final String name, final Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MaryMod2024.MOD_ID, name), item);
    }

    public static void registerModItems() {
        registerItem("mary_coin_item", MARY_COIN);
        registerItem("bandage", BANDAGE);
        registerItem("estus_1", ESTUS_1);
        registerItem("estus_2", ESTUS_2);
        registerItem("estus_3", ESTUS_3);
        registerItem("estus_4", ESTUS_4);
        registerItem("estus_5", ESTUS_5);
        registerItem("estus_6", ESTUS_6);
        registerItem("estus_7", ESTUS_7);
        registerItem("estus_8", ESTUS_8);
        registerItem("estus_9", ESTUS_9);
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