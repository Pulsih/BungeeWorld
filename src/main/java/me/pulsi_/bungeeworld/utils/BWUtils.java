package me.pulsi_.bungeeworld.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BWUtils {

    public static String getStringLocation(Location location) {
        return location.toString().replace("Location{world=CraftWorld{name=", "").replace("},x=", " ")
                .replace(",y=", " ").replace(",z=", " ").replace(",pitch=", " ")
                .replace(",yaw=", " ").replace("}", "");
    }

    public static Location getLocation(String path) {
        Location loc;
        if (path == null)
            return null;
        String[] s = path.split(" ");
        try {
            loc = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[5]), Float.parseFloat(s[4]));
        } catch (ArrayIndexOutOfBoundsException|NumberFormatException e) {
            loc = null;
        }
        return loc;
    }

    public static String getStringAfterArgs(String[] args, int arg) {
        if (arg < 0 || arg >= args.length) return null;

        StringBuilder builder = new StringBuilder();
        for (int i = arg; i < args.length; i++) {
            builder.append(args[i]);
            if (i + 1 < args.length) builder.append(" ");
        }
        return builder.toString();
    }

    public static void playSound(Player p, String soundString) {
        String[] parts = soundString.split(" ");
        try {
            Sound sound = Sound.valueOf(parts[0]);
            int volume = Integer.parseInt(parts[1]);
            int pitch = Integer.parseInt(parts[2]);
            p.playSound(p.getLocation(), sound, volume, pitch);
        } catch (IllegalArgumentException|ArrayIndexOutOfBoundsException e) {
            BWLogger.error("\"" + soundString + "\" Is an invalid sound type!");
        }
    }

    public static void sendTitle(Player p, String titleString) {
        titleString = titleString.replace("%player%", p.getName());
        if (titleString.contains(",")) {
            String[] parts = titleString.split(",");
            String title = parts[0];
            String subtitle = parts[1];
            p.sendTitle(BWChat.color(title), BWChat.color(subtitle));
        } else {
            p.sendTitle(BWChat.color(titleString), "");
        }
    }

    public static boolean hasPermissions(CommandSender s, String permission) {
        if (!s.hasPermission(permission)) {
            BWMessages.send(s, "no_permission");
            return false;
        }
        return true;
    }

    public static boolean isPlayer(CommandSender s) {
        if (!(s instanceof Player)) {
            BWMessages.send(s, "not_player");
            return false;
        }
        return true;
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
        for (int i = 0; i < 200; ) {
            p.sendMessage("");
            i++;
        }
    }
}