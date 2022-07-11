package me.pulsi_.bungeeworld.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class BWMethods {

    public static String getStringLocation(Location location) {
        return location.toString().replace("Location{world=CraftWorld{name=", "").replace("},x=", " ")
                .replace(",y=", " ").replace(",z=", " ").replace(",pitch=", " ")
                .replace(",yaw=", " ").replace("}", "");
    }

    public static Location getLocation(String path) {
        String[] s = path.split(" ");
        Location loc;
        try {
            loc = new Location(Bukkit.getWorld(s[0]),
                    Double.parseDouble(s[1]),
                    Double.parseDouble(s[2]),
                    Double.parseDouble(s[3]),
                    Float.parseFloat(s[5]),
                    Float.parseFloat(s[4]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            BWLogger.error("\"" + path + "\" Is an invalid location!");
            loc = null;
        }
        return loc;
    }

    public static void playSound(Player p, String soundString) {
        String[] parts = soundString.split(" ");
        try {
            Sound sound = Sound.valueOf(parts[0]);
            int volume = Integer.parseInt(parts[1]);
            int pitch = Integer.parseInt(parts[2]);
            p.playSound(p.getLocation(), sound, volume, pitch);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            BWLogger.error("\"" + soundString + "\" Is an invalid sound type!");
        }
    }

    public static boolean isValidNumber(String number) {
        try {
            Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static void clearChat(Player p) {
        for (int i = 0; i < 60; i++) p.sendMessage("");
    }
}