package me.pulsi_.bungeeworld.registry;

import me.pulsi_.bungeeworld.BungeeWorld;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * This class is used to simplify actions for that player in the specified world.
 */
public class PlayerUtils {

    private final Player p;

    public PlayerUtils(Player p) {
        this.p = p;
    }

    public void loadStatistics(String worldName) {
        BWPlayer player = getBWPlayer(worldName);
        if (player == null) return;

        p.getInventory().setContents(player.getInventory());
        p.getEnderChest().setContents(player.getEnderChest());
        p.addPotionEffects(player.getEffects());
        p.setGameMode(player.getGameMode());
        p.setHealth(player.getHealth());
        p.setMaxHealth(player.getMaxHealth());
        p.setFoodLevel(player.getHunger());
    }

    public void storeStatistics(String worldName) {
        BWPlayer player = getBWPlayer(worldName);
        if (player == null) return;

        player.setInventory(p.getInventory().getContents());
        player.setEnderChest(p.getEnderChest().getContents());
        player.setEffects(p.getActivePotionEffects());
        player.setGameMode(p.getGameMode());
        player.setHealth((int) p.getHealth());
        player.setMaxHealth((int) p.getMaxHealth());
        player.setHunger(p.getFoodLevel());
    }

    /**
     * Method to change last player location separated from all other statistics because it is the only value that change when switching world.
     * @param location The location.
     * @param worldName The world where to save this location.
     */
    public void storeLastLocation(Location location, String worldName) {
        BWPlayer player = getBWPlayer(worldName);
        if (player == null) return;

        player.setLastLocation(location);
    }

    private BWPlayer getBWPlayer(String worldName) {
        return BungeeWorld.INSTANCE.getWorldsRegistry().getWorlds().get(worldName).getPlayers().get(p.getUniqueId());
    }
}