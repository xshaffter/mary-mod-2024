package com.paramada.marycum2024.entities.ai;

import com.paramada.marycum2024.entities.custom.BeagleEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class BeagleFollowPlayerGoal extends FollowParentGoal {
    private final BeagleEntity animal;
    private LivingEntity parent;

    public BeagleFollowPlayerGoal(BeagleEntity animal, double speed) {
        super(animal, speed);
        this.animal = animal;
    }

    @Override
    public boolean canStart() {
        List<? extends PlayerEntity> list = this.animal
                .method_48926()
                .getNonSpectatingEntities(PlayerEntity.class, this.animal.getBoundingBox().expand(8.0, 4.0, 8.0));
        PlayerEntity parent = list.stream().filter((player -> player.getName().getString().equalsIgnoreCase("maryblog"))).findFirst().orElse(null);
        if (parent == null) {
            return false;
        }
        this.parent = parent;
        return true;
    }
}
