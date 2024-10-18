package com.paramada.marycum2024.util;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public record LevelModifierMapping(Multimap<EntityAttribute, EntityAttributeModifier> modifiers) {
    public static final LevelModifierMapping EMPTY = new LevelModifierMapping();

    public LevelModifierMapping() {
        this(ImmutableMultimap.of());
    }
}
