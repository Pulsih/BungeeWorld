package me.pulsi_.bungeeworld.values;

public class Values {
    public static ConfigValues CONFIG = new ConfigValues();
    public static GlobalSettings GLOBAL = new GlobalSettings();

    public static void reloadValues() {
        CONFIG.loadValues();
        GLOBAL.loadValues();
    }
}