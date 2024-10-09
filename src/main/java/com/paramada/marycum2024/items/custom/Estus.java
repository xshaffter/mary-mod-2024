package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.util.LivingEntityBridge;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import dev.kosmx.playerAnim.mixin.firstPerson.CameraAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Estus extends PotionItem {
    public Estus(int durability) {
        super(
                new Settings()
                        .fireproof()
                        .rarity(Rarity.EPIC)
                        .maxDamage(durability)
        );

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (stack.getDamage() == stack.getMaxDamage()) {
            return TypedActionResult.fail(stack);
        }

        PlayerEntityBridge.startEstusHealing(user, hand);
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        PlayerEntityBridge.stopAnimation((PlayerEntity) user);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var blockstate = context.getWorld().getBlockState(context.getBlockPos());
        var world = context.getWorld();
        var playerEntity = context.getPlayer();
        if (blockstate.isOf(Blocks.CAMPFIRE) && blockstate.get(CampfireBlock.LIT)) {
            context.getStack().setDamage(0);
            if (!world.isClient()) {
                world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, context.getBlockPos(), 0);
            }
            CampfireBlock.extinguish(context.getPlayer(), world, context.getBlockPos(), blockstate);
            var blockState3 = blockstate.with(CampfireBlock.LIT, false);

            if (!world.isClient) {
                world.setBlockState(context.getBlockPos(), blockState3, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(playerEntity, blockState3));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            assert stack.getNbt() != null;
            int modifier = LivingEntityBridge.getPersistentData(user).getInt("upgrade");
            float healing = (10 + (5 * modifier));
            if (user instanceof PlayerEntity player) {
                if (!player.isCreative()) {
                    if (stack.getDamage() != stack.getMaxDamage() - 1) {
                        player.getItemCooldownManager().set(this, 12 * 20 + 10);
                    }
                    stack.setDamage(stack.getDamage() + 1);
                }
            } else {
                stack.setDamage(stack.getDamage() + 1);
            }
            user.heal(healing);
        }

        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (world != null && world.isClient) {
            int modifier = LivingEntityBridge.getPersistentData(MinecraftClient.getInstance().player).getInt("upgrade");
            double healing = 5 + (5 * modifier) / 2.0;
            var translatable_text = Text.translatable("tooltip.mary-mod-2024.estus_healing").getString();
            tooltip.add(Text.literal(translatable_text.formatted(healing)));
        }
    }
}
