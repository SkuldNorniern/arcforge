package com.nornity.arcforge.arcana;

public class ArcanaStabilityState {
    private double pressure;
    private double stability = 1.0;
    private boolean overloaded;

    public double getPressure() {
        return pressure;
    }

    public double getStability() {
        return stability;
    }

    public boolean isOverloaded() {
        return overloaded;
    }

    public void applyLoad(double load, boolean overloadEnabled) {
        pressure += load;
        if (!overloadEnabled) {
            pressure = Math.min(pressure, 1.0);
            overloaded = false;
            return;
        }
        stability = Math.max(0.0, 1.0 - Math.max(0.0, pressure - 1.0));
        overloaded = pressure > 1.0 && stability <= 0.0;
    }

    public void relax(double amount) {
        pressure = Math.max(0.0, pressure - amount);
        if (pressure <= 1.0) {
            stability = 1.0;
            overloaded = false;
        }
    }
}
