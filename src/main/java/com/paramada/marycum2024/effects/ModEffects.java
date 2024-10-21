package com.paramada.marycum2024.effects;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModEffects {
    public static final StatusEffect ZOMBIEFICATION = new ZombieficationEffect();
    public static final StatusEffect VAMPIRISM = new VampirismEffect();
    public static final StatusEffect INSTANT_HEAL = new InstantHealEffect();
    public static final StatusEffect NEUROTYPICAL = new NeurotypicalEffect();

    private static void registerEffect(String name, StatusEffect effect) {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MaryMod2024.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        registerEffect("instant_heal", INSTANT_HEAL);
        registerEffect("zombiefication", ZOMBIEFICATION);
        registerEffect("vampirism", VAMPIRISM);
        registerEffect("neurotypical", NEUROTYPICAL);
    }
}
