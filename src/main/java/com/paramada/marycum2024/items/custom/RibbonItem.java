package com.paramada.marycum2024.items.custom;

<<<<<<< Updated upstream
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffect;
=======
import net.minecraft.client.item.TooltipType;
>>>>>>> Stashed changes
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import java.util.List;

public class RibbonItem extends MaryItem {
    private final List<StatusEffectInstance> effects;
    private final StatusEffect potionEffect;

    public RibbonItem(List<StatusEffectInstance> effects, StatusEffect potionEffect) {
        super(new Settings().fireproof().rarity(Rarity.EPIC).maxCount(1));
        this.effects = effects;

        this.potionEffect = potionEffect;
    }

    public RibbonItem() {
        super(new Settings().fireproof().rarity(Rarity.EPIC).maxCount(1));
        this.effects = List.of();
        this.potionEffect = null;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.ribbon"));
    }

    public List<StatusEffectInstance> getEffects() {
        return effects;
    }

    public StatusEffect getPotionUpgrade() {
        return potionEffect;
    }
}
