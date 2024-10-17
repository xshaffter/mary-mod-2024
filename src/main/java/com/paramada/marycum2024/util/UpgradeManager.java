package com.paramada.marycum2024.util;

import com.paramada.marycum2024.items.ItemManager;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;

public class UpgradeManager {
    private static final Map<Integer, Integer> upgradeCostMapping = Map.ofEntries(
            Map.entry(1, 10),
            Map.entry(2, 50),
            Map.entry(3, 100),
            Map.entry(4, 150),
            Map.entry(5, 200),
            Map.entry(6, 250)
    );
    private static final Map<Integer, Integer> durabilityCostMapping = Map.ofEntries(
            Map.entry(1, 150),
            Map.entry(2, 100),
            Map.entry(3, 100),
            Map.entry(4, 150),
            Map.entry(5, 200),
            Map.entry(6, 250)
    );

    public static int getNextUpgradeCost(PlayerEntity player) {
        var stack = player.getInventory().main.stream().filter(itemStack -> itemStack.isOf(ItemManager.ESTUS)).findFirst();

        if (stack.isPresent()) {
            var itemstack = stack.get();
            var nbt = itemstack.getOrCreateNbt();
            var upgrade = nbt.getInt("upgrade");
            return upgradeCostMapping.getOrDefault(upgrade + 1, -1);
        }
        return -1;
    }

    public static int getNextDurabilityCost(PlayerEntity player) {
        var stack = player.getInventory().main.stream().filter(itemStack -> itemStack.isOf(ItemManager.ESTUS)).findFirst();

        if (stack.isPresent()) {
            var itemstack = stack.get();
            var nbt = itemstack.getOrCreateNbt();
            var durability = nbt.getInt("enhance");
            return durabilityCostMapping.getOrDefault(durability + 1, -1);
        }
        return 1;
    }
}
