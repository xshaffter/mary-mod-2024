package com.paramada.marycum2024.events;

import com.paramada.marycum2024.util.functionality.IItemUsagePublisher;
import com.paramada.marycum2024.util.functionality.bridges.LivingEntityBridge;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyboardHandler {
    public static final String KEY_CATEGORY_MARY_MOD = "key.category.mary-mod-2024";
    public static final String KEY_USE_ITEM = "key.mary-mod-2024.use_item";
    public static final String KEY_SWITCH_ITEM = "key.mary-mod-2024.switch_item";
    public static final String KEY_SWITCH_MAIN_HAND = "key.mary-mod-2024.switch_main_hand";
    public static final String KEY_SWITCH_OFF_HAND = "key.mary-mod-2024.switch_off_hand";
    public static final String KEY_Z_TARGETING = "key.mary-mod-2024.z_targeting";

    public static KeyBinding useItemKey;
    public static KeyBinding switchItemNextKey;
    public static KeyBinding zTargetingKey;
    public static KeyBinding switchPrimaryHandKey;
    public static KeyBinding switchSecondaryHandKey;

    private static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (zTargetingKey.wasPressed()) {
                PlayerEntityBridge.lockFocusedTarget();
            }
            if (switchItemNextKey.wasPressed()) {
                PlayerEntityBridge.selectNextItem();
            }
            if (client.player != null) {
                if(client.options.useKey.isPressed()) {
                    PlayerEntityBridge.returnSelectedItem();
                }

                if (useItemKey.isPressed() && !client.player.isUsingItem()) {
                    PlayerEntityBridge.swapSelectedItem();
                }
            }

            if (switchPrimaryHandKey.isPressed()) {
                PlayerEntityBridge.switchPrimaryHand();
            } else {
                PlayerEntityBridge.enableSwitchPrimary();
            }
            if (switchSecondaryHandKey.isPressed()) {
                PlayerEntityBridge.switchSecondaryHand();
            } else {
                PlayerEntityBridge.enableSwitchSecondary();
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
        switchItemNextKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SWITCH_ITEM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_UP,
                KEY_CATEGORY_MARY_MOD
        ));
        zTargetingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_Z_TARGETING,
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
                KEY_CATEGORY_MARY_MOD
        ));
        switchPrimaryHandKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SWITCH_MAIN_HAND,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_TAB,
                KEY_CATEGORY_MARY_MOD
        ));
        switchSecondaryHandKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SWITCH_OFF_HAND,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                KEY_CATEGORY_MARY_MOD
        ));

        registerKeyInputs();
    }
}