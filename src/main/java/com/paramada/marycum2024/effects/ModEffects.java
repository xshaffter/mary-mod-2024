package com.paramada.marycum2024.effects;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect PINK_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect BLUE_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect RED_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect GREEN_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect CYAN_RIBBON_EFFECT = new RibbonEffect();
    public static final StatusEffect BLACK_RIBBON_EFFECT = new RibbonEffect();

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
    }
}
