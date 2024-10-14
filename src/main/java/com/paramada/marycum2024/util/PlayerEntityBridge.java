package com.paramada.marycum2024.util;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.items.ItemManager;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class PlayerEntityBridge {
    public static String getTeam(LivingEntity player) {
        var living = (IPlayerFunctions) player;
        return living.getTeamName();
    }

    public static int getMoney(PlayerEntity player) {
        var enderchest = player.getEnderChestInventory();
        return enderchest.count(ItemManager.MARY_COIN);
    }

    public static void starBandagetHealing(PlayerEntity player) {
        if (!player.getWorld().isClient) {
            return;
        }
        var animationContainer = ((IExampleAnimatedPlayer) player).maryCum2024$getModAnimation();

        KeyframeAnimation animation = PlayerAnimationRegistry.getAnimation(new Identifier(MaryMod2024.MOD_ID, "bandage_heal"));
        assert animation != null;
        var builder = animation.mutableCopy();

        animation = builder.build();
        animationContainer.setAnimation(new KeyframeAnimationPlayer(animation));
    }

    public static void spendMoney(ServerPlayerEntity player, int price) {
        var persistent = LivingEntityBridge.getPersistentData(player);
        var balance = persistent.getInt("coins");
        setMoney(player, balance - price);
    }

    public static void setMoney(ServerPlayerEntity player, int amount) {
        var persistent = LivingEntityBridge.getPersistentData(player);
        persistent.putInt("coins", amount);
        MoneyManager.syncMoney(player);
    }

    public static void addMoney(Entity entity, int count) {
        if (entity instanceof ServerPlayerEntity player) {
            var money = getMoney(player);
            setMoney(player, money + count);
        }
    }

    public static void startEstusHealing(PlayerEntity player, Hand hand) {
        if (!player.getWorld().isClient) {
            return;
        }
        var animationContainer = ((IExampleAnimatedPlayer) player).maryCum2024$getModAnimation();

        KeyframeAnimation animation = PlayerAnimationRegistry.getAnimation(new Identifier(MaryMod2024.MOD_ID, "estus_heal"));

        assert animation != null;
        var builder = animation.mutableCopy();

        animation = builder.build();
        var keyAnimation = new KeyframeAnimationPlayer(animation);
        animationContainer.setAnimation(keyAnimation);
    }

    public static void startRestAnim(PlayerEntity player) {
        if (!player.getWorld().isClient) {
            return;
        }

        var animationContainer = ((IExampleAnimatedPlayer) player).maryCum2024$getModAnimation();

        KeyframeAnimation animation = PlayerAnimationRegistry.getAnimation(new Identifier(MaryMod2024.MOD_ID, "cool_sit"));
        assert animation != null;
        var builder = animation.mutableCopy();
        animation = builder.build();

        MinecraftClient.getInstance().options.setPerspective(Perspective.THIRD_PERSON_BACK);

        var keyAnimation = new KeyframeAnimationPlayer(animation)
                .setFirstPersonConfiguration(new FirstPersonConfiguration(false, false, false, false));
        animationContainer.setAnimation(keyAnimation);
    }

    public static void stopAnimation(PlayerEntity player) {
        if (!player.getWorld().isClient) {
            return;
        }
        MinecraftClient.getInstance().options.setPerspective(Perspective.FIRST_PERSON);

        var animationContainer = ((IExampleAnimatedPlayer) player).maryCum2024$getModAnimation();
        animationContainer.setAnimation(null);
    }
}