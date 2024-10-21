package com.paramada.marycum2024.items.trinkets.bases;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.UUID;

public class RibbonTrinket extends TrinketItem {

    private final List<StatusEffectInstance> effects;
    private final Multimap<EntityAttribute, EntityAttributeModifier> modifiers;

    public RibbonTrinket(Rarity rarity, List<StatusEffectInstance> effects, Multimap<EntityAttribute, EntityAttributeModifier> modifiers) {
        super(new Settings().maxCount(1).rarity(rarity).fireproof());
        this.effects = effects;
        this.modifiers = modifiers;
    }
    public RibbonTrinket(Rarity rarity, List<StatusEffectInstance> effects) {
        this(rarity, effects, ImmutableMultimap.of());
    }
    public RibbonTrinket(Rarity rarity, Multimap<EntityAttribute, EntityAttributeModifier> modifiers) {
        this(rarity, List.of(), modifiers);
    }

    public RibbonTrinket() {
        this(Rarity.COMMON, List.of(), ImmutableMultimap.of());
    }

    public List<StatusEffectInstance> getEffects() {
        return effects;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);

        modifiers.putAll(this.modifiers);

        return modifiers;
    }
}
