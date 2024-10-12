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
    public static final RibbonEffect PINK_RIBBON_EFFECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ));
    public static final RibbonEffect BLUE_RIBBON_EFFECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ));
    public static final RibbonEffect RED_RIBBON_EFFECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ));
    public static final RibbonEffect GREEN_RIBBON_EFFECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ));
    public static final RibbonEffect CYAN_RIBBON_EFFECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ));
    public static final RibbonEffect BLACK_RIBBON_EFFECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
            new StatusEffectInstance(StatusEffects.REGENERATION, MaryMod2024.TICKS_PER_SECOND * 30, 0, false, true)
    ));

    private static void registerEffect(String name, StatusEffect effect) {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MaryMod2024.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        registerEffect("pink_ribbon_effect", PINK_RIBBON_EFFECT);
        registerEffect("blue_ribbon_effect", BLUE_RIBBON_EFFECT);
        registerEffect("red_ribbon_effect", RED_RIBBON_EFFECT);
        registerEffect("green_ribbon_effect", GREEN_RIBBON_EFFECT);
        registerEffect("cyan_ribbon_effect", CYAN_RIBBON_EFFECT);
        registerEffect("black_ribbon_effect", BLACK_RIBBON_EFFECT);
        registerEffect("zombiefication", ZOMBIEFICATION);
        registerEffect("vampirism", VAMPIRISM);
    }
}
