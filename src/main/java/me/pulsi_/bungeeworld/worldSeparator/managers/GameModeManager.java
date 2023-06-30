package me.pulsi_.bungeeworld.worldSeparator.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.players.BWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GameModeManager {

    private final BWPlayer player;
    private final Player p;

    public GameModeManager(Player p) {
        this.player = BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().get(p.getUniqueId());
        this.p = p;
    }

    public boolean setGameMode(boolean clear) {
        return setGameMode(p.getWorld().getName(), clear);
    }

    public boolean setGameMode(String worldName, boolean clear) {
        boolean needToSave = false;

        if (!player.gamemodes.containsKey(worldName)) {
            String gameMode;
            FileConfiguration playerData = player.config;
            String fileGameMode = playerData.getString(worldName + ".gamemode");

            if (fileGameMode == null) {
                if (clear) p.setGameMode(GameMode.SURVIVAL);

                gameMode = p.getGameMode().toString();
                needToSave = true;
            } else gameMode = fileGameMode;

            player.gamemodes.put(worldName, GameMode.valueOf(gameMode));
        }
        p.setGameMode(player.gamemodes.get(worldName));
        return needToSave;
    }

    public void loadGameModesToHashMap() {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            String gameMode = player.config.getString(worldName + ".gamemode");
            if (gameMode != null) player.gamemodes.put(worldName, GameMode.valueOf(gameMode));
        }
    }

    /**
     * Load the current player gamemode to the current world.
     */
    public void loadGameModeToHashMap() {
        loadGameModeToHashMap(p.getWorld().getName());
    }

    /**
     * Load the current player gamemode to the selected world.
     * @param world The world
     */
    public void loadGameModeToHashMap(World world) {
        loadGameModeToHashMap(world.getName());
    }

    /**
     * Load the current player gamemode to the selected world.
     * @param worldName The world name
     */
    public void loadGameModeToHashMap(String worldName) {
        player.gamemodes.put(worldName, p.getGameMode());
    }

    public void saveGameModesToFile() {
        saveGameModesToFile(true);
    }

    public void saveGameModesToFile(boolean save) {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            if (player.gamemodes.containsKey(worldName))
                player.config.set(worldName + ".gamemode", player.gamemodes.get(worldName).name());
        }

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    /**
     * Save the current player gamemode to the current world
     * @param save Save or not the file
     */
    public void saveGameModeToFile(boolean save) {
        saveGameModeToFile(p.getWorld(), save);
    }

    /**
     * Save the current player gamemode to the selected world
     * @param world The world
     * @param save Save or not the file
     */
    public void saveGameModeToFile(World world, boolean save) {
        saveGameModeToFile(world.getName(), save);
    }

    public void saveGameModeToFile(String worldName, boolean save) {
        FileConfiguration playerData = player.config;

        if (player.gamemodes.containsKey(worldName)) playerData.set(worldName + ".gamemode", player.gamemodes.get(worldName).name());
        else playerData.set(worldName + ".gamemode", p.getGameMode().name());

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }
}