package com.paramada.marycum2024.items.custom;

import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.LivingEntityBridge;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HealingFruit extends Item implements IMaryItem {
    public HealingFruit() {
        super(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(4).food(new FoodComponent.Builder().snack().alwaysEdible().hunger(0).saturationModifier(0).build()));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        var data = LivingEntityBridge.getPersistentData(user);
        var currentUpgrade = data.getInt("upgrade");
        data.putInt("upgrade", currentUpgrade + 1);

        ClientPlayNetworking.send(NetworkManager.REQUEST_UPGRADE_ID, PacketByteBufs.create());
        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.healing_fruit"));
        tooltip.add(Text.translatable("tooltip.mary-mod-2024.healing_fruit_2"));
    }
}
