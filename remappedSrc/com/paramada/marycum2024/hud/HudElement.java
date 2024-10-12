package com.paramada.marycum2024.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class HudElement {

    public final int x;
    public final int y;
    public final Identifier texture;
    public final int spriteWidth;
    public final int spriteHeight;
    public final int textureWidth;
    public final int textureHeight;

    public HudElement(int x, int y, Identifier texture, int spriteWidth, int spriteHeight, int textureWidth, int textureHeight) {

        this.x = x;
        this.y = y;
        this.texture = texture;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void render(DrawContext context, int width, int height) {
        context.drawTexture(texture, x, y, width, height, 0, 0, spriteWidth, spriteHeight, textureWidth, textureHeight);
    }

    public void render(DrawContext context) {
        context.drawTexture(texture, x, y, spriteWidth, spriteHeight, 0, 0, spriteWidth, spriteHeight, textureWidth, textureHeight);
    }
}
