package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.items.trinkets.bases.RibbonTrinket;
import com.paramada.marycum2024.util.LivingEntityBridge;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ReusablePotion extends PotionItem implements IMaryItem {
    public ReusablePotion(int durability) {
        super(
                new Settings()
                        .fireproof()
                        .rarity(Rarity.EPIC)
                        .maxDamage(durability)
        );
    }

    @Override
    public Text getName(ItemStack stack) {
        var upgrade = LivingEntityBridge.getPersistentData(MinecraftClient.getInstance().player).getInt("upgrade");
        var upgradeText = upgrade > 0 ? " (+%d)".formatted(upgrade) : "";
        MutableText text = (MutableText) super.getName(stack);
        return text.append(upgradeText);
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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        int modifier = LivingEntityBridge.getPersistentData(entity).getInt("upgrade");
        int healingAmplifier = modifier + 1;

        PotionUtil.setCustomPotionEffects(stack, List.of(
                new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 0, healingAmplifier)
        ));

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        PlayerEntityBridge.stopAnimation((PlayerEntity) user);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
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

            PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
            List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);
            for (var effect : list) {
                if (effect.getEffectType().isInstant()) {
                    effect.getEffectType().applyInstantEffect(playerEntity, playerEntity, user, effect.getAmplifier(), 1.0);
                } else {
                    user.addStatusEffect(new StatusEffectInstance(effect));
                }
            }
        }

        return stack;
    }
}
