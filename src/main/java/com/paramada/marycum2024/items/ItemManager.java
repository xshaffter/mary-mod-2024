package com.paramada.marycum2024.items;

import com.google.common.collect.ImmutableMultimap;
import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.entities.ModEntities;
import com.paramada.marycum2024.items.custom.*;
import com.paramada.marycum2024.items.custom.containers.ParticularContainerItem;
import com.paramada.marycum2024.items.custom.containers.PouchPredicates;
import com.paramada.marycum2024.items.custom.potions.Bandage;
import com.paramada.marycum2024.items.custom.potions.MedikaPotion;
import com.paramada.marycum2024.items.custom.potions.ReusablePotion;
import com.paramada.marycum2024.items.trinkets.GarlicNecklace;
import com.paramada.marycum2024.items.trinkets.Glasses;
import com.paramada.marycum2024.items.trinkets.MicrophoneTrinket;
import com.paramada.marycum2024.items.trinkets.bases.RibbonTrinket;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.UUID;


public class ItemManager {
    public static final Item MARY_COIN = new MaryCoinItem();
    public static final Item PINK_RIBBON = new RibbonItem(Rarity.COMMON);
    public static final Item BANDAGE = new Bandage();
    public static final Item ESTUS = new ReusablePotion();
    public static final Item ESTUS_SHARD = new Item(new FabricItemSettings().rarity(Rarity.RARE).maxCount(8).fireproof());
    public static final Item BEAGLE_SPAWN_EGG = registerItem("beagle_spawn_egg", new SpawnEggItem(ModEntities.BEAGLE, 0xFF9c7144, 0xFF2a1a0d, new FabricItemSettings()));
    public static final Item MEDIKA_POTION = new MedikaPotion();
    public static final Item POTION_CASE = new ParticularContainerItem(Rarity.COMMON, 3, 3, PouchPredicates.POTION_PREDICATE);
    public static final Item POTION_CASE_2 = new ParticularContainerItem(Rarity.COMMON, 4, 4, PouchPredicates.POTION_PREDICATE);

    // Trinkets
    public static final Item GARLIC_NECKLACE = new GarlicNecklace();
    public static final Item GLASSES = new Glasses();
    public static final Item PINK_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(
            new StatusEffectInstance(StatusEffects.RESISTANCE, MaryMod2024.TICKS_PER_SECOND * 15, 0, false, true)
    ), ImmutableMultimap.of(
            EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.randomUUID(), "ribbon_max_hp", 4, EntityAttributeModifier.Operation.ADDITION)
    ));
    public static final Item GREEN_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(
            new StatusEffectInstance(ModEffects.ZOMBIEFICATION, MaryMod2024.TICKS_PER_SECOND * 120)
    ), ImmutableMultimap.of(

    ));
    public static final Item BLACK_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(
            new StatusEffectInstance(ModEffects.VAMPIRISM, MaryMod2024.TICKS_PER_SECOND * 120)
    ), ImmutableMultimap.of(
            EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(UUID.randomUUID(), "ribbon_atk_sp", 1.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
    ));
    public static final Item BLUE_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(
    ), ImmutableMultimap.of(
    ));
    public static final Item CYAN_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(

    ), ImmutableMultimap.of(

    ));
    public static final Item RED_RIBBON_TRINKET = new RibbonTrinket(Rarity.RARE, List.of(

    ), ImmutableMultimap.of(

    ));

    public static final Item MICROPHONE_TRINKET = new MicrophoneTrinket();

    public static final Item TDAH_PILL = new TDAHPillItem();


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
        registerItem("potion_case", POTION_CASE);
        registerItem("potion_case_2", POTION_CASE_2);
        registerItem("glasses", GLASSES);
        registerItem("tdah_pill", TDAH_PILL);
        registerItem("microphone_trinket", MICROPHONE_TRINKET);

        ItemGroups.registerItemGroups();
    }
}