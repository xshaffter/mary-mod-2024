package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.util.LivingEntityBridge;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PotionItem;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MedikaPotion extends PotionItem implements IMaryItem {
    public MedikaPotion() {
        super(
                new Settings()
                        .fireproof()
                        .rarity(Rarity.EPIC)
                        .maxDamage(1)
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
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            assert stack.getNbt() != null;
            if (user instanceof PlayerEntity player) {
                if (!player.isCreative()) {
                    stack.setDamage(stack.getDamage() + 1);
                }
            } else {
                stack.setDamage(stack.getDamage() + 1);
            }
            applyRibbonEffects(user);
        }

        return stack;
    }

    private void applyRibbonEffects(LivingEntity player) {
        if (player.hasStatusEffect(ModEffects.BLACK_RIBBON_EFFECT)) {
            for (StatusEffectInstance effect : ModEffects.BLACK_RIBBON_EFFECT.getEffects()) {
                player.addStatusEffect(effect);
            }
        } else if (player.hasStatusEffect(ModEffects.BLUE_RIBBON_EFFECT)) {
            for (StatusEffectInstance effect : ModEffects.BLUE_RIBBON_EFFECT.getEffects()) {
                player.addStatusEffect(effect);
            }
        } else if (player.hasStatusEffect(ModEffects.CYAN_RIBBON_EFFECT)) {
            for (StatusEffectInstance effect : ModEffects.CYAN_RIBBON_EFFECT.getEffects()) {
                player.addStatusEffect(effect);
            }
        } else if (player.hasStatusEffect(ModEffects.PINK_RIBBON_EFFECT)) {
            for (StatusEffectInstance effect : ModEffects.PINK_RIBBON_EFFECT.getEffects()) {
                player.addStatusEffect(effect);
            }
        } else if (player.hasStatusEffect(ModEffects.GREEN_RIBBON_EFFECT)) {
            for (StatusEffectInstance effect : ModEffects.GREEN_RIBBON_EFFECT.getEffects()) {
                player.addStatusEffect(effect);
            }
        } else if (player.hasStatusEffect(ModEffects.RED_RIBBON_EFFECT)) {
            for (StatusEffectInstance effect : ModEffects.RED_RIBBON_EFFECT.getEffects()) {
                player.addStatusEffect(effect);
            }
        }

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (world != null && world.isClient) {
            int modifier = LivingEntityBridge.getPersistentData(MinecraftClient.getInstance().player).getInt("upgrade");
            double healing = 5 + (5 * modifier) / 2.0;
            var translatable_text = Text.translatable("tooltip.mary-mod-2024.estus_healing").getString();
            tooltip.add(Text.literal(translatable_text.formatted(healing)));

            assert MinecraftClient.getInstance().player != null;
            if (MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.BLACK_RIBBON_EFFECT)) {
                tooltip.add(Text.translatable("tooltip.mary-mod-2024.black_ribbon_effect"));
            } else if (MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.BLUE_RIBBON_EFFECT)) {
                tooltip.add(Text.translatable("tooltip.mary-mod-2024.blue_ribbon_effect"));
            } else if (MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.CYAN_RIBBON_EFFECT)) {
                tooltip.add(Text.translatable("tooltip.mary-mod-2024.cyan_ribbon_effect"));
            } else if (MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.PINK_RIBBON_EFFECT)) {
                tooltip.add(Text.translatable("tooltip.mary-mod-2024.pink_ribbon_effect"));
            } else if (MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.GREEN_RIBBON_EFFECT)) {
                tooltip.add(Text.translatable("tooltip.mary-mod-2024.green_ribbon_effect"));
            } else if (MinecraftClient.getInstance().player.hasStatusEffect(ModEffects.RED_RIBBON_EFFECT)) {
                tooltip.add(Text.translatable("tooltip.mary-mod-2024.red_ribbon_effect"));
            }
        }
    }
}
