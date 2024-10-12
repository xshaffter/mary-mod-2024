package com.paramada.marycum2024.items.custom;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;

<<<<<<< Updated upstream
import static com.paramada.marycum2024.items.ItemGroups.MARY_MOD_GROUP;

public class ModMusicDiscItem extends MusicDiscItem {
    /**
     * Access widened by fabric-transitive-access-wideners-v1 to accessible
     *
     * @param comparatorOutput
     * @param sound
     * @param lengthInSeconds
     */
=======
public class ModMusicDiscItem extends MusicDiscItem implements IMaryItem {
>>>>>>> Stashed changes
    public ModMusicDiscItem(int comparatorOutput, SoundEvent sound, final int lengthInSeconds) {
        super(comparatorOutput, sound, new Settings()
                .rarity(Rarity.COMMON)
                .maxCount(1)
                .fireproof(), lengthInSeconds);
    }
}
