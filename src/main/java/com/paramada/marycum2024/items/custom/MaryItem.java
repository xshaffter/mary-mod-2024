package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import java.util.List;


public class MaryItem extends Item {
    public MaryItem(Settings settings) {
        super(settings);
    }

    public MaryItem() {
        this(
                new Settings()
                        .rarity(Rarity.COMMON)
                        .maxCount(16)
                        .fireproof()
        );
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var className = this.getClass().getSimpleName().toLowerCase();
        var tooltipLang = "tooltip.%s.%s".formatted(MaryMod2024.MOD_ID, className);
        var translation = Text.translatable(tooltipLang);
        if (translation.contains(Text.literal(tooltipLang))) {
            tooltip.add(Text.translatable("tooltip.mary-mod-2024.default_text"));
        } else {
            tooltip.add(translation);
        }
    }
}