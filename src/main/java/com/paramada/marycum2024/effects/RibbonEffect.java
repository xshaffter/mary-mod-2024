package com.paramada.marycum2024.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Colors;

import java.util.List;

public class RibbonEffect extends StatusEffect {
    private final List<StatusEffectInstance> effects;

    protected RibbonEffect(List<StatusEffectInstance> effects) {
        super(StatusEffectCategory.BENEFICIAL, Colors.WHITE);
        this.effects = effects;
    }
    protected RibbonEffect() {
        super(StatusEffectCategory.BENEFICIAL, Colors.WHITE);
        this.effects = List.of();
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (entity.getStatusEffects().stream().anyMatch((statusEffectInstance -> statusEffectInstance.getEffectType() instanceof RibbonEffect))) {
            entity.removeStatusEffect(this);
        }
        super.onApplied(entity, amplifier);
    }

    public List<StatusEffectInstance> getEffects() {
        return effects;
    }
}
