package com.paramada.marycum2024.mixins;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.paramada.marycum2024.effects.ModEffects;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

@Mixin(PotionUtil.class)
public class PotionUtilMixin {

    @Inject(method = "buildTooltip(Ljava/util/List;Ljava/util/List;FF)V", at = @At("HEAD"), cancellable = true)
    private static void buildTooltip(List<StatusEffectInstance> statusEffects, List<Text> list, float durationMultiplier, float tickRate, CallbackInfo ci) {
        List<Pair<EntityAttribute, EntityAttributeModifier>> list2 = Lists.newArrayList();
        Iterator<StatusEffectInstance> iterator;
        Iterator<Pair<EntityAttribute, EntityAttributeModifier>> iterator2;
        MutableText mutableText;
        StatusEffect statusEffect;
        if (!statusEffects.isEmpty()) {
            for (iterator = statusEffects.iterator(); iterator.hasNext(); list.add(mutableText.formatted(statusEffect.getCategory().getFormatting()))) {
                StatusEffectInstance statusEffectInstance = iterator.next();
                mutableText = Text.translatable(statusEffectInstance.getTranslationKey());
                statusEffect = statusEffectInstance.getEffectType();
                var map = statusEffect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for (var entry : map.entrySet()) {
                        list2.add(new Pair<>(entry.getKey(), entry.getValue().createAttributeModifier(statusEffectInstance.getAmplifier())));
                    }
                }

                if (statusEffectInstance.getAmplifier() > 0) {
                    if (statusEffectInstance.getEffectType() == ModEffects.INSTANT_HEAL) {
                        var amplifierNumericValue = statusEffectInstance.getAmplifier() / 2.0;
                        var fmt = new DecimalFormat("0.#");
                        var amplifierValue = fmt.format(amplifierNumericValue);
                        mutableText = Text.translatable("effect.mary-mod-2024.instant_heal", amplifierValue);
                    } else {
                        mutableText = Text.translatable("potion.withAmplifier", mutableText, Text.translatable("potion.potency." + statusEffectInstance.getAmplifier()));
                    }
                }

                if (!statusEffectInstance.isDurationBelow(20)) {
                    mutableText = Text.translatable("potion.withDuration", mutableText, StatusEffectUtil.getDurationText(statusEffectInstance, durationMultiplier, tickRate));
                }
            }
        }

        if (!list2.isEmpty()) {
            list.add(ScreenTexts.EMPTY);
            list.add(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE));
            iterator2 = list2.iterator();

            while (iterator2.hasNext()) {
                Pair<EntityAttribute, EntityAttributeModifier> pair = iterator2.next();
                EntityAttributeModifier entityAttributeModifier = pair.getSecond();
                double attributeModifierValue = entityAttributeModifier.getValue();
                double attributeModifierValueMultiplier;
                if (entityAttributeModifier.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_BASE && entityAttributeModifier.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_TOTAL) {
                    attributeModifierValueMultiplier = entityAttributeModifier.getValue();
                } else {
                    attributeModifierValueMultiplier = entityAttributeModifier.getValue() * 100.0;
                }

                if (attributeModifierValue > 0.0) {
                    list.add(Text.translatable("attribute.modifier.plus." + entityAttributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(attributeModifierValueMultiplier), Text.translatable(((EntityAttribute) pair.getFirst()).getTranslationKey())).formatted(Formatting.BLUE));
                } else if (attributeModifierValue < 0.0) {
                    attributeModifierValueMultiplier *= -1.0;
                    list.add(Text.translatable("attribute.modifier.take." + entityAttributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(attributeModifierValueMultiplier), Text.translatable(((EntityAttribute) pair.getFirst()).getTranslationKey())).formatted(Formatting.RED));
                }
            }
        }

        ci.cancel();
    }
}
