package me.pulsi_.bungeeworld.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class BWSounds {

    public static void playSound(Player p, String soundString) {
        try {
            newPlaySound(p, soundString);
        } catch (NoClassDefFoundError e) {
            legacyPlaySound(p, soundString);
        }
    }

    public static void playSound(Location location, String soundString) {
        try {
            newPlaySound(location, soundString);
        } catch (NoClassDefFoundError e) {
            legacyPlaySound(location, soundString);
        }
    }

    private static void newPlaySound(Player p, String soundString) {
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

    private static void legacyPlaySound(Player p, String soundString) {
        if (soundString == null || soundString.isEmpty()) return;

        float volume = 1F, pitch = 1F;
        if (!soundString.contains(" ")) {
            try {
                p.playSound(p.getLocation(), Sound.valueOf(soundString), volume, pitch);
            } catch (IllegalArgumentException e) {
                try {
                    p.playSound(p.getLocation(), soundString, volume, pitch);
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

        try {
            p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
        } catch (IllegalArgumentException e) {
            try {
                p.playSound(p.getLocation(), sound, volume, pitch);
            } catch (Exception ex) {
                BWLogger.warn(sound + " is not a valid sound! (Did you type it in lowercase?)");
            }
        }
    }

    private static void newPlaySound(Location location, String soundString) {
        if (soundString == null || soundString.isEmpty()) return;

        float volume = 1F, pitch = 1F;
        SoundCategory category = SoundCategory.MASTER;
        if (!soundString.contains(" ")) {
            try {
                location.getWorld().playSound(location, Sound.valueOf(soundString), category, volume, pitch);
            } catch (IllegalArgumentException e) {
                try {
                    location.getWorld().playSound(location, soundString, category, volume, pitch);
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
            location.getWorld().playSound(location, Sound.valueOf(sound), category, volume, pitch);
        } catch (IllegalArgumentException e) {
            try {
                location.getWorld().playSound(location, sound, category, volume, pitch);
            } catch (Exception ex) {
                BWLogger.warn(sound + " is not a valid sound! (Did you type it in lowercase?)");
            }
        }
    }

    private static void legacyPlaySound(Location location, String soundString) {
        if (soundString == null || soundString.isEmpty()) return;

        float volume = 1F, pitch = 1F;
        if (!soundString.contains(" ")) {
            try {
                location.getWorld().playSound(location, Sound.valueOf(soundString), volume, pitch);
            } catch (IllegalArgumentException e) {
                try {
                    location.getWorld().playSound(location, soundString, volume, pitch);
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

        try {
            location.getWorld().playSound(location, Sound.valueOf(sound), volume, pitch);
        } catch (IllegalArgumentException e) {
            try {
                location.getWorld().playSound(location, sound, volume, pitch);
            } catch (Exception ex) {
                BWLogger.warn(sound + " is not a valid sound! (Did you type it in lowercase?)");
            }
        }
    }
}