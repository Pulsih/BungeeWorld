package me.pulsi_.bungeeworld.values;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.BWConfigs;
import org.bukkit.configuration.file.FileConfiguration;

public class GlobalSettings {

    private String hub;

    public void loadValues() {
        FileConfiguration config = BungeeWorld.INSTANCE.getConfigs().getConfig(BWConfigs.Type.SAVES.name);

        hub = config.getString("hub");
    }

    public String getHub() {
        return hub;
    }
}