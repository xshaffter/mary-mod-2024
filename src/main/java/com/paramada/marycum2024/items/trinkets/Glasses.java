package com.paramada.marycum2024.items.trinkets;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.util.Rarity;

public class Glasses extends TrinketItem {
    public Glasses() {
        super(new Settings().maxCount(1).rarity(Rarity.RARE).fireproof());
    }

}
