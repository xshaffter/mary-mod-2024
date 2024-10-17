package com.paramada.marycum2024.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Colors;

public class InstantHealEffect extends InstantStatusEffect {

    protected InstantHealEffect() {
        super(StatusEffectCategory.BENEFICIAL, Colors.LIGHT_RED);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.heal(amplifier);
    }
}
