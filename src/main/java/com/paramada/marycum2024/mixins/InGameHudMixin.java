package com.paramada.marycum2024.mixins;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.hud.HudElement;
import com.paramada.marycum2024.util.LivingEntityBridge;
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

    @Shadow protected abstract void renderHotbarItem(DrawContext context, int x, int y, float f, PlayerEntity player, ItemStack stack, int seed);

    @Unique
    private static final Identifier MARYCOIN_TEXTURE = new Identifier(MaryMod2024.MOD_ID, "textures/hud/coin.png");
    @Unique
    private HudElement ECONOMY;

    @Unique
    private void init() {
        int windowWidth = this.scaledWidth;
        int windowHeight = this.scaledHeight;
        int leftX = windowWidth / 2 - 91 - 27;
        int rightX = windowWidth / 2 + 91 + 8 + 16;
        ECONOMY = new HudElement(
                windowWidth - 80,
                windowHeight - 24 - 2,
                MARYCOIN_TEXTURE,
                56,
                56,
                56,
                56
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

    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void renderCustomHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
//        renderPotionHUD(context);
//        renderMainHand(context);
//        renderOffHand(context);

    }

    @Unique
    private void renderMainHand(DrawContext context) {
        assert client.player != null;
        var currentItem = LivingEntityBridge.getPersistentData(client.player).getInt("current_main_item");
        var x = 0;
        var y = 0;
        var f = 0;
        renderHotbarItem(context, x, y, f, client.player, client.player.getInventory().getStack(currentItem), 0);
    }

    @Unique
    private void renderPotionHUD(DrawContext context) {
        assert client.player != null;
        var currentItem = LivingEntityBridge.getPersistentData(client.player).getInt("current_item");
        var x = 0;
        var y = 0;
        var f = 0;
        renderHotbarItem(context, x, y, f, client.player, client.player.getInventory().getStack(currentItem), 0);
    }

    @Unique
    private void renderOffHand(DrawContext context) {
        assert client.player != null;
        var x = 0;
        var y = 0;
        var f = 0;
        renderHotbarItem(context, x, y, f, client.player, client.player.getOffHandStack(), 0);
    }

}
