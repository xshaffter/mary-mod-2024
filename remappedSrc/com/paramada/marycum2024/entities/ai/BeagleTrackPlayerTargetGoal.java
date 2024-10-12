package com.paramada.marycum2024.entities.ai;

import com.paramada.marycum2024.entities.custom.BeagleEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;

public class BeagleTrackPlayerTargetGoal extends TrackTargetGoal {
    private final BeagleEntity entity;
    private LivingEntity attacking;
    private int lastAttackTime;

    public BeagleTrackPlayerTargetGoal(BeagleEntity mob) {
        super(mob, false);
        this.entity = mob;
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.entity.getOwner();
        if (livingEntity == null) {
            return false;
        } else {
            this.attacking = livingEntity.getAttacking();
            int i = livingEntity.getLastAttackTime();
            return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.entity.canAttackWithOwner(this.attacking, livingEntity);
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacking);
        LivingEntity livingEntity = this.entity.getOwner();
        if (livingEntity != null) {
            this.lastAttackTime = livingEntity.getLastAttackTime();
        }

        super.start();
    }
}
