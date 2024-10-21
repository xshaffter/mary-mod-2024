package com.paramada.marycum2024.items.custom.containers;

import com.paramada.marycum2024.events.SoundManager;
import com.paramada.marycum2024.items.custom.containers.entities.ParticularContainerEntityManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class ParticularContainerItem extends Item {
    private final int rows;
    private final int columns;
    private final Predicate<ItemStack> predicate;

    public ParticularContainerItem(Rarity rarity, int rows, int columns, Predicate<ItemStack> predicate) {
        super(new Settings().fireproof().rarity(rarity).maxCount(1));
        this.rows = rows;
        this.columns = columns;
        this.predicate = predicate;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            return super.use(world, user, hand);
        }
        var stack = user.getStackInHand(hand);
        openContainer(stack, user);
        return super.use(world, user, hand);
    }

    public NamedScreenHandlerFactory getItemEntity(ItemStack stack) {
        return new ParticularContainerEntityManager(stack, rows, columns, predicate);
    }

    public void openContainer(ItemStack stack, PlayerEntity player) {
        NamedScreenHandlerFactory screenHandlerFactory = getItemEntity(stack);
        player.openHandledScreen(screenHandlerFactory);
        player.playSound(SoundManager.ZIPPER_OPEN, SoundCategory.PLAYERS, 0.5f, 1);
    }

}
