package me.pulsi_.bungeeworld.values.configs;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.BWConfigs;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValues {

    private String hubName;

    private String hubSpawn;

    private String hubTeleportSound;

    private String chatFormat;

    private String joinMessage;

    private String quitMessage;

    private String deathMessage1;

    private String deathMessage2;

    private String deathMessage3;

    private String joinTitle;

    private String joinSound;

    private boolean joinSendTitle;

    private boolean joinPlaySound;

    private boolean updateChecker;

    private boolean teleportHubWhenJoin;

    private boolean clearChat;

    private boolean saveStatisticsOnWorldChange, denyDotsCommands;

    boolean isolateChat, isolateEffects, isolateEnderchests, isolateGamemode, isolateHealth, isolateHunger, isolateInventories;

    public void loadValues() {
        FileConfiguration config = BungeeWorld.INSTANCE.getConfigs().getConfig(BWConfigs.Type.CONFIG.name);
        
        hubName = config.getString("hub.name");
        hubSpawn = config.getString("hub.spawn");
        hubTeleportSound = config.getString("hub.teleport-sound");
        chatFormat = config.getString("server-settings.chat");
        joinMessage = config.getString("server-settings.join-message");
        quitMessage = config.getString("server-settings.quit-message");
        deathMessage1 = config.getString("server-settings.death-message");
        deathMessage2 = config.getString("server-settings.killer-death-message");
        deathMessage3 = config.getString("server-settings.killer-weapon-death-message");
        joinTitle = config.getString("join-settings.title.text");
        joinSendTitle = config.getBoolean("join-settings.title.send");
        joinSound = config.getString("join-settings.sound.sound-type");
        joinPlaySound = config.getBoolean("join-settings.sound.play");
        updateChecker = config.getBoolean("update-checker");
        teleportHubWhenJoin = config.getBoolean("hub.teleport-when-join");
        isolateChat = config.getBoolean("server-settings.isolate-chat");
        isolateInventories = config.getBoolean("server-settings.isolate-inventories");
        isolateEffects = config.getBoolean("server-settings.isolate-effects");
        isolateGamemode = config.getBoolean("server-settings.isolate-gamemode");
        isolateHealth = config.getBoolean("server-settings.isolate-health");
        isolateHunger = config.getBoolean("server-settings.isolate-hunger");
        isolateEnderchests = config.getBoolean("server-settings.isolate-enderchests");
        clearChat = config.getBoolean("server-settings.clear-chat");
        saveStatisticsOnWorldChange = config.getBoolean("server-settings.save-statistics-on-world-change");
        denyDotsCommands = config.getBoolean("server-settings.deny-dots-commands");
    }

    public String getHubName() {
        return hubName;
    }

    public String getHubSpawn() {
        return hubSpawn;
    }

    public String getHubTeleportSound() {
        return hubTeleportSound;
    }

    public String getDeathMessage1() {
        return deathMessage1;
    }

    public String getDeathMessage2() {
        return deathMessage2;
    }

    public String getDeathMessage3() {
        return deathMessage3;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public String getQuitMessage() {
        return quitMessage;
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

    public boolean isSaveStatisticsOnWorldChange() {
        return saveStatisticsOnWorldChange;
    }

    public boolean isDenyDotsCommands() {
        return denyDotsCommands;
    }
}