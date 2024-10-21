package com.paramada.marycum2024.networking.packets;

import com.paramada.marycum2024.MaryMod2024;
import com.paramada.marycum2024.blocks.custom.entities.EfigyBlockEntity;
import com.paramada.marycum2024.items.ItemManager;
import com.paramada.marycum2024.items.custom.potions.ReusablePotion;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.ChunkSectionPos;

public class RestAtEfigyC2SPacket {
    public static <T extends FabricPacket> void receive(MinecraftServer server, ServerPlayerEntity player,
                                                        ServerPlayNetworkHandler handler,
                                                        PacketByteBuf buf, PacketSender responseSender) {
        var pos = buf.readBlockPos();
        var world = player.getWorld();
        var blockState = world.getBlockState(pos);
        if (!blockState.hasBlockEntity()) {
            MaryMod2024.LOGGER.info("NO ENTITY DETECTED");
            return;
        }

        var chunk = world.getChunk(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ()));


        var blockEntity = chunk.getBlockEntity(pos);

        if (blockEntity == null) {
            MaryMod2024.LOGGER.info("NULL EFIGY ENTITY");
            return;
        }

        ItemStack ribbonItem = ((EfigyBlockEntity) blockEntity).getItem();
        if (!ribbonItem.isEmpty()) {
            player.clearStatusEffects();
            player.setHealth(player.getMaxHealth());
            player.setAbsorptionAmount(0);
            player.getHungerManager().setSaturationLevel(5.0f);
            player.getHungerManager().setExhaustion(0f);
            player.getHungerManager().setFoodLevel(20);

            var reusablePotions = player.getInventory().main.stream().filter(
                    itemStack -> itemStack.isOf(ItemManager.MEDIKA_POTION)
                            || itemStack.getItem() instanceof ReusablePotion
            );

            for (var potion : reusablePotions.toList()) {
                potion.setDamage(0);
            }
        }
    }
}
