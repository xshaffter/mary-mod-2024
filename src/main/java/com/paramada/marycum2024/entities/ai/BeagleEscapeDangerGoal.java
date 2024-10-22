package com.paramada.marycum2024.entities.ai;


import com.paramada.marycum2024.entities.custom.BeagleEntity;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;

public class BeagleEscapeDangerGoal extends EscapeDangerGoal {
    public BeagleEscapeDangerGoal(BeagleEntity entity, double speed) {
        super(entity, speed);
    }

    protected boolean isInDanger() {
        return this.mob.shouldEscapePowderSnow() || this.mob.isOnFire();
    }
}
