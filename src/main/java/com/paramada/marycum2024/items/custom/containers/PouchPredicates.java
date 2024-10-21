package com.paramada.marycum2024.items.custom.containers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;

import java.util.function.Predicate;

public class PouchPredicates {
    public static final Predicate<ItemStack> POTION_PREDICATE = stack -> stack.getItem() instanceof PotionItem;
}
