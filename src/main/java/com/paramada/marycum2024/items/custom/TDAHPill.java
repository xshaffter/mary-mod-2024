package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.items.trinkets.Glasses;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

public class TDAHPill extends Item {
    public TDAHPill() {
        super(new Item.Settings().fireproof().rarity(Rarity.RARE).food(
                new FoodComponent.Builder()
                        .alwaysEdible()
                        .hunger(0)
                        .snack()
                        .statusEffect(new StatusEffectInstance(ModEffects.NEUROTYPICAL, MaryMod2024.TICKS_PER_SECOND * 120, 0), 1)
                        .build()
        ));
    }


    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        var trinketComponent = TrinketsApi.getTrinketComponent(user);
        if (trinketComponent.isPresent()) {
            var comp = trinketComponent.get();
            if (comp.isEquipped(ItemManager.GLASSES)) {
                return super.finishUsing(stack, world, user);
            }
        }
        stack.decrement(1);
        return stack;
    }
}
