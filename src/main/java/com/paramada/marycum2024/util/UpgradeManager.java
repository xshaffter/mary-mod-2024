package com.paramada.marycum2024.util;

import com.paramada.marycum2024.items.ItemManager;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;

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

    public static int getAmountToHealForUpgrade(int upgrade) {
        return HEALING_MAPPING.getOrDefault(upgrade, 0);
    }
}
