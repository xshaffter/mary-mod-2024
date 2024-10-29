package com.paramada.marycum2024.util.functionality;

import com.paramada.marycum2024.events.CustomExplosion;
import net.fabricmc.api.EnvType;

import java.util.NoSuchElementException;

public class PerformanceCooldownManager<T extends CustomExplosion> {

    private T value = null;
    private boolean alive = true;

    public void put(T newValue) {
        if (alive) {
            if (newValue != null) {
                value = newValue;
            }
        }
    }

    public boolean hasValue() {
        return value != null;
    }

    public T get(EnvType env) {
        if (value == null || !alive) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    private void kill() {
        value = null;
        alive = false;
    }

    public void perform() {
        if (hasValue()) {
            value.collectBlocksAndDamageEntities();
            value.affectWorld(true);
        }

        kill();
    }

    public void revive() {
        alive = true;
    }

    public boolean isAlive() {
        return alive;
    }
}
