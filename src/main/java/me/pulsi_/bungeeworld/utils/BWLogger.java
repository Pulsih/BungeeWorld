package me.pulsi_.bungeeworld.utils;

import org.bukkit.Bukkit;

public class BWLogger {

    public static void error(Object error) {
        log(BWChat.prefix + " &8[&cERROR&8] &c" + error);
    }

    public static void warn(Object warn) {
        log(BWChat.prefix + " &8[&eWARN&8] &e" + warn);
    }

    public static void info(Object info) {
        log(BWChat.prefix + " &8[&9INFO&8] &9" + info);
    }

    public static void log(Object log) {
        Bukkit.getConsoleSender().sendMessage(BWChat.color(log.toString()));
    }
}