package com.paramada.marycum2024.util.inventory;

import com.paramada.marycum2024.util.functionality.bridges.LivingEntityBridge;
import net.minecraft.util.math.MathHelper;

public class SpecialSlotManager {
    public final int minSlot;
    public final int maxSlot;
    private int selectedSlot;

    public SpecialSlotManager(int minSlot, int maxSlot) {
        this.minSlot = minSlot;
        this.maxSlot = maxSlot;
        this.selectedSlot = minSlot;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public int selectNextSlot() {
        selectedSlot++;
        if (selectedSlot > maxSlot) {
            selectedSlot = minSlot;
        }
        return selectedSlot;
    }

    public int getPreviousSlot() {
        if (selectedSlot > minSlot) {
            return selectedSlot - 1;
        }

        return maxSlot;
    }

    public int getNextSlot() {
        if (selectedSlot < maxSlot) {
            return selectedSlot + 1;
        }

        return minSlot;
    }

    public void select(int slot) {
        this.selectedSlot = MathHelper.clamp(slot, minSlot, maxSlot);
    }
}
