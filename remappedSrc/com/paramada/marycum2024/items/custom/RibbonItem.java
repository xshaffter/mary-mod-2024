package com.paramada.marycum2024.items.custom;

import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RibbonItem extends MaryItem {
    private final List<StatusEffectInstance> effects;
    private final List<StatusEffectInstance> interactEffects;

    public RibbonItem(List<StatusEffectInstance> areaEffects, List<StatusEffectInstance> interactEffects) {
        super(new Settings().fireproof().rarity(Rarity.EPIC).maxCount(1));
        this.effects = areaEffects;

        this.interactEffects = interactEffects;
    }

    public RibbonItem() {
        super(new Settings().fireproof().rarity(Rarity.EPIC).maxCount(1));
        this.effects = List.of();
        this.interactEffects = List.of();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipType context) {
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.ribbon"));
    }

    public List<StatusEffectInstance> getEffects() {
        return effects;
    }

    public List<StatusEffectInstance> getInteractEffects() {
        return interactEffects;
    }
}
