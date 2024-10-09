package com.paramada.marycum2024.entities.custom;

import com.paramada.marycum2024.entities.ModEntities;
import com.paramada.marycum2024.entities.ai.BeagleAttackGoal;
import com.paramada.marycum2024.entities.ai.BeagleAutoFindEnemiesGoal;
import com.paramada.marycum2024.entities.ai.BeagleFollowPlayerGoal;
import com.paramada.marycum2024.entities.ai.BeagleTrackPlayerTargetGoal;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BeagleEntity extends AnimalEntity {
    public static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(BeagleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> BARKING = DataTracker.registerData(BeagleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState barkAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int barkAnimationTimeout = 0;
    public int biteAnimationTimeout = 0;

    public BeagleEntity(EntityType<? extends BeagleEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public boolean isBarking() {
        return this.dataTracker.get(BARKING);
    }

    public void setBarking(boolean barking) {
        this.dataTracker.set(BARKING, true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(BARKING, false);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        if (isAttacking() && this.biteAnimationTimeout <= 0) {
            this.biteAnimationTimeout = 40;
            this.biteAnimationState.start(this.age);
        } else {
            --this.biteAnimationTimeout;
        }
        if (!isAttacking()) {
            biteAnimationState.stop();
        }

        if (isBarking() && this.barkAnimationTimeout <= 0) {
            this.barkAnimationTimeout = 40;
            this.barkAnimationState.start(this.age);
        } else {
            --this.barkAnimationTimeout;
        }
        if (!isBarking()) {
            barkAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BeagleAttackGoal(this, 1, true));
        this.goalSelector.add(2, new BeagleAutoFindEnemiesGoal(this));
        this.goalSelector.add(3, new BeagleFollowPlayerGoal(this, 1.15));

        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1d));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new BeagleTrackPlayerTargetGoal(this));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, HostileEntity.class, false));
    }

    public static DefaultAttributeContainer.Builder createBeagleAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, Integer.MAX_VALUE)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
                .add(EntityAttributes.GENERIC_ARMOR, 99999);
    }

    @Override
    public BeagleEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.BEAGLE.create(world);
    }

    public LivingEntity getOwner() {
        List<? extends PlayerEntity> list = this
                .getWorld()
                .getNonSpectatingEntities(PlayerEntity.class, this.getBoundingBox().expand(8.0, 4.0, 8.0));
        return list.stream().filter((player -> player.getName().getString().equalsIgnoreCase("maryblog") || list.get(0) == player)).findFirst().orElse(null);
    }

    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (target instanceof CreeperEntity || target instanceof GhastEntity) {
            return false;
        } else if (target instanceof WolfEntity wolfEntity) {
            return !wolfEntity.isTamed() || wolfEntity.getOwner() != owner;
        } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).shouldDamagePlayer((PlayerEntity) target)) {
            return false;
        } else {
            return (!(target instanceof AbstractHorseEntity) || !((AbstractHorseEntity) target).isTame()) && (!(target instanceof TameableEntity) || !((TameableEntity) target).isTamed());
        }
    }

}
