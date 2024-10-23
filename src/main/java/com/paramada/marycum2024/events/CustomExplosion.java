package com.paramada.marycum2024.events;

import com.google.common.collect.Sets;
import com.paramada.marycum2024.damages.ModDamageTypes;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

import java.util.List;

public class CustomExplosion extends Explosion {
    private final CustomExplosionBehaviour behavior;
    private final DamageSource damageSource;
    public final World world;
    public final Entity source;
    private final Entity target;

    public CustomExplosion(World world, Entity source, Entity target, float power) {
        this(world, source, target, ModDamageTypes.of(world, ModDamageTypes.CHARGED_PLAYER_ATTACK, source), new CustomExplosionBehaviour(), power, false, Explosion.DestructionType.KEEP);
    }

    public CustomExplosion(World world, Entity source, Entity target, DamageSource damageSource, CustomExplosionBehaviour behavior, float power, boolean createFire, DestructionType destructionType) {
        super(world, target, damageSource, behavior, target.getX(), target.getY(), target.getZ(), power, createFire, destructionType, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.ENTITY_GENERIC_EXPLODE);
        this.world = world;
        this.source = source;
        this.target = target;
        this.behavior = behavior;
        this.damageSource = damageSource;
    }


    @Override
    public void collectBlocksAndDamageEntities() {
        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();

        this.world.emitGameEvent(this.target, GameEvent.EXPLODE, target.getPos());

        this.getAffectedBlocks().addAll(Sets.newHashSet());
        float explosionRadius = this.getPower() * 2.0F;

        int minX = MathHelper.floor(x - (double) explosionRadius - 1.0);
        int maxX = MathHelper.floor(x + (double) explosionRadius + 1.0);

        int minY = MathHelper.floor(y - (double) explosionRadius - 1.0);
        int maxY = MathHelper.floor(y + (double) explosionRadius + 1.0);
        int minZ = MathHelper.floor(z - (double) explosionRadius - 1.0);
        int maxZ = MathHelper.floor(z + (double) explosionRadius + 1.0);

        List<Entity> entities = this.world.getOtherEntities(source, new Box(minX, minY, minZ, maxX, maxY, maxZ));

        var onlyPushableEntities = entities.stream().filter(entity -> {
            var distance3D = Math.sqrt(target.squaredDistanceTo(entity.getEyePos()));
            if (entity instanceof TntEntity) {
                distance3D = entity.distanceTo(target);
            }
            return entity.distanceTo(this.target) <= explosionRadius && distance3D > 0 && !entity.isImmuneToExplosion(this);
        });

        onlyPushableEntities.forEach(entity -> {

            var distance3D = entity.distanceTo(target);
            var distanceRate = distance3D / explosionRadius;

            if (this.behavior.shouldDamage(this, entity)) {
                entity.damage(this.damageSource, getPower());
            }

            var exposureTerminalSpeed = 0.2;
            var exposureSpeed = exposureTerminalSpeed * (1 - distanceRate);
            var speedVector = new Vec3d(exposureSpeed, exposureSpeed, exposureSpeed);
            entity.setVelocity(entity.getVelocity().add(speedVector));

            if (entity instanceof PlayerEntity playerEntity && playerEntity.isCreative() && playerEntity.getAbilities().flying && playerEntity.isSpectator()) {
                this.getAffectedPlayers().put(playerEntity, speedVector);
            }
        });
    }


    @Override
    public String toString() {
        return this.world + " explosion on " + this.getPosition();
    }
}
