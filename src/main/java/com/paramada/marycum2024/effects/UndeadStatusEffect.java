package com.paramada.marycum2024.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Colors;

public abstract class UndeadStatusEffect extends StatusEffect {
    protected UndeadStatusEffect() {
        super(StatusEffectCategory.HARMFUL, Colors.RED);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        var world = entity.getWorld();
        if (world.isClient()) {
            return;
        }
        var blockPos = entity.getBlockPos();
        if (world.isSkyVisible(blockPos) && world.isDay() && entity.getFireTicks() < 5) {
            entity.setOnFireFor(5);
        }
    }
}
