package com.paramada.marycum2024.util;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.paramada.marycum2024.items.ItemManager;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UpgradeManager {
    private static final Map<Integer, Integer> UPGRADE_COST_MAPPING = Map.ofEntries(
            Map.entry(1, 10),
            Map.entry(2, 50),
            Map.entry(3, 100),
            Map.entry(4, 150),
            Map.entry(5, 200),
            Map.entry(6, 250)
    );
    private static final Map<Integer, Integer> DURABILITY_COST_MAPPING = Map.ofEntries(
            Map.entry(1, 100),
            Map.entry(2, 100),
            Map.entry(3, 150),
            Map.entry(4, 150),
            Map.entry(5, 200),
            Map.entry(6, 250)
    );
    private static final Map<Integer, Integer> LEVEL_COST_MAPPING = Map.ofEntries(
            Map.entry(1, 100),
            Map.entry(2, 150),
            Map.entry(3, 200),
            Map.entry(4, 250),
            Map.entry(5, 300),
            Map.entry(6, 350),
            Map.entry(7, 400),
            Map.entry(8, 450),
            Map.entry(9, 500),
            Map.entry(10, 550)
    );
    private static final Map<Integer, LevelModifierMapping> LEVEL_ATTRIBUTE_MAPPING = Map.ofEntries(
            Map.entry(1, LevelModifierMapping.EMPTY),
            Map.entry(2, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("1b019975-5b2f-45db-82d3-b17d8bdb968f"), "LEVEL_UP_MAX_HEALTH", 2, EntityAttributeModifier.Operation.ADDITION)
            ))),
            Map.entry(3, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("f6d75b7c-fd5a-4885-aa50-3bf81e2a33af"), "LEVEL_UP_MAX_HEALTH", 4, EntityAttributeModifier.Operation.ADDITION)

            ))),
            Map.entry(4, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("9c00c442-198e-49bd-8b39-6fa854560798"), "LEVEL_UP_MAX_HEALTH", 6, EntityAttributeModifier.Operation.ADDITION)

            ))),
            Map.entry(5, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("60fd5339-cf30-4560-922e-42318d73d03e"), "LEVEL_UP_MAX_HEALTH", 8, EntityAttributeModifier.Operation.ADDITION)

            ))),
            Map.entry(6, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("e28dd4c5-d4bb-4fce-a560-9d2cdf2e0ba1"), "LEVEL_UP_MAX_HEALTH", 10, EntityAttributeModifier.Operation.ADDITION)

            ))),
            Map.entry(7, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("f081dcb0-1c1e-4d38-ac06-5d06675ee660"), "LEVEL_UP_MAX_HEALTH", 12, EntityAttributeModifier.Operation.ADDITION)

            ))),
            Map.entry(8, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("d20cb235-5ee9-4e93-8214-db8a6dec8591"), "LEVEL_UP_MAX_HEALTH", 14, EntityAttributeModifier.Operation.ADDITION)

            ))),
            Map.entry(9, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("891e43f0-2480-4a89-ab8b-216aee73acd8"), "LEVEL_UP_MAX_HEALTH", 16, EntityAttributeModifier.Operation.ADDITION)

            ))),
            Map.entry(10, new LevelModifierMapping(ImmutableMultimap.of(
                    EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(UUID.fromString("a2b0d8cd-117d-462f-b55d-7aef1947b728"), "LEVEL_UP_MAX_HEALTH", 18, EntityAttributeModifier.Operation.ADDITION)
            )))
    );
    private static final Map<Integer, Integer> HEALING_MAPPING = Map.ofEntries(
            Map.entry(0, 6),
            Map.entry(1, 9),
            Map.entry(2, 12),
            Map.entry(3, 15),
            Map.entry(4, 18),
            Map.entry(5, 21),
            Map.entry(6, 24)
    );

    public static int getNextUpgradeCost(PlayerEntity player) {
        var stack = player.getInventory().main.stream().filter(itemStack -> itemStack.isOf(ItemManager.ESTUS)).findFirst();

        if (stack.isPresent()) {
            var itemstack = stack.get();
            var nbt = itemstack.getOrCreateNbt();
            var upgrade = nbt.getInt("upgrade");
            return UPGRADE_COST_MAPPING.getOrDefault(upgrade + 1, -1);
        }
        return -1;
    }

    public static int getNextDurabilityCost(PlayerEntity player) {
        var stack = player.getInventory().main.stream().filter(itemStack -> itemStack.isOf(ItemManager.ESTUS)).findFirst();

        if (stack.isPresent()) {
            var itemstack = stack.get();
            var nbt = itemstack.getOrCreateNbt();
            var durability = nbt.getInt("enhance");
            return DURABILITY_COST_MAPPING.getOrDefault(durability + 1, -1);
        }
        return -1;
    }

    public static int getNextLevelCost(PlayerEntity player) {
        var currentLevel = LivingEntityBridge.getPersistentData(player).getInt("level");
        return LEVEL_COST_MAPPING.getOrDefault(currentLevel + 1, -1);
    }

    public static LevelModifierMapping getLevelModifiers(int level) {
        return LEVEL_ATTRIBUTE_MAPPING.getOrDefault(level, new LevelModifierMapping());
    }

    public static int getAmountToHealForUpgrade(int upgrade) {
        return HEALING_MAPPING.getOrDefault(upgrade, 0);
    }
}









