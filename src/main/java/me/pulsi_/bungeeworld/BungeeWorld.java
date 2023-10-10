package me.pulsi_.bungeeworld;

import me.pulsi_.bungeeworld.managers.BWConfigs;
import me.pulsi_.bungeeworld.managers.BWData;
import me.pulsi_.bungeeworld.registry.WorldsRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BungeeWorld extends JavaPlugin {

    public static BungeeWorld INSTANCE;

    private boolean placeholderApiHooked;
    private WorldsRegistry worldsRegistry;
    private BWData data;
    private BWConfigs configs;

    @Override
    public void onEnable() {
        INSTANCE = this;

        data = new BWData(this);
        configs = new BWConfigs(this);
        worldsRegistry = new WorldsRegistry(this);

        data.setupPlugin();

        placeholderApiHooked = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @Override
    public void onDisable() {
        data.shutdownPlugin();
    }

    public WorldsRegistry getWorldsRegistry() {
        return worldsRegistry;
    }

    public BWData getData() {
        return data;
    }

    public BWConfigs getConfigs() {
        return configs;
    }

    public boolean isPlaceholderApiHooked() {
        return placeholderApiHooked;
    }
}