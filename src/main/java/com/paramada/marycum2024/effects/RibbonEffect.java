package com.paramada.marycum2024.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Colors;

public class RibbonEffect extends StatusEffect {
    protected RibbonEffect() {
        super(StatusEffectCategory.BENEFICIAL, Colors.WHITE);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.getStatusEffects().stream().anyMatch((statusEffectInstance -> statusEffectInstance.getEffectType() instanceof RibbonEffect))) {
            entity.removeStatusEffect(this);
        }
        super.onApplied(entity, attributes, amplifier);
    }
}
