package com.paramada.marycum2024.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Colors;

public class NeurotypicalEffect extends StatusEffect {

    private NeurotypicalEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public NeurotypicalEffect() {
        this(StatusEffectCategory.BENEFICIAL, Colors.WHITE);
    }

}
