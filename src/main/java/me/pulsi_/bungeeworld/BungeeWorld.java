package me.pulsi_.bungeeworld;

import me.pulsi_.bungeeworld.managers.ConfigManager;
import me.pulsi_.bungeeworld.managers.DataManager;
import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWLogger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class BungeeWorld extends JavaPlugin {

    private static BungeeWorld instance;
    private static boolean placeholderApiHooked;
    private ConfigManager configs;

    @Override
    public void onEnable() {
        instance = this;

        configs = new ConfigManager(this);
        DataManager.setupPlugin();

        for (World world : Bukkit.getWorlds()) WorldManager.registerWorld(world);
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) placeholderApiHooked = true;
    }

    @Override
    public void onDisable() {
        instance = this;

        BWLogger.log("");
        BWLogger.log("    &2&lBungee&a&lWorld &cDisabling plugin...");
        BWLogger.log("");
    }

    public static BungeeWorld getInstance() {
        return instance;
    }

    public ConfigManager getConfigs() {
        return configs;
    }

    public static boolean isPlaceholderApiHooked() {
        return placeholderApiHooked;
    }
}