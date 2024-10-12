package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.effects.RibbonEffect;
import com.paramada.marycum2024.util.LivingEntityBridge;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.PotionItem;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

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

        var ribbons = player.getActiveStatusEffects().values().stream().filter(statusEffectInstance -> statusEffectInstance.getEffectType().value() instanceof RibbonEffect);
        var ribbon = ribbons.map(statusEffectInstance -> (RibbonEffect) statusEffectInstance.getEffectType().value()).findFirst();
        if (ribbon.isPresent()) {
            for (StatusEffectInstance effect : ribbon.get().getEffects()) {
                player.addStatusEffect(effect);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int modifier = LivingEntityBridge.getPersistentData(MinecraftClient.getInstance().player).getInt("upgrade");
        double healing = 5 + (5 * modifier) / 2.0;
        var translatable_text = Text.translatable("tooltip.mary-mod-2024.estus_healing").getString();
        tooltip.add(Text.literal(translatable_text.formatted(healing)));

        assert MinecraftClient.getInstance().player != null;

        var player = MinecraftClient.getInstance().player;
        var ribbons = player.getActiveStatusEffects().values().stream().filter(statusEffectInstance -> statusEffectInstance.getEffectType().value() instanceof RibbonEffect);
        var ribbon = ribbons.map(statusEffectInstance -> (RibbonEffect) statusEffectInstance.getEffectType().value()).findFirst();
        if (ribbon.isPresent()) {
            var activeRibbon = ribbon.get();
            tooltip.add(Text.translatable("tooltip.mary-mod-2024.%s".formatted(activeRibbon.getId())));
        }
    }
}
