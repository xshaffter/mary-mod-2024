package com.paramada.marycum2024.attributes;

import com.paramada.marycum2024.MaryMod2024;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityAttributes {

    public static final EntityAttribute DISTANCE_DAMAGE_MULTIPLIER = new ClampedEntityAttribute("attribute.name.generic.distance_damage_multiplier", 1, 0, 2).setTracked(true);


    private static void registerAttribute(final String name, final EntityAttribute entityAttribute) {
        Registry.register(Registries.ATTRIBUTE, new Identifier(MaryMod2024.MOD_ID, name), entityAttribute);
        FabricDefaultAttributeRegistry.register(EntityType.PLAYER, PlayerEntity.createPlayerAttributes().add(entityAttribute));
    }
    public static void registerAttributes() {
        registerAttribute("generic.distance_damage_multiplier", DISTANCE_DAMAGE_MULTIPLIER);
    }
}
