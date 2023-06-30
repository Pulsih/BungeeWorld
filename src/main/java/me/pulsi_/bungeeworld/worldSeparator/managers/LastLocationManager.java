package me.pulsi_.bungeeworld.worldSeparator.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.players.BWPlayer;
import me.pulsi_.bungeeworld.utils.BWUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LastLocationManager {

    private final BWPlayer player;
    private final Player p;

    public LastLocationManager(Player p) {
        this.player = BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().get(p.getUniqueId());
        this.p = p;
    }

    public void loadLastLocationsToHashMap() {
        FileConfiguration playerData = player.config;

        ConfigurationSection playerWorlds = playerData.getConfigurationSection("");
        if (playerWorlds == null) return;

        for (String worldName : playerWorlds.getKeys(false)) {
            String location = playerData.getString(worldName + ".last-location");
            if (location != null) player.locations.put(worldName, BWUtils.getLocation(location));
        }
    }

    public void saveLastLocationsToFile(boolean save) {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            if (player.locations.containsKey(worldName))
                player.config.set(worldName + ".last-location", player.locations.get(worldName));
        }

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    public void saveLastLocationToFile(Location location) {
        player.config.set(location.getWorld().getName() + ".last-location", BWUtils.getStringLocation(location));
        BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    public void saveLastLocationToFile(Location location, World world) {
        player.config.set(location.getWorld().getName() + ".last-location", BWUtils.getStringLocation(location));
        BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }
}