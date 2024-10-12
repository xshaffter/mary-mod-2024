package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.events.SoundManager;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.PotionItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Bandage extends PotionItem implements IMaryItem {
    public Bandage() {
        super(
                new Settings()
                        .fireproof()
                        .rarity(Rarity.COMMON)
                        .maxCount(16)
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        PlayerEntityBridge.starBandagetHealing(user);
        user.playSound(SoundManager.BANDAGE_HEAL, 1, 1);
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity player = (PlayerEntity) user;

        player.getItemCooldownManager().set(this, 12 * 20 + 10);
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 12 * 20 + 10, 1));

        stack.decrement(1);
        player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1, 1);
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        PlayerEntityBridge.stopAnimation((PlayerEntity) user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipType context) {
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.bandage"));
    }
}
