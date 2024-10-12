package com.paramada.marycum2024.events;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;



public class SoundManager {
    public static final SoundEvent BANDAGE_HEAL = registerSound("bandage_heal");
    public static final SoundEvent FANSA_MUSIC = registerSound("fansa_music");
    public static final SoundEvent RASPUTIN = registerSound("rasputin");
    public static final SoundEvent DARK_CAVE = registerSound("dark_cave");

    private static SoundEvent registerSound(final String name) {
        Identifier id = new Identifier(MaryMod2024.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
    }
}
