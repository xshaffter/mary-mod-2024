package com.paramada.marycum2024.damages;

import com.paramada.marycum2024.MaryMod2024;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> CHARGED_PLAYER_ATTACK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(MaryMod2024.MOD_ID, "charged_player_attack"));


    public static DamageSource of(World world, RegistryKey<DamageType> key, Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker);
    }
}
