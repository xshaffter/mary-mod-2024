package com.paramada.marycum2024.screens.components;

public record SpriteData(int x, int y, int width, int height) {
    public SpriteData(int width, int height) {
        this(0,0,width,height);
    }
}
