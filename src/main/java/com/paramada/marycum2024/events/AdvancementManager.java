package com.paramada.marycum2024.events;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AdvancementManager {

    private static AdvancementEntry getAdvancement(MinecraftServer server, final String advancement) {
        return server.getAdvancementLoader().get(new Identifier(MaryMod2024.MOD_ID, advancement));
    }
    public static void grantAdvancement(ServerPlayerEntity player, final String advancement) {
        var adv = getAdvancement(player.server, advancement);
        var unobtained = player.getAdvancementTracker().getProgress(adv).getUnobtainedCriteria();
        for (var criterion : unobtained) {
            player.getAdvancementTracker().grantCriterion(adv, criterion);
        }
    }

    public static void registerCriterions() {
    }

    public static boolean hasAdvancement(ServerPlayerEntity player, final String advancement) {
        var adv = getAdvancement(player.server, advancement);
        var progress = player.getAdvancementTracker().getProgress(adv);
        return progress.isDone();
    }

}
