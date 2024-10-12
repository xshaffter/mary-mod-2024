package com.paramada.marycum2024.items;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.entities.ModEntities;
import com.paramada.marycum2024.items.custom.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;


public class ItemManager {
    public static final Item MARY_COIN = new MaryCoinItem();
    public static final Item PINK_RIBBON = new RibbonItem(List.of(
            new StatusEffectInstance(StatusEffects.ABSORPTION, MaryMod2024.TICKS_PER_SECOND * 180, 1, true, true),
            new StatusEffectInstance(StatusEffects.REGENERATION, MaryMod2024.TICKS_PER_SECOND * 60, 1, true, true),
            new StatusEffectInstance(StatusEffects.RESISTANCE, MaryMod2024.TICKS_PER_SECOND * 60, 2, true, true)
    ), ModEffects.PINK_RIBBON_EFFECT);
    public static final Item BLUE_RIBBON = new RibbonItem(List.of(

    ), ModEffects.BLUE_RIBBON_EFFECT);
    public static final Item RED_RIBBON = new RibbonItem(List.of(
            new StatusEffectInstance(StatusEffects.ABSORPTION, MaryMod2024.TICKS_PER_SECOND * 180, 0, true, true)

    ), ModEffects.RED_RIBBON_EFFECT);
    public static final Item GREEN_RIBBON = new RibbonItem(List.of(

    ), ModEffects.GREEN_RIBBON_EFFECT);
    public static final Item CYAN_RIBBON = new RibbonItem(List.of(

    ), ModEffects.CYAN_RIBBON_EFFECT);
    public static final Item BLACK_RIBBON = new RibbonItem(List.of(
            new StatusEffectInstance(StatusEffects.REGENERATION, MaryMod2024.TICKS_PER_SECOND * 4 + 1, 2, true, true)
    ), ModEffects.BLACK_RIBBON_EFFECT);
    public static final Item BANDAGE = new Bandage();
<<<<<<< Updated upstream
    public static final Item ESTUS_1 = new Estus(1);
    public static final Item ESTUS_2 = new Estus(2);
    public static final Item ESTUS_3 = new Estus(3);
    public static final Item ESTUS_4 = new Estus(4);
    public static final Item ESTUS_5 = new Estus(5);
    public static final Item ESTUS_6 = new Estus(6);
    public static final Item ESTUS_7 = new Estus(7);
    public static final Item ESTUS_8 = new Estus(8);
    public static final Item ESTUS_9 = new Estus(9);
    public static final Item ESTUS_SHARD = new Item(new FabricItemSettings().rarity(Rarity.RARE).maxCount(8).fireproof());
    public static final Item HEALING_FRUIT = new HealingFruit();
    public static final Item BEAGLE_SPAWN_EGG = registerItem("beagle_spawn_egg", new SpawnEggItem(ModEntities.BEAGLE, 0xFF9c7144, 0xFF2a1a0d, new FabricItemSettings()));
=======
    public static final Item ESTUS_1 = new ReusablePotion(1);
    public static final Item ESTUS_2 = new ReusablePotion(2);
    public static final Item ESTUS_3 = new ReusablePotion(3);
    public static final Item ESTUS_4 = new ReusablePotion(4);
    public static final Item ESTUS_5 = new ReusablePotion(5);
    public static final Item ESTUS_6 = new ReusablePotion(6);
    public static final Item ESTUS_7 = new ReusablePotion(7);
    public static final Item ESTUS_8 = new ReusablePotion(8);
    public static final Item ESTUS_9 = new ReusablePotion(9);
    public static final Item ESTUS_SHARD = new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(8).fireproof());
    public static final Item HEALING_FRUIT = new HealingFruit();
    public static final Item BEAGLE_SPAWN_EGG = registerItem("beagle_spawn_egg", new SpawnEggItem(ModEntities.BEAGLE, 0xFF9c7144, 0xFF2a1a0d, new Item.Settings()));
    public static final Item MEDIKA_POTION = new MedikaPotion();
>>>>>>> Stashed changes

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
        registerItem("healing_fruit", HEALING_FRUIT);
        registerItem("pink_ribbon", PINK_RIBBON);
        registerItem("blue_ribbon", BLUE_RIBBON);
        registerItem("black_ribbon", BLACK_RIBBON);
        registerItem("cyan_ribbon", CYAN_RIBBON);
        registerItem("red_ribbon", RED_RIBBON);
        registerItem("green_ribbon", GREEN_RIBBON);
        ItemGroups.registerItemGroups();
    }
}