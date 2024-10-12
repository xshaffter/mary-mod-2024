package com.paramada.marycum2024.items.custom.materials;

import com.paramada.marycum2024.items.ItemManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class MaryMaterial implements ArmorMaterial {

    @Override
    public int getDurability(ArmorItem.Type type) {
        return 0;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofStacks(new ItemStack(ItemManager.MARY_COIN));
    }

    @Override
    public String getName() {
        return "Mary material";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 10;
    }
}
