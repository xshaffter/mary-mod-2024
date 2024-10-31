package com.paramada.marycum2024.util.functionality.bridges;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.souls.SoulsPlayer;
import com.paramada.marycum2024.util.animator.IExampleAnimatedPlayer;
import com.paramada.marycum2024.util.functionality.MoneyManager;
import com.paramada.marycum2024.util.souls.ISoulsPlayer;
import dev.emi.trinkets.api.TrinketsApi;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PlayerEntityBridge {


    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static int getMoney(PlayerEntity player) {
        var enderchest = player.getEnderChestInventory();
        return enderchest.count(ItemManager.MARY_COIN);
    }

    public static boolean hasTDAH(PlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) {
            return false;
        }

        var trinketComponent = TrinketsApi.getTrinketComponent(player);
        if (trinketComponent.isPresent()) {
            var comp = trinketComponent.get();
            if (comp.isEquipped(ItemManager.GLASSES)) {
                return !player.hasStatusEffect(ModEffects.NEUROTYPICAL);
            }
        }
        return true;
    }

    public static void starBandageHealing(PlayerEntity player) {
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

    public static void startEstusHealing(PlayerEntity player) {
        if (!player.getWorld().isClient) {
            return;
        }
        var animationContainer = PlayerEntityBridge.getAnimator();

        if (animationContainer == null) return;

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

        var keyAnimation = new KeyframeAnimationPlayer(animation);
        animationContainer.setAnimation(keyAnimation);
    }

    public static void stopAnimation(PlayerEntity player) {
        if (!player.getWorld().isClient) {
            return;
        }

        var animationContainer = ((IExampleAnimatedPlayer) player).maryCum2024$getModAnimation();
        animationContainer.setAnimation(null);
    }

    public static ModifierLayer<IAnimation> getAnimator() {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return null;
        return ((IExampleAnimatedPlayer) player).maryCum2024$getModAnimation();
    }

    public static SoulsPlayer getCurrentSoulsPlayer() {
        var player = MinecraftClient.getInstance().player;
        if (player == null) {
            return null;
        }
        return ((ISoulsPlayer) player).marymod2024$getSoulsPlayer();
    }

    @SuppressWarnings("unused")
    public static SoulsPlayer getSoulsPlayer(PlayerEntity player) {
        if (player == null) {
            return null;
        }
        return ((ISoulsPlayer) player).marymod2024$getSoulsPlayer();
    }
}