package ru.peef.mglegendsoflevel;

public class Utils {
    public static double calculateHealth(double base, double currentLevel, double currentXP, double maxXP) {
        double result;

        double onePer = maxXP / 100f;
        int currentXPPercent = (int) (currentXP / onePer);

        double bPCL = base + currentLevel;
        double bMCL = base - currentLevel;
        if (currentLevel < 15) {
            result = (base * (currentXPPercent)) / bPCL;
            if (result < base) result = base;
        } else {
            // base + level
            // base - level

            // 75 + 28 = 103
            // 75 - 28 = 47

            // 50 + 28 = 78
            // 50 - 28 = 22

            // 40 + 28 = 68
            // 40 - 28 = 12
            result = (base * (currentXPPercent)) / ((base / 4f) * (currentLevel * 3.5f));
        }
        if (result < bPCL) {
            result = bPCL;
        }
        if (result > 2048) result = 2048;
        return result;
    }
}
