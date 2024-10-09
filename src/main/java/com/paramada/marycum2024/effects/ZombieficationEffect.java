package com.paramada.marycum2024.effects;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Colors;
import net.minecraft.world.LightType;

public class ZombieficationEffect extends StatusEffect {
    protected ZombieficationEffect() {
        super(StatusEffectCategory.HARMFUL, Colors.RED);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        var world = entity.getWorld();
        var blockPos = entity.getBlockPos();
        var skyLight = world.getLightLevel(LightType.SKY, blockPos);
        if (skyLight > 0) {
            var tickLenght = (int) ((MaryMod2024.TICKS_PER_SECOND * 4.0) / skyLight);
            if (world.getTime() % tickLenght == 0) {
                entity.damage(entity.getDamageSources().magic(), amplifier);
            }
        }
    }
}
