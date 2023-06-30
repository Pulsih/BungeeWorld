package me.pulsi_.bungeeworld.worldSeparator.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.players.BWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnderChestManager {

    private final BWPlayer player;
    private final Player p;

    public EnderChestManager(Player p) {
        this.player = BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().get(p.getUniqueId());
        this.p = p;
    }

    public boolean setEnderChest(boolean clear) {
        return setEnderChest(p.getWorld().getName(), clear);
    }

    public boolean setEnderChest(String worldName, boolean clear) {
        boolean needToSave = false;

        if (!player.enderchests.containsKey(worldName)) {
            ItemStack[] enderchest;
            FileConfiguration playerData = player.config;
            List<?> items = playerData.getList(worldName + ".enderchest");

            if (items == null) {
                if (clear) p.getEnderChest().clear();

                enderchest = p.getEnderChest().getContents();
                needToSave = true;
            } else enderchest = items.toArray(new ItemStack[0]);

            player.enderchests.put(worldName, enderchest);
        }
        Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.INSTANCE, () -> p.getEnderChest().setContents(player.enderchests.get(worldName)));
        return needToSave;
    }

    public void loadEnderChestsToHashMap() {
       for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            List<?> items = player.config.getList(worldName + ".enderchest");
            if (items != null) player.enderchests.put(worldName, items.toArray(new ItemStack[0]));
        }
    }

    /**
     * Load the current player enderchest to the current world.
     */
    public void loadEnderChestToHashMap() {
        loadEnderChestToHashMap(p.getWorld().getName());
    }

    /**
     * Load the current player enderchest to the selected world.
     * @param world The world
     */
    public void loadEnderChestToHashMap(World world) {
        loadEnderChestToHashMap(world.getName());
    }

    /**
     * Load the current player enderchest to the selected world.
     * @param worldName The world name
     */
    public void loadEnderChestToHashMap(String worldName) {
        player.enderchests.put(worldName, p.getEnderChest().getContents());
    }

    public void saveEnderChestsToFile() {
        saveEnderChestsToFile(true);
    }

    public void saveEnderChestsToFile(boolean save) {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            if (player.enderchests.containsKey(worldName))
                player.config.set(worldName + ".enderchest", player.enderchests.get(worldName));
        }

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    /**
     * Save the current player enderchest to the current world
     * @param save Save or not the file
     */
    public void saveEnderChestToFile(boolean save) {
        saveEnderChestToFile(p.getWorld(), save);
    }

    /**
     * Save the current player enderchest to the selected world
     * @param world The world
     * @param save Save or not the file
     */
    public void saveEnderChestToFile(World world, boolean save) {
        FileConfiguration playerData = player.config;
        String worldName = world.getName();

        if (player.enderchests.containsKey(worldName)) playerData.set(worldName + ".enderchest", player.enderchests.get(worldName));
        else playerData.set(worldName + ".enderchest", p.getInventory().getContents());

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }
}