package me.pulsi_.bungeeworld.values;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.BWConfigs;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValues {

    private String hubTeleportSound, joinTitle, joinSound;
    private boolean updateChecker, teleportHubWhenJoin, joinSendTitle, joinPlaySound;
    private int saveTime;
    private boolean denyDotsCommands, isolateChat, isolateInventories, isolateEffects, isolateGamemode, isolateHealth, isolateHunger, isolateEnderchests, clearChat;

    public void loadValues() {
        FileConfiguration config = BungeeWorld.INSTANCE.getConfigs().getConfig(BWConfigs.Type.CONFIG.name);

        updateChecker = config.getBoolean("update-checker");
        hubTeleportSound = config.getString("hub.teleport-sound");
        joinTitle = config.getString("join-settings.title.text");
        joinSendTitle = config.getBoolean("join-settings.title.send");
        joinSound = config.getString("join-settings.sound.sound-type");
        joinPlaySound = config.getBoolean("join-settings.sound.play");
        teleportHubWhenJoin = config.getBoolean("hub.teleport-when-join");
        saveTime = config.getInt("server-settings.save-time");
        isolateChat = config.getBoolean("server-settings.isolate-chat");
        isolateInventories = config.getBoolean("server-settings.isolate-inventories");
        isolateEffects = config.getBoolean("server-settings.isolate-effects");
        isolateGamemode = config.getBoolean("server-settings.isolate-gamemode");
        isolateHealth = config.getBoolean("server-settings.isolate-health");
        isolateHunger = config.getBoolean("server-settings.isolate-hunger");
        isolateEnderchests = config.getBoolean("server-settings.isolate-enderchests");
        clearChat = config.getBoolean("server-settings.clear-chat");
        denyDotsCommands = config.getBoolean("server-settings.deny-dots-commands");
    }

    public String getHubTeleportSound() {
        return hubTeleportSound;
    }

    public String getJoinTitle() {
        return joinTitle;
    }

    public boolean isJoinSendTitle() {
        return joinSendTitle;
    }

    public String getJoinSound() {
        return joinSound;
    }

    public int getSaveTime() {
        return saveTime;
    }

    public boolean isJoinPlaySound() {
        return joinPlaySound;
    }

    public boolean isUpdateChecker() {
        return updateChecker;
    }

    public boolean isTeleportHubWhenJoin() {
        return teleportHubWhenJoin;
    }

    public boolean isIsolateChat() {
        return isolateChat;
    }

    public boolean isIsolateInventories() {
        return isolateInventories;
    }

    public boolean isIsolateEffects() {
        return isolateEffects;
    }

    public boolean isIsolateGamemode() {
        return isolateGamemode;
    }

    public boolean isIsolateHealth() {
        return isolateHealth;
    }

    public boolean isIsolateHunger() {
        return isolateHunger;
    }

    public boolean isIsolateEnderchests() {
        return isolateEnderchests;
    }

    public boolean isClearChat() {
        return clearChat;
    }

    public boolean isDenyDotsCommands() {
        return denyDotsCommands;
    }
}