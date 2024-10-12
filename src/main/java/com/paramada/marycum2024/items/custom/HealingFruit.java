package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.networking.packets.payloads.PotionUpgradeDataPayload;
import com.paramada.marycum2024.util.LivingEntityBridge;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HealingFruit extends Item {
    public HealingFruit() {
        super(new Settings().fireproof().rarity(Rarity.EPIC).maxCount(4).food(new FoodComponent.Builder().snack().alwaysEdible().nutrition(0).saturationModifier(0).build()));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        var data = LivingEntityBridge.getPersistentData(user);
        var currentUpgrade = data.getInt("upgrade");
        data.putInt("upgrade", currentUpgrade + 1);

        ClientPlayNetworking.send(new PotionUpgradeDataPayload(NetworkManager.REQUEST_UPGRADE_ID));
        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.healing_fruit"));
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.healing_fruit_2"));
    }
}
