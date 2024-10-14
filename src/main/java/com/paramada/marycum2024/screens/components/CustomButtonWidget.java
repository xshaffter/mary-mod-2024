package com.paramada.marycum2024.screens.components;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.screens.EfigyScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class CustomButtonWidget extends ButtonWidget {
    private static final Identifier MENU_RIBBON = new Identifier(MaryMod2024.MOD_ID, "textures/item/pink_ribbon.png");

    public CustomButtonWidget(int x, int y, int width, int height, PressAction onPress) {
        this(x, y, width, height, onPress, Text.literal(""));
    }

    public CustomButtonWidget(int x, int y, int width, int height, PressAction onPress, MutableText text) {
        super(x, y, width, height, text, onPress, textSupplier -> text);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        Rectangle2D area = new Rectangle(this.getX(), this.getY(), width, height);
        if (area.contains(mouseX, mouseY)) {
            //noinspection SuspiciousNameCombination
            context.drawTexture(MENU_RIBBON, getX() - EfigyScreen.TITLE_HEIGHT, getY() + (height / 2) - (EfigyScreen.TITLE_HEIGHT / 2), EfigyScreen.TITLE_HEIGHT, EfigyScreen.TITLE_HEIGHT, 0, 0, 16, 16, 16, 16);
        }

        drawMessage(context, textRenderer, 0xFFFFFFFF);
    }
}
