package com.paramada.marycum2024.items.trinkets.bases;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Rarity;

import java.util.List;

public class RibbonTrinket extends TrinketItem {

    private final List<StatusEffectInstance> effects;
    public RibbonTrinket(Rarity rarity, List<StatusEffectInstance> effects) {
        super(new Settings().maxCount(1).rarity(rarity).fireproof());
        this.effects = effects;
    }

    public RibbonTrinket() {
        this(Rarity.COMMON, List.of());
    }

    public List<StatusEffectInstance> getEffects() {
        return effects;
    }
}
