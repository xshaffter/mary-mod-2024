package com.paramada.marycum2024.screens.components;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class TextureComponent {

    private Identifier texture;
    private int x;
    private int y;
    private final int renderWidth;
    private final int regionX;
    private final int regionY;
    private final int regionWidth;
    private final int regionHeight;
    private final int renderHeight;
    private final int spriteWidth;
    private final int spriteHeight;

    private TextureComponent(Identifier texture, int x, int y, int renderWidth, int renderHeight, int spriteWidth, int spriteHeight, int regionX, int regionY, int regionWidth, int regionHeight) {
        this.texture = texture;
        this.renderHeight = renderHeight;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.x = x;
        this.y = y;
        this.renderWidth = renderWidth;
        this.regionX = regionX;
        this.regionY = regionY;
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
    }

    public TextureComponent(Identifier identifier, SpriteData renderData, SpriteData spriteData, SpriteData spriteRegionData) {
        this(identifier, renderData.x(), renderData.y(), renderData.width(), renderData.height(), spriteData.width(), spriteData.height(), spriteRegionData.x(), spriteRegionData.y(), spriteRegionData.width(), spriteRegionData.height());
    }

    public TextureComponent(Identifier identifier, SpriteData renderData, SpriteData spriteData) {
        this(identifier, renderData, spriteData, spriteData);
    }

    public void render(DrawContext context) {
        context.drawTexture(texture, x, y, renderWidth, renderHeight, regionX, regionY, regionWidth, regionHeight, spriteWidth, spriteHeight);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRenderHeight() {
        return renderHeight;
    }

    public int getRenderWidth() {
        return renderWidth;
    }
}
