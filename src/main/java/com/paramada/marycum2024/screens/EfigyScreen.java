package com.paramada.marycum2024.screens;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.mixins.ScreenMixin;
import com.paramada.marycum2024.networking.NetworkManager;
import com.paramada.marycum2024.screens.components.CustomButtonWidget;
import com.paramada.marycum2024.screens.components.SpriteData;
import com.paramada.marycum2024.screens.components.TextureComponent;
import com.paramada.marycum2024.screens.handlers.EfigyBlockScreenHandler;
import com.paramada.marycum2024.util.PlayerEntityBridge;
import com.paramada.marycum2024.util.UpgradeManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EfigyScreen extends HandledScreen<EfigyBlockScreenHandler> {
    private static final Identifier MENU_TITLE_ID = new Identifier(MaryMod2024.MOD_ID, "textures/gui/title/marycraft_logo.png");
    private TextureComponent MENU_TITLE_COMPONENT;
    private int MENU_WIDTH;
    private static int FIRST_CHILD_MARGIN = 50;

    private int PADDING_WIDTH = 32;
    private static final int PADDING_HEIGHT = 32;
    public static final int TITLE_HEIGHT = 32;

    public EfigyScreen(EfigyBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.fill(0, 0, MENU_WIDTH, height, 0, 0x99000000);
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
        var nextDurabilityCost = UpgradeManager.getNextDurabilityCost(client.player);
        var nextUpgradeCost = UpgradeManager.getNextUpgradeCost(client.player);
        var nextLevelCost = UpgradeManager.getNextLevelCost(client.player);

        var durabilityText = "Aumentar pociones";
        var upgradeText = "Mejorar poción";
        var levelText = "Subir de nivel";

        if (nextDurabilityCost > -1) {
            durabilityText += " (%d)".formatted(nextDurabilityCost);
        }

        if (nextUpgradeCost > -1) {
            upgradeText += " (%d)".formatted(nextUpgradeCost);
        }

        if (nextLevelCost > -1) {
            levelText += " (%d)".formatted(nextLevelCost);
        }

        this.clearChildren();

        this.addDrawableChild(new CustomButtonWidget(
                PADDING_WIDTH + 1,
                FIRST_CHILD_MARGIN + PADDING_HEIGHT,
                MENU_WIDTH - PADDING_WIDTH * 2,
                32,
                button -> handler.levelUp(),
                Text.literal(levelText),
                nextLevelCost == -1
        ));

        this.addDrawableChild(new CustomButtonWidget(
                PADDING_WIDTH + 1,
                FIRST_CHILD_MARGIN + PADDING_HEIGHT * 2,
                MENU_WIDTH - PADDING_WIDTH * 2,
                32,
                button -> handler.increasePotion(),
                Text.literal(durabilityText),
                nextDurabilityCost == -1
        ));

        this.addDrawableChild(new CustomButtonWidget(
                PADDING_WIDTH + 1,
                FIRST_CHILD_MARGIN + PADDING_HEIGHT * 3,
                MENU_WIDTH - PADDING_WIDTH * 2,
                32,
                button -> handler.upgradePotion(),
                Text.literal(upgradeText),
                nextUpgradeCost == -1
        ));

        this.addDrawableChild(new CustomButtonWidget(
                PADDING_WIDTH + 1,
                FIRST_CHILD_MARGIN + PADDING_HEIGHT * 4,
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
                FIRST_CHILD_MARGIN + PADDING_HEIGHT * 5,
                MENU_WIDTH - PADDING_WIDTH * 2,
                32,
                button -> this.close(),
                Text.literal("Salir")
        ));

        var titleW = MENU_WIDTH - 1 - PADDING_WIDTH * 2;
        var titleX = (MENU_WIDTH / 2) - (titleW / 2);
        var titleY = 10;

        MENU_TITLE_COMPONENT = new TextureComponent(
                MENU_TITLE_ID,
                new SpriteData(titleX, titleY, titleW, TITLE_HEIGHT),
                new SpriteData(944, 249)
        );

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.init();
        renderBackground(context, mouseX, mouseY, delta);
        //super.render(context, mouseX, mouseY, delta);

        renderTitle(context);
        renderButtons(context, mouseX, mouseY, delta);
    }

    private void renderTitle(DrawContext context) {
        MENU_TITLE_COMPONENT.render(context);
    }

    private void renderButtons(DrawContext context, int mouseX, int mouseY, float delta) {
        for (Drawable widget : ((ScreenMixin) this).getDrawables()) {
            widget.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public void close() {
        assert client != null;
        assert client.player != null;
        PlayerEntityBridge.stopAnimation(client.player);
        ClientPlayNetworking.send(NetworkManager.CONTINUE_GAME, PacketByteBufs.create());
        super.close();
    }
}
