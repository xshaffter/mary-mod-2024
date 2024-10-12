package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.MaryMod2024;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


import java.util.List;


public class MaryItem extends Item implements IMaryItem {
    public MaryItem(Settings settings) {
        super(settings);
    }

    public MaryItem() {
        this(
                new FabricItemSettings()
                        .rarity(Rarity.COMMON)
                        .maxCount(16)
                        .fireproof()
        );
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipType context) {
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