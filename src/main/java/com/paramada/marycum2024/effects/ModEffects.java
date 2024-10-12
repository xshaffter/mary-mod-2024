package com.paramada.marycum2024.effects;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
<<<<<<< Updated upstream
    public static final StatusEffect PINK_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect BLUE_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect RED_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect GREEN_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect CYAN_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect BLACK_RIBBON_EFFECT = new RibbonEffect();
=======
    private static final StatusEffect ZOMBIEFICATION_OBJECT = new ZombieficationEffect();
    private static final StatusEffect VAMPIRISM_OBJECT = new VampirismEffect();
    private static final RibbonEffect PINK_RIBBON_EFFECT_OBJECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ), "pink_ribbon_effect");
    private static final RibbonEffect BLUE_RIBBON_EFFECT_OBJECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ), "blue_ribbon_effect");
    private static final RibbonEffect RED_RIBBON_EFFECT_OBJECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ), "red_ribbon_effect");
    private static final RibbonEffect GREEN_RIBBON_EFFECT_OBJECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ), "green_ribbon_effect");
    private static final RibbonEffect CYAN_RIBBON_EFFECT_OBJECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
//            new StatusEffectInstance()
    ), "cyan_ribbon_effect");
    private static final RibbonEffect BLACK_RIBBON_EFFECT_OBJECT = new RibbonEffect(List.of(
//            new StatusEffectInstance(),
//            new StatusEffectInstance(),
            new StatusEffectInstance(StatusEffects.REGENERATION, MaryMod2024.TICKS_PER_SECOND * 30, 0, false, true)
    ), "black_ribbon_effect");
>>>>>>> Stashed changes

    public static final RegistryEntry<StatusEffect> ZOMBIEFICATION = registerEffect("zombiefication", ZOMBIEFICATION_OBJECT);
    public static final RegistryEntry<StatusEffect> VAMPIRISM = registerEffect("vampirism", VAMPIRISM_OBJECT);
    public static final RegistryEntry<StatusEffect> PINK_RIBBON_EFFECT = registerEffect("pink_ribbon_effect", PINK_RIBBON_EFFECT_OBJECT);
    public static final RegistryEntry<StatusEffect> BLUE_RIBBON_EFFECT = registerEffect("blue_ribbon_effect", BLUE_RIBBON_EFFECT_OBJECT);
    public static final RegistryEntry<StatusEffect> RED_RIBBON_EFFECT = registerEffect("red_ribbon_effect", RED_RIBBON_EFFECT_OBJECT);
    public static final RegistryEntry<StatusEffect> GREEN_RIBBON_EFFECT = registerEffect("green_ribbon_effect", GREEN_RIBBON_EFFECT_OBJECT);
    public static final RegistryEntry<StatusEffect> CYAN_RIBBON_EFFECT = registerEffect("cyan_ribbon_effect", CYAN_RIBBON_EFFECT_OBJECT);
    public static final RegistryEntry<StatusEffect> BLACK_RIBBON_EFFECT = registerEffect("black_ribbon_effect", BLACK_RIBBON_EFFECT_OBJECT);

    private static RegistryEntry<StatusEffect> registerEffect(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MaryMod2024.MOD_ID, name), effect);
    }

<<<<<<< Updated upstream
    public static void registerEffects() {
        registerEffect("pink_ribbon_effect", PINK_RIBBON_EFFECT);
        registerEffect("blue_ribbon_effect", BLUE_RIBBON_EFFECT);
        registerEffect("red_ribbon_effect", RED_RIBBON_EFFECT);
        registerEffect("green_ribbon_effect", GREEN_RIBBON_EFFECT);
        registerEffect("cyan_ribbon_effect", CYAN_RIBBON_EFFECT);
        registerEffect("black_ribbon_effect", BLACK_RIBBON_EFFECT);
    }
=======
>>>>>>> Stashed changes
}
