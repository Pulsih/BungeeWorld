package me.pulsi_.bungeeworld.values.configs;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValues {

    private String hubName, hubSpawn, hubTeleportSound, defaultChatFormat, securityDenyMessage, commandsDenyMessage, deathMessage, joinMessage, quitMessage;
    private String joinTitle, joinSound;
    private boolean joinSendTitle, joinPlaySound;
    private boolean disableBlockPlace, disableBlockBreak, disableMobSpawn, disableExplosion, disableActions, disableDrops, disablePickup, disableFall, disablePvP;
    private boolean updateChecker, teleportHubWhenJoin, isolateChat, isolateInventories;

    public void loadValues() {
        FileConfiguration config = BungeeWorld.getInstance().getConfigs().getConfig(ConfigManager.Type.CONFIG);

        hubName = config.getString("hub.name");
        hubSpawn = config.getString("hub.spawn");
        hubTeleportSound = config.getString("hub.teleport-sound");
        defaultChatFormat = config.getString("default-formats.chat");
        securityDenyMessage = config.getString("default-formats.security-deny-message");
        commandsDenyMessage = config.getString("default-formats.commands-deny-message");
        deathMessage = config.getString("default-formats.death-message");
        joinMessage = config.getString("default-formats.join-message");
        quitMessage = config.getString("default-formats.quit-message");
        joinTitle = config.getString("join-settings.title.text");
        joinSendTitle = config.getBoolean("join-settings.title.send");
        joinSound = config.getString("join-settings.sound.sound-type");
        joinPlaySound = config.getBoolean("join-settings.sound.play");
        updateChecker = config.getBoolean("update-checker");
        teleportHubWhenJoin = config.getBoolean("hub.teleport-when-join");
        isolateChat = config.getBoolean("server-settings.isolate-chat");
        isolateInventories = config.getBoolean("server-settings.isolate-inventories");
        disableBlockPlace = config.getBoolean("default-formats.disable-block-place");
        disableBlockBreak = config.getBoolean("default-formats.disable-block-break");
        disableMobSpawn = config.getBoolean("default-formats.disable-mob-spawning");
        disableExplosion = config.getBoolean("default-formats.disable-explosions");
        disableActions = config.getBoolean("default-formats.disable-player-actions");
        disableDrops = config.getBoolean("default-formats.disable-players-drops");
        disablePickup = config.getBoolean("default-formats.disable-players-pickup");
        disableFall = config.getBoolean("default-formats.disable-fall-damage");
        disablePvP = config.getBoolean("default-formats.disable-pvp");
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

    public String getDefaultChatFormat() {
        return defaultChatFormat;
    }

    public String getSecurityDenyMessage() {
        return securityDenyMessage;
    }

    public String getCommandsDenyMessage() {
        return commandsDenyMessage;
    }

    public String getDeathMessage() {
        return deathMessage;
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

    public boolean isDisableBlockPlace() {
        return disableBlockPlace;
    }

    public boolean isDisableBlockBreak() {
        return disableBlockBreak;
    }

    public boolean isDisableMobSpawn() {
        return disableMobSpawn;
    }

    public boolean isDisableExplosion() {
        return disableExplosion;
    }

    public boolean isDisableActions() {
        return disableActions;
    }

    public boolean isDisableDrops() {
        return disableDrops;
    }

    public boolean isDisablePickup() {
        return disablePickup;
    }

    public boolean isDisableFall() {
        return disableFall;
    }

    public boolean isDisablePvP() {
        return disablePvP;
    }
}