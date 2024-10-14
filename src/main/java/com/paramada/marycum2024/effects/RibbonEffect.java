package com.paramada.marycum2024.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Colors;

import java.util.List;

public class RibbonEffect extends StatusEffect {
    private final List<StatusEffectInstance> effects;
    private final String id;

    protected RibbonEffect(List<StatusEffectInstance> effects, String id) {
        super(StatusEffectCategory.BENEFICIAL, Colors.WHITE);
        this.effects = effects;
        this.id = id;
    }
    protected RibbonEffect(String id) {
        this(List.of(), id);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (entity.getStatusEffects().stream().anyMatch((
                statusEffectInstance -> statusEffectInstance.getEffectType() instanceof RibbonEffect &&
                        statusEffectInstance.getEffectType() != this
                ))) {
            entity.removeStatusEffect(this);
        }
        super.onApplied(entity, amplifier);
    }

    public List<StatusEffectInstance> getEffects() {
        return effects;
    }

    public String getId() {
        return this.id;
    }
}
