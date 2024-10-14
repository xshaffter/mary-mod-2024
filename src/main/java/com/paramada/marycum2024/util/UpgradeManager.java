package com.paramada.marycum2024.util;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class UpgradeManager {
    private static final Map<Integer, Integer> costMapping = Map.ofEntries(
            Map.entry(1, 10),
            Map.entry(2, 50),
            Map.entry(3, 100),
            Map.entry(4, 150),
            Map.entry(5, 200),
            Map.entry(6, 250)
    );

    public static int getNextUpgradeCost(PlayerEntity player) {
        var upgrade = LivingEntityBridge.getPersistentData(player).getInt("upgrade");
        return costMapping.getOrDefault(upgrade + 1, -1);
    }
}
