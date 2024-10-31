package com.paramada.marycum2024.mixins;

import com.github.exopandora.shouldersurfing.api.client.ShoulderSurfing;
import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.hud.HudElement;
import com.paramada.marycum2024.screens.components.SpriteData;
import com.paramada.marycum2024.screens.components.TextureComponent;
import com.paramada.marycum2024.souls.SoulsPlayer;
import com.paramada.marycum2024.util.functionality.bridges.LivingEntityBridge;
import com.paramada.marycum2024.util.functionality.bridges.PlayerEntityBridge;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Shadow
    protected abstract void renderHotbarItem(DrawContext context, int x, int y, float f, PlayerEntity player, ItemStack stack, int seed);

    @Unique
    private static final Identifier MARYCOIN_TEXTURE = new Identifier(MaryMod2024.MOD_ID, "textures/hud/coin.png");
    @Unique
    private static final Identifier HOTBAR_SELECTOR_TEXTURE = new Identifier(MaryMod2024.MOD_ID, "textures/hud/hotbar_selector.png");
    @Unique
    private HudElement ECONOMY;
    @Unique
    private TextureComponent FAKE_HOTBAR;

    @Unique
    private void init() {
        int windowWidth = this.scaledWidth;
        int windowHeight = this.scaledHeight;
        ECONOMY = new HudElement(
                windowWidth - 80,
                windowHeight - 24 - 2,
                MARYCOIN_TEXTURE,
                56,
                56,
                56,
                56
        );

        FAKE_HOTBAR = new TextureComponent(
                HOTBAR_SELECTOR_TEXTURE,
                new SpriteData(
                        0, windowHeight - 48, 64, 48
                ),
                new SpriteData(
                        64, 48
                )
        );

    }

    @Unique
    private void renderEconomy(DrawContext context) {
        TextRenderer textRenderer = getTextRenderer();
        assert client.player != null;
        int balance = LivingEntityBridge.getPersistentData(client.player).getInt("coins");
        String balanceString = new DecimalFormat("000").format(balance);

        int fontHeight = textRenderer.fontHeight;
        int textWidth = textRenderer.getWidth(balanceString);
        int textureSize = 16;
        int boxPadding = 2;
        ECONOMY.render(context, textureSize, textureSize);

        context.fill(ECONOMY.x - boxPadding, ECONOMY.y - boxPadding, ECONOMY.x + textWidth + textureSize + boxPadding * 2, ECONOMY.y + fontHeight + (textureSize / 2) + boxPadding, 0xAA000000);
        context.drawText(getTextRenderer(), Text.literal(balanceString), ECONOMY.x + textureSize + boxPadding, ECONOMY.y + (textureSize / 2) - (getTextRenderer().fontHeight / 2), 0xFFFFFFFF, false);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        init();
        renderEconomy(context);
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void renderCustomHotbar(DrawContext context, CallbackInfo ci) {
        var player = MinecraftClient.getInstance().player;
        var shoulderSurfing = ShoulderSurfing.getInstance();
        if (player != null && shoulderSurfing.isShoulderSurfing() && !player.isCreative() && !player.isSpectator() && !shoulderSurfing.isAiming()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void renderCustomHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
        var player = MinecraftClient.getInstance().player;
        if (player != null && (player.isCreative() || player.isSpectator())) {
            return;
        }

        if (player == null) {
            return;
        }

        renderFakeHotbar(context);
        renderSelectableItems(context);
        renderMainHand(context);
        renderOffHand(context);
        ci.cancel();
    }

    @Unique
    private void renderFakeHotbar(DrawContext context) {
        FAKE_HOTBAR.render(context);
    }

    @SuppressWarnings("DataFlowIssue")
    @Unique
    private void renderMainHand(DrawContext context) {
        var player = PlayerEntityBridge.getCurrentSoulsPlayer();
        if (player.getCurrentAction() == SoulsPlayer.SoulsAction.USING_ITEM && player.isSwappedItem()) {
            var currentItem = player.itemSelectorManager.getSelectedSlot();
            renderItem(
                    context,
                    currentItem,
                    FAKE_HOTBAR.getX() + 39,
                    FAKE_HOTBAR.getY() + 2
            );
        } else {
            var x = FAKE_HOTBAR.getX() + 39;
            var y = FAKE_HOTBAR.getY() + 2;
            renderHotbarItem(context, x, y, 0, client.player, client.player.getInventory().getMainHandStack(), 0);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Unique
    private void renderSelectableItems(DrawContext context) {
        var player = PlayerEntityBridge.getCurrentSoulsPlayer();
        var selector = player.itemSelectorManager;
        var currentItem = selector.getSelectedSlot();
        var previousItem = selector.getPreviousSlot();
        var nextItem = selector.getNextSlot();

        if (player.getCurrentAction() == SoulsPlayer.SoulsAction.USING_ITEM && player.isSwappedItem()) {
            var x = FAKE_HOTBAR.getX() + 24;
            var y = FAKE_HOTBAR.getY() + 29;
            renderHotbarItem(context, x, y, 0, client.player, client.player.getInventory().getMainHandStack(), 0);
        } else {
            renderItem(
                    context,
                    currentItem,
                    FAKE_HOTBAR.getX() + 24,
                    FAKE_HOTBAR.getY() + 29
            );
        }

        renderItem(
                context,
                previousItem,
                FAKE_HOTBAR.getX() + 2,
                FAKE_HOTBAR.getY() + 29
        );
        renderItem(
                context,
                nextItem,
                FAKE_HOTBAR.getX() + 46,
                FAKE_HOTBAR.getY() + 29
        );
    }

    @Unique
    private void renderItem(DrawContext context, int slot, int x, int y) {
        renderHotbarItem(context, x, y, 0, client.player, client.player.getInventory().getStack(slot), 0);
    }

    @Unique
    private void renderOffHand(DrawContext context) {
        assert client.player != null;
        var x = FAKE_HOTBAR.getX() + 9;
        var y = FAKE_HOTBAR.getY() + 2;
        renderHotbarItem(context, x, y, 0, client.player, client.player.getOffHandStack(), 0);
    }

}
