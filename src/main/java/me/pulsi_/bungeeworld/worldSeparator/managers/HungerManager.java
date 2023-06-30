package me.pulsi_.bungeeworld.worldSeparator.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.players.BWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HungerManager {

    private final BWPlayer player;
    private final Player p;

    public HungerManager(Player p) {
        this.player = BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().get(p.getUniqueId());
        this.p = p;
    }

    public boolean setHunger(boolean clear) {
        return setHunger(p.getWorld().getName(), clear);
    }

    public boolean setHunger(String worldName, boolean clear) {
        boolean needToSave = false;

        if (!player.hungers.containsKey(worldName)) {
            int foodLevel;
            FileConfiguration playerData = player.config;
            int fileFoodLevel = playerData.getInt(worldName + ".hunger");

            if (fileFoodLevel <= 0) {
                if (clear) p.setFoodLevel(20);

                foodLevel = p.getFoodLevel();
                needToSave = true;
            } else foodLevel = fileFoodLevel;

            player.hungers.put(worldName, foodLevel);
        }
        p.setFoodLevel(player.hungers.get(worldName));
        return needToSave;
    }

    public void loadHungersToHashMap() {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            int foodLevel = player.config.getInt(worldName + ".hunger");
            if (foodLevel > 0) player.hungers.put(worldName, foodLevel);
        }
    }

    /**
     * Load the current player hunger to the current world.
     */
    public void loadHungerToHashMap() {
        loadHungerToHashMap(p.getWorld().getName());
    }

    /**
     * Load the current player hunger to the selected world.
     * @param world The world
     */
    public void loadHungerToHashMap(World world) {
        loadHungerToHashMap(world.getName());
    }

    /**
     * Load the current player hunger to the selected world.
     * @param worldName The world name
     */
    public void loadHungerToHashMap(String worldName) {
        player.hungers.put(worldName, p.getFoodLevel());
    }

    public void saveHungersToFile() {
        saveHungersToFile(true);
    }

    public void saveHungersToFile(boolean save) {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            if (player.hungers.containsKey(worldName))
                player.config.set(worldName + ".hunger", player.hungers.get(worldName));
        }

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    /**
     * Save the current player hunger to the current world
     * @param save Save or not the file
     */
    public void saveHungerToFile(boolean save) {
        saveHungerToFile(p.getWorld(), save);
    }

    /**
     * Save the current player hunger to the selected world
     * @param world The world
     * @param save Save or not the file
     */
    public void saveHungerToFile(World world, boolean save) {
        saveHungerToFile(world.getName(), save);
    }

    public void saveHungerToFile(String worldName, boolean save) {
        FileConfiguration playerData = player.config;

        if (player.hungers.containsKey(worldName)) playerData.set(worldName + ".hunger", player.hungers.get(worldName));
        else playerData.set(worldName + ".hunger", p.getFoodLevel());

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }
}