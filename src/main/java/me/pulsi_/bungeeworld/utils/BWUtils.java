package me.pulsi_.bungeeworld.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
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
        if (soundString == null || soundString.isEmpty()) return;

        float volume = 1F, pitch = 1F;
        SoundCategory category = SoundCategory.MASTER;
        if (!soundString.contains(" ")) {
            try {
                p.playSound(p.getLocation(), Sound.valueOf(soundString), category, volume, pitch);
            } catch (IllegalArgumentException e) {
                try {
                    p.playSound(p.getLocation(), soundString, category, volume, pitch);
                } catch (Exception ex) {
                    BWLogger.warn(soundString + " is not a valid sound! (Did you type it in lowercase?)");
                }
            }
            return;
        }
        String[] parts = soundString.split(" ");

        String sound = parts[0];

        if (parts.length > 1) {
            try {
                volume = Float.parseFloat(parts[1]);
            } catch (IllegalArgumentException e) {
                BWLogger.warn(parts[1] + " is not a valid volume number! Using 1 as default.");
            }
        }

        if (parts.length > 2) {
            try {
                pitch = Float.parseFloat(parts[2]);
            } catch (IllegalArgumentException e) {
                BWLogger.warn(parts[2] + " is not a valid pitch number! Using 1 as default.");
            }
        }

        if (parts.length > 3) {
            try {
                category = SoundCategory.valueOf(parts[3]);
            } catch (IllegalArgumentException e) {
                BWLogger.warn(parts[3] + " is not a valid sound category! Using MASTER as default.");
            }
        }

        try {
            p.playSound(p.getLocation(), Sound.valueOf(sound), category, volume, pitch);
        } catch (IllegalArgumentException e) {
            try {
                p.playSound(p.getLocation(), sound, category, volume, pitch);
            } catch (Exception ex) {
                BWLogger.warn(sound + " is not a valid sound! (Did you type it in lowercase?)");
            }
        }
    }

    public static void playSound(Location loc, String soundString) {
        if (soundString == null || soundString.isEmpty()) return;

        float volume = 1F, pitch = 1F;
        SoundCategory category = SoundCategory.MASTER;
        if (!soundString.contains(" ")) {
            try {
                loc.getWorld().playSound(loc, Sound.valueOf(soundString), category, volume, pitch);
            } catch (IllegalArgumentException e) {
                try {
                    loc.getWorld().playSound(loc, soundString, category, volume, pitch);
                } catch (Exception ex) {
                    BWLogger.warn(soundString + " is not a valid sound! (Did you type it in lowercase?)");
                }
            }
            return;
        }
        String[] parts = soundString.split(" ");

        String sound = parts[0];

        if (parts.length > 1) {
            try {
                volume = Float.parseFloat(parts[1]);
            } catch (IllegalArgumentException e) {
                BWLogger.warn(parts[1] + " is not a valid volume number! Using 1 as default.");
            }
        }

        if (parts.length > 2) {
            try {
                pitch = Float.parseFloat(parts[2]);
            } catch (IllegalArgumentException e) {
                BWLogger.warn(parts[2] + " is not a valid pitch number! Using 1 as default.");
            }
        }

        if (parts.length > 3) {
            try {
                category = SoundCategory.valueOf(parts[3]);
            } catch (IllegalArgumentException e) {
                BWLogger.warn(parts[3] + " is not a valid sound category! Using MASTER as default.");
            }
        }

        try {
            loc.getWorld().playSound(loc, Sound.valueOf(sound), category, volume, pitch);
        } catch (IllegalArgumentException e) {
            try {
                loc.getWorld().playSound(loc, sound, category, volume, pitch);
            } catch (Exception ex) {
                BWLogger.warn(sound + " is not a valid sound! (Did you type it in lowercase?)");
            }
        }
    }

    public static void sendTitle(Player p, String title) {
        if (title == null || p == null) return;

        title = BWMessages.addPrefix(title);
        if (!title.contains(";")) p.sendTitle(title, "");
        else {
            String[] titles = title.split(";");
            String title1 = titles[0], title2 = titles[1];

            if (titles.length == 2) p.sendTitle(title1, title2);
            else {
                int[] values = {20, 20, 20};
                boolean error = false;

                for (int i = 2; i < titles.length; i++) {
                    try {
                        values[i - 2] = Integer.parseInt(titles[i]);
                    } catch (NumberFormatException e) {
                        error = true;
                    }
                }
                if (error) BWLogger.warn("Invalid number in the title fades values! Please correct it as soon as possible! (Title: " + title + "&a)");

                try {
                    p.sendTitle(title1, title2, values[0], values[1], values[2]);
                } catch (NoSuchMethodError e) {
                    p.sendTitle(title1, title2);
                }
            }
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