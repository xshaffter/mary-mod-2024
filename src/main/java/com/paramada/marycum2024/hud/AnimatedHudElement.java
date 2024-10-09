package com.paramada.marycum2024.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AnimatedHudElement {

    private final int x;
    private final int y;
    private final Identifier texture;
    private final int spriteWidth;
    private final int spriteHeight;
    private final int textureWidth;
    private final int textureHeight;
    private final int tickRate;
    private final int maxState;
    private int state;

    public AnimatedHudElement(int x, int y, Identifier texture, int spriteWidth, int spriteHeight, int textureWidth, int textureHeight, int tickRate, int maxState) {

        this.x = x;
        this.y = y;
        this.texture = texture;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.tickRate = tickRate;
        this.maxState = maxState;
        state = 0;
    }

    private void countTicks(int tickCounter) {
        if (tickCounter != 0 && tickCounter % tickRate == 0) {
            state++;
        }

        if (state > maxState) {
            state = 0;
        }
    }

    public void render(DrawContext context, int width, int height, int tickCounter) {
        countTicks(tickCounter);
        context.drawTexture(texture, x, y, width, height, 0, state * spriteHeight, spriteWidth, spriteHeight, textureWidth, textureHeight);
    }

    public void render(DrawContext context, int tickCounter) {
        countTicks(tickCounter);
        context.drawTexture(texture, x, y, spriteWidth, spriteHeight, 0, state * spriteHeight, spriteWidth, spriteHeight, textureWidth, textureHeight);
    }
}
