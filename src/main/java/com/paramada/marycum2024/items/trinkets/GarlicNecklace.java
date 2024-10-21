package com.paramada.marycum2024.items.trinkets;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.util.Rarity;

public class GarlicNecklace extends TrinketItem {
    public GarlicNecklace() {
        super(new Settings().fireproof().maxCount(1).rarity(Rarity.RARE));
    }
}
