package com.paramada.marycum2024.util.functionality.bridges;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.effects.ModEffects;
import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.functionality.MoneyManager;
import com.paramada.marycum2024.util.animator.IExampleAnimatedPlayer;
import com.paramada.marycum2024.util.inventory.SpecialSlotManager;
import com.paramada.marycum2024.util.souls.ISoulsPlayerCamera;
import com.paramada.marycum2024.util.souls.ISoulsPlayerSelector;
import dev.emi.trinkets.api.TrinketsApi;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

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

    public static ISoulsPlayerCamera asCameraComponent(ClientPlayerEntity player) {
        return (ISoulsPlayerCamera) player;
    }

    public static void selectNextItem() {
        var data = LivingEntityBridge.getPersistentData(client.player);
        if (data.getBoolean("using_item")) {
            return;
        }
        var selector = PlayerEntityBridge.getItemSelector();

        data.putInt("current_item", selector.selectNextSlot());
    }

    public static void swapSelectedItem() {
        if (client.player != null) {
            var selector = PlayerEntityBridge.getItemSelector();
            var data = LivingEntityBridge.getPersistentData(client.player);
            if (data.getBoolean("using_item") || data.getBoolean("enabled_return")) {
                return;
            }

            data.putBoolean("item_swapped", true);
            data.putBoolean("enabled_return", true);
            data.putBoolean("using_item", true);
            var currentItem = selector.getSelectedSlot();

            NetworkManager.swapHandWithSelectedItem(currentItem, true);
        }
    }

    public static void lockFocusedTarget() {
        var targets = MinecraftClientBridge.getPossibleTargets(client);

        LivingEntity target = MinecraftClientBridge.getClossestTarget(targets);

        var cameraComponent = PlayerEntityBridge.asCameraComponent(client.player);
        if (cameraComponent != null) {
            if (cameraComponent.maryCum2024$hasLockedTarget()) {
                cameraComponent.maryCum2024$setLockedTarget(null);
            } else if (target != null) {
                cameraComponent.maryCum2024$setLockedTarget(target);
            }
        }
    }

    public static void switchSecondaryHand() {
        if (client.player == null) {
            return;
        }

        var data = LivingEntityBridge.getPersistentData(client.player);
    }

    public static void switchPrimaryHand() {
        if (client.player == null) {
            return;
        }

        var data = LivingEntityBridge.getPersistentData(client.player);

        if (data.getBoolean("using_item")) {
            return;
        }

        if (data.getBoolean("enabled_primary")) {
            var selectedSlot = client.player.getInventory().selectedSlot;
            client.player.getInventory().selectedSlot = getNextNonEmptySlot(selectedSlot);
            syncSelectedSlot();
            data.putBoolean("enabled_primary", false);
        }
    }


    private static int getNextNonEmptySlot(int selectedSlot) {
        for (int i = selectedSlot + 1; i < 9; i++) {
            var stack = client.player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                return i;
            }
        }
        for (int i = 0; i < selectedSlot; i++) {
            var stack = client.player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                return i;
            }
        }
        return selectedSlot;
    }

    private static void syncSelectedSlot() {
        if (client.player == null || client.getNetworkHandler() == null) {
            return;
        }

        int i = client.player.getInventory().selectedSlot;
        client.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(i));
    }

    public static SpecialSlotManager getItemSelector() {
        if (client.player == null) {
            return null;
        }
        return ((ISoulsPlayerSelector) client.player).maryCum2024$getItemSelector();
    }

    public static SpecialSlotManager getOffHandSelector() {
        if (client.player == null) {
            return null;
        }
        return ((ISoulsPlayerSelector) client.player).maryCum2024$getoffHandSelector();
    }

    public static void returnSelectedItem() {
        if (client.player == null) {
            return;
        }
        var selector = PlayerEntityBridge.getItemSelector();
        var data = LivingEntityBridge.getPersistentData(client.player);
        if (data.getBoolean("using_item")) {
            return;
        }

        if (!data.getBoolean("enabled_return")) {
            return;
        }

        client.options.useKey.setPressed(false);

        data.putBoolean("enabled_return", false);
        data.putBoolean("item_swapped", false);
        var currentItem = selector.getSelectedSlot();
        NetworkManager.swapHandWithSelectedItem(currentItem, false);
    }

    public static void enableSwitchPrimary() {
        if (client.player == null) {
            return;
        }
        var data = LivingEntityBridge.getPersistentData(client.player);
        if (!data.getBoolean("enabled_primary")) {
            data.putBoolean("enabled_primary", true);
        }
    }

    public static void enableSwitchSecondary() {
        if (client.player == null) {
            return;
        }
        var data = LivingEntityBridge.getPersistentData(client.player);
        if (!data.getBoolean("enabled_secondary")) {
            data.putBoolean("enabled_secondary", true);
        }
    }

    public static ModifierLayer<IAnimation> getAnimator() {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return null;
        return ((IExampleAnimatedPlayer) player).maryCum2024$getModAnimation();
    }

    public static Vec3d getMovement(PlayerEntity player) {
        var velo = player.getVelocity();
        return player.getPos().add(velo);
    }
}