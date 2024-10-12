package com.paramada.marycum2024.entities.ai;

import com.paramada.marycum2024.entities.custom.BeagleEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.sound.SoundEvents;

import java.util.List;

public class BeagleAutoFindEnemiesGoal extends Goal {

    private static final int TICKS_PER_SECOND = 20;
    private static final int LOCALIZATION_DURATION_SECONDS = 3;
    private final BeagleEntity entity;
    public static final int BARK_COOLDOWN = TICKS_PER_SECOND * 5;
    public static final double EFFECT_RANGE = 16;
    private int barkAnimationDelay = 4;
    private int ticksUntilNextBark = BARK_COOLDOWN;
    private boolean shouldCountTillNextBark = false;

    public BeagleAutoFindEnemiesGoal(BeagleEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        bark();
        if (shouldCountTillNextBark) {
            this.ticksUntilNextBark = Math.max(this.ticksUntilNextBark - 1, 0);
        }
    }

    private void resetBarkCooldown() {
        this.ticksUntilNextBark = this.getTickCount(BARK_COOLDOWN);
    }

    protected void performBark() {
        this.resetBarkCooldown();
        this.entity.playSound(SoundEvents.ENTITY_WOLF_AMBIENT, 1f, 1f);

        List<? extends HostileEntity> list = this
                .entity
                .method_48926()
                .getNonSpectatingEntities(HostileEntity.class, this.entity.getBoundingBox().expand(EFFECT_RANGE, EFFECT_RANGE, EFFECT_RANGE));
        list.forEach((enemy) -> {
            enemy.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, TICKS_PER_SECOND * LOCALIZATION_DURATION_SECONDS));
        });
    }

    protected boolean isTimeToBark() {
        return this.ticksUntilNextBark <= 0;
    }

    protected boolean isTimeToStartBarkAnimation() {
        return this.ticksUntilNextBark <= barkAnimationDelay;
    }

    protected void bark() {
        List<? extends HostileEntity> list = this
                .entity
                .method_48926()
                .getNonSpectatingEntities(HostileEntity.class, this.entity.getBoundingBox().expand(EFFECT_RANGE, EFFECT_RANGE, EFFECT_RANGE))
                .stream().filter((entity) -> !entity.hasStatusEffect(StatusEffects.GLOWING)).toList();
        if (list.size() > 0) {
            shouldCountTillNextBark = true;

            if (isTimeToStartBarkAnimation()) {
                entity.setBarking(true);
            }

            if (isTimeToBark()) {
                performBark();
            }
        } else {
            resetBarkCooldown();
            shouldCountTillNextBark = false;
            entity.setBarking(false);
            entity.biteAnimationTimeout = 0;
        }
    }

    @Override
    public void start() {
        super.start();
        barkAnimationDelay = 4;
        ticksUntilNextBark = 60;
    }

    @Override
    public void stop() {
        super.stop();
    }
}
