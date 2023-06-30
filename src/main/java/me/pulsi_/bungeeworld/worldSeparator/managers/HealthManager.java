package me.pulsi_.bungeeworld.worldSeparator.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.players.BWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HealthManager {

    private final BWPlayer player;
    private final Player p;

    public HealthManager(Player p) {
        this.player = BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().get(p.getUniqueId());
        this.p = p;
    }

    public boolean setHealth(boolean clear) {
        return setHealth(p.getWorld().getName(), clear);
    }

    public boolean setHealth(String worldName, boolean clear) {
        boolean needToSave = false;

        if (!player.healths.containsKey(worldName)) {
            double health, maxHealth;
            FileConfiguration playerData = player.config;
            double value = playerData.getDouble(worldName + ".health.value"), max = playerData.getDouble(worldName + ".health.max");

            if (value <= 0) {
                if (clear) {
                    p.setHealth(20);
                    p.setMaxHealth(20);
                }

                health = p.getHealth();
                maxHealth = p.getMaxHealth();
                needToSave = true;
            } else {
                health = value;
                maxHealth = max;
            }

            player.healths.put(worldName, new BWPlayer.BWHealth(health, maxHealth));
        }
        p.setHealth(player.healths.get(worldName).value);
        p.setMaxHealth(player.healths.get(worldName).max);
        return needToSave;
    }

    public void loadHealthsToHashMap() {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            double value = player.config.getDouble(worldName + ".health.value"), max = player.config.getDouble(worldName + ".health.max");
            if (value > 0) player.healths.put(worldName, new BWPlayer.BWHealth(value, max));
        }
    }

    /**
     * Load the current player health to the current world.
     */
    public void loadHealthToHashMap() {
        loadHealthToHashMap(p.getWorld().getName());
    }

    /**
     * Load the current player health to the selected world.
     *
     * @param world The world
     */
    public void loadHealthToHashMap(World world) {
        loadHealthToHashMap(world.getName());
    }

    /**
     * Load the current player health to the selected world.
     *
     * @param worldName The world name
     */
    public void loadHealthToHashMap(String worldName) {
        player.healths.put(worldName, new BWPlayer.BWHealth(p.getHealth(), p.getMaxHealth()));
    }

    public void saveHealthsToFile() {
        saveHealthsToFile(true);
    }

    public void saveHealthsToFile(boolean save) {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            if (player.healths.containsKey(worldName)) {
                player.config.set(worldName + ".health.value", player.healths.get(worldName).value);
                player.config.set(worldName + ".health.max", player.healths.get(worldName).max);
            }
        }

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    /**
     * Save the current player health to the current world
     *
     * @param save Save or not the file
     */
    public void saveHealthToFile(boolean save) {
        saveHealthToFile(p.getWorld(), save);
    }

    /**
     * Save the current player health to the selected world
     *
     * @param world The world
     * @param save  Save or not the file
     */
    public void saveHealthToFile(World world, boolean save) {
        saveHealthToFile(world.getName(), save);
    }

    public void saveHealthToFile(String worldName, boolean save) {
        FileConfiguration playerData = player.config;

        if (player.healths.containsKey(worldName)) {
            playerData.set(worldName + ".health.value", player.healths.get(worldName).value);
            playerData.set(worldName + ".health.max", player.healths.get(worldName).max);
        } else {
            playerData.set(worldName + ".health.value", p.getHealth());
            playerData.set(worldName + ".health.max", p.getMaxHealth());
        }

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }
}