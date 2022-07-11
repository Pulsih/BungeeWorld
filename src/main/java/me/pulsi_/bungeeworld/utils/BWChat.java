package me.pulsi_.bungeeworld.utils;

import org.bukkit.ChatColor;

public class BWChat {

    public static final String prefix = "&8[&2Bungee&aWorld&8]";

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}