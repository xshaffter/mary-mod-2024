package com.paramada.marycum2024.events;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class CustomExplosionBehaviour extends ExplosionBehavior {


    @Override
    public boolean shouldDamage(Explosion explosion, Entity entity) {
        if (explosion instanceof CustomExplosion customExplosion && customExplosion.source == entity) {
            return false;
        }
        return super.shouldDamage(explosion, entity);
    }

    @Override
    public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
        return false;
    }
}
