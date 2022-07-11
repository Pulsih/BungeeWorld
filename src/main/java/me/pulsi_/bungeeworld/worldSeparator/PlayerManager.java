package me.pulsi_.bungeeworld.worldSeparator;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private static final HashMap<UUID, File> playerFile = new HashMap<>();

    private static final HashMap<UUID, FileConfiguration> playerConfig = new HashMap<>();

    public static File getPlayerFile(Player p) {
        if (!playerFile.containsKey(p.getUniqueId())) {
            File file = new File(BungeeWorld.getInstance().getDataFolder(), "playerdata" + File.separator + p.getUniqueId() + ".yml");
            try {
                file.createNewFile();
            } catch (IOException e) {
                BWLogger.error(e.getMessage());
            }
            playerFile.put(p.getUniqueId(), file);
        }
        return playerFile.get(p.getUniqueId());
    }

    public static FileConfiguration getPlayerConfig(Player p) {
        if (!playerConfig.containsKey(p.getUniqueId())) {
            File file = getPlayerFile(p);
            FileConfiguration config = new YamlConfiguration();
            try {
                config.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                BWLogger.error(e.getMessage());
            }
            playerConfig.put(p.getUniqueId(), config);
        }
        return playerConfig.get(p.getUniqueId());
    }

    public static void savePlayerFile(Player p, boolean async) {
        File file = getPlayerFile(p);
        FileConfiguration config = getPlayerConfig(p);

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.getInstance(), () -> {
                try {
                    config.save(file);
                } catch (Exception ex) {
                    Bukkit.getScheduler().runTask(BungeeWorld.getInstance(), () -> {
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            BWLogger.error(e.getMessage());
                        }
                    });
                }
            });
        } else {
            try {
                config.save(file);
            } catch (IOException e) {
                BWLogger.error(e.getMessage());
            }
        }
    }

    public static void reloadPlayerFile(Player p) {
        File file = getPlayerFile(p);
        FileConfiguration config = getPlayerConfig(p);

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            BWLogger.error(e.getMessage());
        }
    }
}