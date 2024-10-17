package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.items.trinkets.bases.RibbonTrinket;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.PotionUtil;
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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        var trinketComponent = TrinketsApi.getTrinketComponent((LivingEntity) entity);
        if (trinketComponent.isPresent()) {
            var ribbons = trinketComponent.get().getEquipped(itemStack -> itemStack.getItem() instanceof RibbonTrinket);
            if (ribbons.size() == 0) {
                NbtCompound nbtCompound = stack.getOrCreateNbt();
                NbtList nbtList = nbtCompound.getList("custom_potion_effects", 9);
                nbtCompound.put("custom_potion_effects", nbtList);
                return;
            }

            var ribbon = ribbons.stream().findFirst();
            var cleanRibbon = ((RibbonTrinket) ribbon.get().getRight().getItem());
            PotionUtil.setCustomPotionEffects(stack, cleanRibbon.getEffects());
        }
        super.inventoryTick(stack, world, entity, slot, selected);
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
