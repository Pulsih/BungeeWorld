package me.pulsi_.bungeeworld;

import me.pulsi_.bungeeworld.managers.ConfigManager;
import me.pulsi_.bungeeworld.managers.DataManager;
import me.pulsi_.bungeeworld.players.PlayerRegistry;
import me.pulsi_.bungeeworld.utils.BWLogger;
import me.pulsi_.bungeeworld.worlds.WorldsRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BungeeWorld extends JavaPlugin {

    public static BungeeWorld INSTANCE;

    private boolean placeholderApiHooked;
    private PlayerRegistry playerRegistry;
    private WorldsRegistry worldsRegistry;
    private DataManager dataManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Bukkit.getScheduler().runTaskLater(this, () -> {
            dataManager = new DataManager(this);
            configManager = new ConfigManager(this);
            playerRegistry = new PlayerRegistry();
            worldsRegistry = new WorldsRegistry();

            dataManager.setupPlugin();

            placeholderApiHooked = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        }, 1l);
    }

    @Override
    public void onDisable() {
        INSTANCE = this;

        BWLogger.log("");
        BWLogger.log("    &2&lBungee&a&lWorld &cDisabling plugin...");
        BWLogger.log("");
    }

    public PlayerRegistry getPlayerRegistry() {
        return playerRegistry;
    }

    public WorldsRegistry getWorldsRegistry() {
        return worldsRegistry;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public ConfigManager getConfigs() {
        return configManager;
    }

    public boolean isPlaceholderApiHooked() {
        return placeholderApiHooked;
    }
}