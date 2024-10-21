package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.effects.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class TDAHPillItem extends Item {
    public TDAHPillItem() {
        super(new Item.Settings().fireproof().rarity(Rarity.RARE).food(
                new FoodComponent.Builder()
                        .alwaysEdible()
                        .hunger(0)
                        .snack()
                        .statusEffect(new StatusEffectInstance(ModEffects.NEUROTYPICAL, MaryMod2024.TICKS_PER_SECOND * 120, 0), 1)
                        .build()
        ));
    }
}
