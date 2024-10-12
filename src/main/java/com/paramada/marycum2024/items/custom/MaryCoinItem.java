package com.paramada.marycum2024.items.custom;

import net.minecraft.client.item.TooltipType;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import java.util.List;

public class MaryCoinItem extends MaryItem {
    public MaryCoinItem() {
        super(new Settings().fireproof().rarity(Rarity.EPIC).maxCount(64));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.mary_coin"));
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.mary_coin.2"));
    }
}
