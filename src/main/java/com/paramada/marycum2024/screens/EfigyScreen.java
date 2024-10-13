package com.paramada.marycum2024.screens;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.mixins.ScreenMixin;
import com.paramada.marycum2024.screens.components.CustomButtonWidget;
import com.paramada.marycum2024.screens.handlers.EfigyBlockScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EfigyScreen extends HandledScreen<EfigyBlockScreenHandler> {
    private static final Identifier MENU_TITLE = new Identifier(MaryMod2024.MOD_ID, "textures/gui/title/marycraft_logo.png");
    private int MENU_WIDTH;

    private int PADDING_WIDTH = 32;
    private static final int PADDING_HEIGHT = 32;
    public static final int TITLE_HEIGHT = 32;

    public EfigyScreen(EfigyBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.fill(0, 0, MENU_WIDTH, height,0, 0x99000000);
    }

    @Override
    public Text getTitle() {
        return super.getTitle();
    }

    @Override
    protected void init() {
        super.init();
        MENU_WIDTH = width / 3;
        PADDING_WIDTH = Math.max(PADDING_WIDTH, MENU_WIDTH / 4);

        this.addDrawableChild(new CustomButtonWidget(
                PADDING_WIDTH + 1,
                PADDING_HEIGHT,
                MENU_WIDTH - PADDING_WIDTH * 2,
                32,
                button -> {
                    System.out.println("wawawa");
                },
                Text.literal("Subir de nivel")
        ));

        this.addDrawableChild(new CustomButtonWidget(
                PADDING_WIDTH + 1,
                PADDING_HEIGHT * 2,
                MENU_WIDTH - PADDING_WIDTH * 2,
                32,
                button -> {
                    handler.rest();
                    this.close();
                },
                Text.literal("Descansar")
        ));

        this.addDrawableChild(new CustomButtonWidget(
                PADDING_WIDTH + 1,
                PADDING_HEIGHT * 3,
                MENU_WIDTH - PADDING_WIDTH * 2,
                32,
                button -> this.close(),
                Text.literal("Salir")
        ));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        //super.render(context, mouseX, mouseY, delta);

        renderTitle(context);
        renderButtons(context, mouseX, mouseY, delta);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void renderTitle(DrawContext context) {
        context.drawTexture(MENU_TITLE, PADDING_WIDTH + TITLE_HEIGHT + 1, 0, MENU_WIDTH - TITLE_HEIGHT - 1 - PADDING_WIDTH * 2, 32, 1, 1, 944, 249, 944, 249);
    }

    private void renderButtons(DrawContext context, int mouseX, int mouseY, float delta) {
        for (Drawable widget : ((ScreenMixin) this).getDrawables()) {
            widget.render(context, mouseX, mouseY, delta);
        }
    }
}
