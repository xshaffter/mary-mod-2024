package com.paramada.marycum2024.util.souls;

import net.minecraft.entity.Entity;

public interface ISoulsPlayerCamera {

    Entity getLockedTarget();

    boolean hasLockedTarget();

    void setLockedTarget(Entity target);
}
