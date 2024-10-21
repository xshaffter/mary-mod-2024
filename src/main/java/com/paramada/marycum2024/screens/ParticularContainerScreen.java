package com.paramada.marycum2024.screens;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.screens.handlers.ParticularContainerScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ParticularContainerScreen extends HandledScreen<ParticularContainerScreenHandler> {
    private static final Identifier PLAYER_TEXTURE = new Identifier(MaryMod2024.MOD_ID, "textures/gui/particular_container/player_inv.png");
    private static final Identifier ROW_TEXTURE = new Identifier(MaryMod2024.MOD_ID, "textures/gui/particular_container/row.png");
    private static final Identifier SUPERIOR_TEXTURE = new Identifier(MaryMod2024.MOD_ID, "textures/gui/particular_container/superior_band.png");
    private static final Identifier SLOT_TEXTURE = new Identifier(MaryMod2024.MOD_ID, "textures/gui/particular_container/slot.png");

    public static final int INVENTORY_WIDTH = 176;
    public static final int PLAYER_INV_HEIGHT = 90;
    public static final int SUPERIOR_HEIGHT = 16;
    private int guiStartSlotX;
    private int guiStartY;

    public ParticularContainerScreen(ParticularContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        backgroundHeight = handler.getRows() * ParticularContainerScreenHandler.SLOT_SIZE + PLAYER_INV_HEIGHT + SUPERIOR_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        if (handler.getColumns() < 5) {
            this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        int guiSlotsWidth = handler.getColumns() * ParticularContainerScreenHandler.SLOT_SIZE;
        guiStartY = (height - backgroundHeight) / 2;
        guiStartSlotX = (width - guiSlotsWidth) / 2;

        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public void renderSlots(DrawContext context) {
        int startSlotY = guiStartY + SUPERIOR_HEIGHT;
        for (int row = 0; row < handler.getRows(); row++) {
            for (int col = 0; col < handler.getColumns(); col++) {
                context.drawTexture(
                        SLOT_TEXTURE,
                        guiStartSlotX + col * ParticularContainerScreenHandler.SLOT_SIZE,
                        startSlotY + row * ParticularContainerScreenHandler.SLOT_SIZE,
                        0,
                        0,
                        ParticularContainerScreenHandler.SLOT_SIZE,
                        ParticularContainerScreenHandler.SLOT_SIZE,
                        ParticularContainerScreenHandler.SLOT_SIZE,
                        ParticularContainerScreenHandler.SLOT_SIZE
                );
            }
        }
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int slotHeight = ParticularContainerScreenHandler.SLOT_SIZE;
        int x = (this.width - INVENTORY_WIDTH) / 2;

        context.drawTexture(SUPERIOR_TEXTURE, x, guiStartY, 0, 0, INVENTORY_WIDTH, SUPERIOR_HEIGHT, INVENTORY_WIDTH, SUPERIOR_HEIGHT);

        for (int row = 0; row < handler.getRows(); row++) {
            int y = guiStartY + SUPERIOR_HEIGHT + row * ParticularContainerScreenHandler.SLOT_SIZE;

            context.drawTexture(ROW_TEXTURE, x, y, 0, 0, INVENTORY_WIDTH, slotHeight, INVENTORY_WIDTH, slotHeight);
        }
        renderSlots(context);

        context.drawTexture(PLAYER_TEXTURE, x, guiStartY + SUPERIOR_HEIGHT + slotHeight * handler.getRows(), 0, 0, INVENTORY_WIDTH, PLAYER_INV_HEIGHT, INVENTORY_WIDTH, PLAYER_INV_HEIGHT);
    }
}
