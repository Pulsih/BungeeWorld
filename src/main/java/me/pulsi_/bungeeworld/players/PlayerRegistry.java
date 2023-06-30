package me.pulsi_.bungeeworld.players;

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

public class PlayerRegistry {

    private final HashMap<UUID, BWPlayer> players = new HashMap<>();

    public HashMap<UUID, BWPlayer> getPlayers() {
        return players;
    }

    public BWPlayer registerPlayer(Player p) {
        players.put(p.getUniqueId(), new BWPlayer());

        BWPlayer player = players.get(p.getUniqueId());
        player.configFile = getPlayerFile(p);
        player.config = getPlayerConfig(p);

        return player;
    }

    public File getPlayerFile(Player p) {
        if (players.get(p.getUniqueId()).configFile == null) {
            File file = new File(BungeeWorld.INSTANCE.getDataFolder(), "playerdata" + File.separator + p.getUniqueId() + ".yml");

            try {
                file.getParentFile().mkdir();
                file.createNewFile();
            } catch (IOException e) {
                BWLogger.error(e.getMessage());
            }

            players.get(p.getUniqueId()).configFile = file;
        }
        return players.get(p.getUniqueId()).configFile;
    }

    public FileConfiguration getPlayerConfig(Player p) {
        if (players.get(p.getUniqueId()).config == null) {
            File file = getPlayerFile(p);
            FileConfiguration config = new YamlConfiguration();

            try {
                config.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                BWLogger.error(e.getMessage());
            }

            players.get(p.getUniqueId()).config = config;
        }
        return players.get(p.getUniqueId()).config;
    }

    public void savePlayerFile(Player p, boolean async) {
        File file = getPlayerFile(p);
        FileConfiguration config = getPlayerConfig(p);

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.INSTANCE, () -> {
                try {
                    config.save(file);
                } catch (Exception ex) {
                    Bukkit.getScheduler().runTask(BungeeWorld.INSTANCE, () -> {
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

    public void reloadPlayerFile(Player p) {
        File file = getPlayerFile(p);
        FileConfiguration config = getPlayerConfig(p);

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            BWLogger.error(e.getMessage());
        }
    }
}