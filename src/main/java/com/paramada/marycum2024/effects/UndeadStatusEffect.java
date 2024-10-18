package com.paramada.marycum2024.effects;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
        if (this.equals(ModEffects.VAMPIRISM) && world.isSkyVisible(blockPos) && world.isDay() && world.getTime() % (MaryMod2024.TICKS_PER_SECOND * 4) == 0) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, MaryMod2024.TICKS_PER_SECOND * 5));
        }
    }
}
