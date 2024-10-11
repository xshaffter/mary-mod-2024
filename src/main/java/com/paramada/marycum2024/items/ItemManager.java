package com.paramada.marycum2024.items;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.entities.ModEntities;
import com.paramada.marycum2024.items.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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

    //tank
    public static final Item PINK_RIBBON = new RibbonItem(List.of(
            new StatusEffectInstance(StatusEffects.ABSORPTION, MaryMod2024.TICKS_PER_SECOND * 180, 1, true, false),
            new StatusEffectInstance(StatusEffects.REGENERATION, MaryMod2024.TICKS_PER_SECOND * 60, 1, true, false),
            new StatusEffectInstance(StatusEffects.RESISTANCE, MaryMod2024.TICKS_PER_SECOND * 60, 2, true, false)
    ), List.of(
            new StatusEffectInstance(ModEffects.PINK_RIBBON_EFFECT, StatusEffectInstance.INFINITE, 0, true, false)
    ));

    //damage
    public static final Item BLUE_RIBBON = new RibbonItem(List.of(

    ), List.of(
            new StatusEffectInstance(ModEffects.BLUE_RIBBON_EFFECT, StatusEffectInstance.INFINITE, 0, true, false)
    ));

    //Healing
    public static final Item RED_RIBBON = new RibbonItem(List.of(
            new StatusEffectInstance(StatusEffects.ABSORPTION, MaryMod2024.TICKS_PER_SECOND * 180, 0, true, false)

    ), List.of(
            new StatusEffectInstance(ModEffects.RED_RIBBON_EFFECT, StatusEffectInstance.INFINITE, 0, true, false)
    ));

    //Speed
    public static final Item CYAN_RIBBON = new RibbonItem(List.of(
            new StatusEffectInstance(StatusEffects.SPEED, MaryMod2024.TICKS_PER_SECOND * 120, 1, true, false)
    ), List.of(
            new StatusEffectInstance(ModEffects.CYAN_RIBBON_EFFECT, StatusEffectInstance.INFINITE, 0, true, false)
    ));

    //Zombie
    public static final Item GREEN_RIBBON = new RibbonItem(List.of(
    ), List.of(
            new StatusEffectInstance(ModEffects.GREEN_RIBBON_EFFECT, StatusEffectInstance.INFINITE, 0, true, false),
            new StatusEffectInstance(ModEffects.ZOMBIEFICATION, StatusEffectInstance.INFINITE, 0, true, true)

    ));

    //Vampire
    public static final Item BLACK_RIBBON = new RibbonItem(List.of(
            new StatusEffectInstance(StatusEffects.SPEED, MaryMod2024.TICKS_PER_SECOND * 120 + 1, 2, true, false),
            new StatusEffectInstance(StatusEffects.STRENGTH, MaryMod2024.TICKS_PER_SECOND * 120 + 1, 2, true, false)
    ), List.of(
            new StatusEffectInstance(ModEffects.BLACK_RIBBON_EFFECT, StatusEffectInstance.INFINITE, 0, true, false),
            new StatusEffectInstance(ModEffects.VAMPIRISM, StatusEffectInstance.INFINITE, 0, true, true),
            new StatusEffectInstance(StatusEffects.HEALTH_BOOST, StatusEffectInstance.INFINITE, 3, true, false)

    ));
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
    public static final Item HEALING_FRUIT = new HealingFruit();
    public static final Item BEAGLE_SPAWN_EGG = registerItem("beagle_spawn_egg", new SpawnEggItem(ModEntities.BEAGLE, 0xFF9c7144, 0xFF2a1a0d, new FabricItemSettings()));
    public static final Item MEDIKA_POTION = new MedikaPotion();

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
        registerItem("medika_potion", MEDIKA_POTION);
        ItemGroups.registerItemGroups();
    }
}