package com.paramada.marycum2024.events;

import com.paramada.marycum2024.client.MaryMod2024Client;
import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public class KeyboardHandler {
    public static final String KEY_CATEGORY_MARY_MOD = "key.category.mary-mod-2024";
    public static final String KEY_USE_ITEM = "key.mary-mod-2024.use_item";
    public static final String KEY_SWITCH_ITEM = "key.mary-mod-2024.switch_item";
    public static final String KEY_Z_TARGETING = "key.mary-mod-2024.z_targeting";

    public static KeyBinding useItemKey;
    public static KeyBinding switchItemKey;
    public static KeyBinding zTargetingKey;

    private static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (zTargetingKey.wasPressed()) {
                var target = client.crosshairTarget;
                var cameraComponent = PlayerEntityBridge.asCameraComponent(client.player);
                if (cameraComponent != null) {
                    if (cameraComponent.maryCum2024$hasLockedTarget()) {
                        cameraComponent.maryCum2024$setLockedTarget(null);
                    } else if (target != null && target.getType() == HitResult.Type.ENTITY){
                        var entityTarget = (EntityHitResult)target;
                        cameraComponent.maryCum2024$setLockedTarget(entityTarget.getEntity());
                    }
                }
            }
        });
    }

    public static void register() {
        useItemKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_USE_ITEM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY_MARY_MOD
        ));
        switchItemKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SWITCH_ITEM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_TAB,
                KEY_CATEGORY_MARY_MOD
        ));
        zTargetingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_Z_TARGETING,
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
                KEY_CATEGORY_MARY_MOD
        ));

        registerKeyInputs();
    }
}