package com.paramada.marycum2024.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Colors;

public class RibbonEffect extends StatusEffect {
<<<<<<< Updated upstream
    protected RibbonEffect() {
        super(StatusEffectCategory.BENEFICIAL, Colors.WHITE);
=======
    private final List<StatusEffectInstance> effects;
    private String id;

    protected RibbonEffect(List<StatusEffectInstance> effects, String id) {
        super(StatusEffectCategory.BENEFICIAL, Colors.WHITE);
        this.effects = effects;
        this.id = id;
    }
    protected RibbonEffect(String id) {
        super(StatusEffectCategory.BENEFICIAL, Colors.WHITE);
        this.effects = List.of();
        this.id = id;
>>>>>>> Stashed changes
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (entity.getStatusEffects().stream().anyMatch((statusEffectInstance -> statusEffectInstance.getEffectType().value() instanceof RibbonEffect))) {
            entity.removeStatusEffect(this);
        }
        super.onApplied(entity, amplifier);
    }
<<<<<<< Updated upstream
=======

    public List<StatusEffectInstance> getEffects() {
        return effects;
    }

    public String getId() {
        return this.id;
    }
>>>>>>> Stashed changes
}
