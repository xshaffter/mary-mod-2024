package com.paramada.marycum2024.items.custom.potions;

import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.items.custom.IMaryItem;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import com.paramada.marycum2024.util.functionality.bridges.UpgradeManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class ReusablePotion extends PotionItem implements IMaryItem {
    public ReusablePotion() {
        super(
                new Settings()
                        .fireproof()
                        .rarity(Rarity.EPIC)
                        .maxDamage(1)
        );
    }

    @Override
    public Text getName(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();
        var upgrade = nbt.getInt("upgrade");
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

        PlayerEntityBridge.startEstusHealing(user);
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        var nbt = stack.getOrCreateNbt();
        var upgrade = nbt.getInt("upgrade");

        var amountToHeal = UpgradeManager.getAmountToHealForUpgrade(upgrade);

        PotionUtil.setCustomPotionEffects(stack, List.of(
                new StatusEffectInstance(ModEffects.INSTANT_HEAL, 1, amountToHeal)
        ));

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        PlayerEntityBridge.stopAnimation((PlayerEntity) user);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient) {
            return stack;
        }

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
        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

}
