package com.paramada.marycum2024.events;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;



public class SoundManager {
    public static final SoundEvent BANDAGE_HEAL = registerSound("bandage_heal");
    public static final SoundEvent ZIPPER_OPEN = registerSound("zipper_open");
    public static final SoundEvent ZIPPER_CLOSE = registerSound("zipper_close");

    private static SoundEvent registerSound(final String name) {
        Identifier id = new Identifier(MaryMod2024.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
    }
}
